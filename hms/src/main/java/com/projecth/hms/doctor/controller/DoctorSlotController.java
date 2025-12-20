package com.projecth.hms.doctor.controller;

import java.time.LocalDate;
import java.util.List;

import com.projecth.hms.doctor.dto.DoctorSlotResponse;
import com.projecth.hms.doctor.service.DoctorSlotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DoctorSlotController {

    private final DoctorSlotService doctorSlotService;

    @PostMapping("/doctors/slot")
    public ResponseEntity<Void> generateSlots(
            @RequestParam int days
    ) {
        doctorSlotService.generateSlotsForNextDays(days);
        return ResponseEntity.ok().build();
    }
}

