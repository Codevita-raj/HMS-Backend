package com.projecth.hms.lab.service;

import com.projecth.hms.lab.dto.labOrder.LabOrderItemResponse;
import com.projecth.hms.lab.dto.labOrder.LabOrderRequest;
import com.projecth.hms.lab.dto.labOrder.LabOrderResponse;
import com.projecth.hms.lab.entity.LabOrder;
import com.projecth.hms.lab.entity.LabOrderItem;
import com.projecth.hms.lab.repository.LabOrderItemRepository;
import com.projecth.hms.lab.repository.LabOrderRepository;
import com.projecth.hms.shared.enums.LabOrderStatus;
import com.projecth.hms.shared.enums.LabSampleStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LabOrderService {

    private final LabOrderRepository labOrderRepository;
    private final LabOrderItemRepository labOrderItemRepository;

    @Transactional
    public LabOrderResponse createLabOrder(LabOrderRequest request) {

        LabOrder order = new LabOrder();
        order.setPatientId(request.getPatientId());
        order.setAdmissionId(request.getAdmissionId());
        order.setDoctorId(request.getDoctorId());
        order.setStatus(LabOrderStatus.ORDERED);
        order.setOrderedAt(LocalDateTime.now());
        order.setCreatedAt(LocalDateTime.now());
        order.setCreatedBy("SYSTEM");

        LabOrder savedOrder = labOrderRepository.save(order);

        List<LabOrderItem> items = request.getLabTestIds()
                .stream()
                .map(testId -> {
                    LabOrderItem item = new LabOrderItem();
                    item.setLabOrderId(savedOrder.getId());
                    item.setLabTestId(testId);
                    item.setSampleStatus(LabSampleStatus.NOT_COLLECTED);
                    return item;
                }).toList();

        labOrderItemRepository.saveAll(items);

        return mapToResponse(savedOrder, items);
    }

    public LabOrderResponse getLabOrderById(Long labOrderId) {

        LabOrder order = labOrderRepository.findById(labOrderId)
                .orElseThrow(() -> new IllegalStateException("LabOrder not found"));

        List<LabOrderItem> items =
                labOrderItemRepository.findByLabOrderId(order.getId());

        return mapToResponse(order, items);
    }

    public List<LabOrderResponse> getLabOrdersByPatientId(Long patientId) {

        return labOrderRepository.findByPatientId(patientId)
                .stream()
                .map(order -> {
                    List<LabOrderItem> items =
                            labOrderItemRepository.findByLabOrderId(order.getId());
                    return mapToResponse(order, items);
                })
                .toList();
    }


    public List<LabOrderResponse> getLabOrdersByAdmissionId(Long admissionId) {

        return labOrderRepository.findByAdmissionId(admissionId)
                .stream()
                .map(order -> {
                    List<LabOrderItem> items =
                            labOrderItemRepository.findByLabOrderId(order.getId());
                    return mapToResponse(order, items);
                })
                .toList();
    }

    /* ---------------- CANCEL (SOFT DELETE) ---------------- */

    @Transactional
    public void cancelLabOrder(Long labOrderId) {

        LabOrder order = labOrderRepository.findById(labOrderId)
                .orElseThrow(() -> new IllegalStateException("LabOrder not found"));

        boolean anyCollected =
                labOrderItemRepository
                        .existsByLabOrderIdAndSampleStatusNot(
                                labOrderId, LabSampleStatus.NOT_COLLECTED);

        if (anyCollected) {
            throw new IllegalStateException(
                    "Cannot cancel LabOrder after sample collection");
        }

        order.setStatus(LabOrderStatus.CANCELLED);
        labOrderRepository.save(order);
    }

    /* ---------------- MAPPER ---------------- */

    private LabOrderResponse mapToResponse(LabOrder order, List<LabOrderItem> items) {
        return LabOrderResponse.builder()
                .labOrderId(order.getId())
                .patientId(order.getPatientId())
                .admissionId(order.getAdmissionId())
                .doctorId(order.getDoctorId())
                .status(order.getStatus())
                .orderedAt(order.getOrderedAt())
                .items(items.stream().map(i ->
                        LabOrderItemResponse.builder()
                                .labTestId(i.getLabTestId())
                                .sampleStatus(i.getSampleStatus())
                                .resultValue(i.getResultValue())
                                .build()
                ).toList())
                .build();
    }
}
