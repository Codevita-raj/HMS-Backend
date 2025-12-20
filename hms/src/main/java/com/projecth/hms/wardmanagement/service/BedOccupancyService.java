package com.projecth.hms.wardmanagement.service;

import com.projecth.hms.shared.enums.BedStatus;
import com.projecth.hms.wardmanagement.dto.bedOccupancy.BedOccupancyRequest;
import com.projecth.hms.wardmanagement.dto.bedOccupancy.BedOccupancyResponse;
import com.projecth.hms.wardmanagement.entity.Bed;
import com.projecth.hms.wardmanagement.entity.BedOccupancy;
import com.projecth.hms.wardmanagement.repository.BedOccupancyRepository;
import com.projecth.hms.wardmanagement.repository.BedRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BedOccupancyService {

    private final BedRepository bedRepository;
    private final BedOccupancyRepository occupancyRepository;

    @Transactional
    public BedOccupancyResponse occupyBed(BedOccupancyRequest request) {

        //  Fetch new bed
        Bed newBed = bedRepository.findById(request.getBedId())
                .orElseThrow(() -> new IllegalStateException("Bed not found"));

        if (newBed.getStatus() != BedStatus.AVAILABLE) {
            throw new IllegalStateException("Bed is not available");
        }

        //  Find active occupancy for this admission (ONLY ONE should exist)
        occupancyRepository
                .findByAdmissionIdAndOccupiedTillIsNull(request.getAdmissionId())
                .ifPresent(activeOccupancy -> {

                    // Close previous occupancy
                    activeOccupancy.setOccupiedTill(LocalDateTime.now());
                    occupancyRepository.save(activeOccupancy);

                    // Free old bed
                    Bed oldBed = bedRepository.findById(activeOccupancy.getBedId())
                            .orElseThrow(() -> new IllegalStateException("Old bed not found"));
                    oldBed.setStatus(BedStatus.AVAILABLE);
                    bedRepository.save(oldBed);
                });

        //  Create NEW occupancy row (history preserved)
        BedOccupancy newOccupancy = new BedOccupancy();
        newOccupancy.setAdmissionId(request.getAdmissionId());
        newOccupancy.setPatientId(request.getPatientId());
        newOccupancy.setBedId(request.getBedId());
        newOccupancy.setRemarks(request.getRemarks());
        newOccupancy.setOccupiedFrom(LocalDateTime.now());

        BedOccupancy saved = occupancyRepository.save(newOccupancy);

        //  Mark new bed as occupied
        newBed.setStatus(BedStatus.OCCUPIED);
        bedRepository.save(newBed);

        return mapToResponse(saved);
    }


    public void freeBedByAdmission(Long admissionId) {

        BedOccupancy occupancy = occupancyRepository
                .findByAdmissionIdAndOccupiedTillIsNull(admissionId)
                .orElseThrow(() -> new IllegalStateException("Active occupancy not found"));

        occupancy.setOccupiedTill(LocalDateTime.now());

        Bed bed = bedRepository.findById(occupancy.getBedId())
                .orElseThrow(() -> new IllegalStateException("Bed not found"));

        bed.setStatus(BedStatus.AVAILABLE);

        occupancyRepository.save(occupancy);
        bedRepository.save(bed);
    }

    public void freeBed(Long bedId) {

        BedOccupancy occupancy = occupancyRepository
                .findByBedIdAndOccupiedTillIsNull(bedId)
                .orElseThrow(() -> new IllegalStateException("Active occupancy not found"));

        occupancy.setOccupiedTill(LocalDateTime.now());

        Bed bed = bedRepository.findById(bedId)
                .orElseThrow(() -> new IllegalStateException("Bed not found"));

        bed.setStatus(BedStatus.AVAILABLE);

        occupancyRepository.save(occupancy);
        bedRepository.save(bed);
    }

    private BedOccupancyResponse mapToResponse(BedOccupancy occupancy) {
        BedOccupancyResponse response = new BedOccupancyResponse();
        response.setOccupancyId(occupancy.getId());
        response.setBedId(occupancy.getBedId());
        response.setPatientId(occupancy.getPatientId());
        response.setAdmissionId(occupancy.getAdmissionId());
        response.setRemarks(occupancy.getRemarks());
        response.setOccupiedFrom(occupancy.getOccupiedFrom());
        response.setOccupiedTill(occupancy.getOccupiedTill());
        return response;
    }
}

