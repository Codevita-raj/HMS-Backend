package com.projecth.hms.patient.service;

import com.projecth.hms.inventory.repository.InventoryTransactionRepository;
import com.projecth.hms.lab.repository.LabOrderItemRepository;
import com.projecth.hms.lab.repository.LabOrderRepository;
import com.projecth.hms.patient.dto.AdmissionCreateRequest;
import com.projecth.hms.patient.dto.AdmissionDischargeRequest;
import com.projecth.hms.patient.dto.AdmissionResponse;
import com.projecth.hms.patient.dto.AdmissionUpdateRequest;
import com.projecth.hms.patient.entity.Patient;
import com.projecth.hms.patient.entity.PatientAdmission;
import com.projecth.hms.patient.entity.PatientCareAssignment;
import com.projecth.hms.patient.repository.PatientAdmissionRepository;
import com.projecth.hms.patient.repository.PatientCareAssignmentRepository;
import com.projecth.hms.patient.repository.PatientRepository;
import com.projecth.hms.shared.enums.AdmissionStatus;
import com.projecth.hms.shared.enums.InventoryTransactionType;
import com.projecth.hms.shared.enums.LabSampleStatus;
import com.projecth.hms.user.entity.User;
import com.projecth.hms.user.service.UserService;
import com.projecth.hms.wardmanagement.dto.bedOccupancy.BedOccupancyRequest;
import com.projecth.hms.wardmanagement.entity.BedOccupancy;
import com.projecth.hms.wardmanagement.repository.BedOccupancyRepository;
import com.projecth.hms.wardmanagement.service.BedOccupancyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientAdmissionService {

    private final PatientAdmissionRepository patientAdmissionRepository;
    private final PatientRepository patientRepository;
    private final PatientCareAssignmentRepository patientCareAssignmentRepository;
    private final BedOccupancyService bedOccupancyService;
    private final BedOccupancyRepository bedOccupancyRepository;
    private final LabOrderItemRepository labOrderItemRepository;
    private final LabOrderRepository labOrderRepository;
    private final InventoryTransactionRepository inventoryTransactionRepository;
    private final UserService userService;


    @Transactional
    public AdmissionResponse createAdmission(AdmissionCreateRequest admissionCreateRequest){

        User currentUser = userService.getCurrentUser();


        Long patientId = admissionCreateRequest.getPatientId();

        Patient patient=  patientRepository.findById(patientId)
              .orElseThrow(()->new RuntimeException("Patient not found with Id "+patientId));
        boolean alreadyAdmitted = patientAdmissionRepository.existsActiveAdmission(
                patientId,
                List.of(AdmissionStatus.ADMITTED, AdmissionStatus.IN_TREATMENT)
        );

        if (alreadyAdmitted) {
            throw new RuntimeException("Patient already has an active admission");
        }
        PatientAdmission patientAdmission = new PatientAdmission();

        patientAdmission.setPatientId(patientId);
        patientAdmission.setAdmissionDate(admissionCreateRequest.getAdmissionDate());
        patientAdmission.setAdmissionType(admissionCreateRequest.getAdmissionType());
        patientAdmission.setReasonForAdmission(admissionCreateRequest.getReasonForAdmission());

        patientAdmission.setStatus(AdmissionStatus.ADMITTED);
        patientAdmission.setCreatedAt(LocalDateTime.now());
        patientAdmission.setCreatedBy(currentUser.getEmail());
        patientAdmission.setUpdatedAt(LocalDateTime.now());
        patientAdmission.setUpdatedBy(currentUser.getEmail());
        PatientAdmission savedAdmission =
                patientAdmissionRepository.save(patientAdmission);

        if (admissionCreateRequest.getBedId() != null) {

            BedOccupancyRequest occupancyRequest = new BedOccupancyRequest();
            occupancyRequest.setBedId(admissionCreateRequest.getBedId());
            occupancyRequest.setPatientId(admissionCreateRequest.getPatientId());
            Long admissionId = savedAdmission.getAdmissionId();
            occupancyRequest.setAdmissionId(admissionId);

            bedOccupancyService.occupyBed(occupancyRequest);
        }

        return toMapAdmissionResponse( savedAdmission);
    }

    @Transactional
    public AdmissionResponse updateAdmission(Long admissionId , AdmissionUpdateRequest admissionUpdateRequest){
        User currentUser = userService.getCurrentUser();
      //  Long patientId = admissionUpdateRequest.getPatientId();

//        Patient patient=  patientRepository.findById(patientId)
//                .orElseThrow(()->new RuntimeException("Patient not found with Id "+patientId));
       PatientAdmission patientAdmission = patientAdmissionRepository.findById(admissionId)
              .orElseThrow(()-> new RuntimeException("Admission not found with id "+admissionId));

        if (patientAdmission.getStatus() == AdmissionStatus.DISCHARGED ||
                patientAdmission.getStatus() == AdmissionStatus.CANCELLED) {
            throw new RuntimeException("Cannot update a discharged or cancelled admission");
        }

       // patientAdmission.setPatientId(admissionUpdateRequest.getPatientId());
        patientAdmission.setAdmissionDate(admissionUpdateRequest.getAdmissionDate());
        patientAdmission.setAdmissionType(admissionUpdateRequest.getAdmissionType());
        patientAdmission.setReasonForAdmission(admissionUpdateRequest.getReasonForAdmission());

        patientAdmission.setUpdatedAt(LocalDateTime.now());
        patientAdmission.setUpdatedBy(currentUser.getEmail());
        PatientAdmission savedAdmission =
                patientAdmissionRepository.save(patientAdmission);
        if (admissionUpdateRequest.getBedId() != null) {
            BedOccupancyRequest occupancyRequest = new BedOccupancyRequest();
            occupancyRequest.setBedId(admissionUpdateRequest.getBedId());
            occupancyRequest.setPatientId(patientAdmission.getPatientId());
            occupancyRequest.setAdmissionId(patientAdmission.getAdmissionId());

            bedOccupancyService.occupyBed(occupancyRequest);
        }

        return toMapAdmissionResponse(savedAdmission);
    }
    @Transactional
    public AdmissionResponse dischargeAdmission(Long admissionId, AdmissionDischargeRequest admissionDischargeRequest){
        User currentUser = userService.getCurrentUser();
        PatientAdmission patientAdmission = patientAdmissionRepository.findById(admissionId)
                .orElseThrow(()-> new RuntimeException("Admission not found with id "+admissionId));

        if (patientAdmission.getStatus() == AdmissionStatus.DISCHARGED ||
                patientAdmission.getStatus() == AdmissionStatus.CANCELLED) {
            throw new RuntimeException("Cannot update a discharged or cancelled admission");
        }

        LocalDateTime dischargeDate = admissionDischargeRequest.getDischargeDate();
        if (dischargeDate.isBefore(patientAdmission.getAdmissionDate())){
            throw new RuntimeException("Discharge date cannot be before Admission date ");
        }

        validateLabClearance(admissionId);

        validateMedicineClearance(admissionId);

        List<PatientCareAssignment> activeAssignments =
                patientCareAssignmentRepository
                        .findByAdmissionIdAndActiveTrue(admissionId);

        for (PatientCareAssignment assignment : activeAssignments) {
            assignment.setAssignedTo(LocalDateTime.now());
            assignment.setActive(false);
        }

        patientCareAssignmentRepository.saveAll(activeAssignments);

        patientAdmission.setDischargeDate(dischargeDate);
        patientAdmission.setRemarks(admissionDischargeRequest.getRemarks());
        patientAdmission.setStatus(AdmissionStatus.DISCHARGED);
        patientAdmission.setUpdatedAt(LocalDateTime.now());
        patientAdmission.setUpdatedBy(currentUser.getEmail());

        bedOccupancyService.freeBedByAdmission(admissionId);
        //billingHookService.onDischarge(admissionId);

        return toMapAdmissionResponse(patientAdmissionRepository.save(patientAdmission));
    }

    public List<AdmissionResponse> getAllAdmissions(Long patientId){
        List<PatientAdmission> patientAdmission = patientAdmissionRepository.findByPatientId(patientId);
        if (patientAdmission.isEmpty()){
           throw  new RuntimeException("Admission not found with patientId "+patientId);
        }
        return patientAdmission.stream()
                .map(this::toMapAdmissionResponse)
                .toList();
    }
    public AdmissionResponse getAdmissionById(Long admissionId){
        PatientAdmission patientAdmission = patientAdmissionRepository.findById(admissionId)
                .orElseThrow(()-> new RuntimeException("Admission not found with id admissionId "+admissionId));

        return toMapAdmissionResponse(patientAdmission);
    }
    //Helper Methods

    public void validateLabClearance(Long admissionId) {


        List<Long> labOrderIds =
                labOrderRepository.findIdsByAdmissionId(admissionId);

        if (labOrderIds.isEmpty()) {
            return;
        }

        boolean hasPendingLabs =
                labOrderItemRepository
                        .existsByLabOrderIdInAndSampleStatusIn(
                                labOrderIds,
                                List.of(
                                        LabSampleStatus.NOT_COLLECTED,
                                        LabSampleStatus.COLLECTED,
                                        LabSampleStatus.IN_PROGRESS
                                )
                        );

        if (hasPendingLabs) {
            throw new RuntimeException(
                    "Cannot discharge patient: Pending lab tests exist"
            );
        }
    }


    private void validateMedicineClearance(Long admissionId) {

        int issuedQty = inventoryTransactionRepository
                .sumQuantityByAdmissionAndType(
                        admissionId,
                        InventoryTransactionType.OUT
                );

        int returnedQty = inventoryTransactionRepository
                .sumQuantityByAdmissionAndType(
                        admissionId,
                        InventoryTransactionType.RETURN
                );

        if (issuedQty > returnedQty) {
            throw new RuntimeException(
                    "Cannot discharge patient: Pending medicine quantity not returned"
            );
        }
    }

    private AdmissionResponse toMapAdmissionResponse(PatientAdmission patientAdmission){
        Long currentBedId = bedOccupancyRepository
                .findTopByAdmissionIdOrderByOccupiedFromDesc(patientAdmission.getAdmissionId())
                .map(BedOccupancy::getBedId)
                .orElse(null);


        return AdmissionResponse.builder()
                .admissionId(patientAdmission.getAdmissionId())
                .patientId(patientAdmission.getPatientId())
                .bedId(currentBedId)
                .admissionDate(patientAdmission.getAdmissionDate())
                .admissionType(patientAdmission.getAdmissionType())
                .reasonForAdmission(patientAdmission.getReasonForAdmission())
                .dischargeDate(patientAdmission.getDischargeDate())
                .remarks(patientAdmission.getRemarks())
                .status(patientAdmission.getStatus())
                .build();
    }
}
