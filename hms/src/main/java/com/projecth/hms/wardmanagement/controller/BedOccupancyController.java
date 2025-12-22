package com.projecth.hms.wardmanagement.controller;

import com.projecth.hms.wardmanagement.dto.bedOccupancy.BedOccupancyRequest;
import com.projecth.hms.wardmanagement.dto.bedOccupancy.BedOccupancyResponse;
import com.projecth.hms.wardmanagement.service.BedOccupancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BedOccupancyController {

    private final BedOccupancyService occupancyService;

    @PreAuthorize("hasAnyRole('NURSE')")
    @PostMapping("/bed/occupancy/occupy")
    public ResponseEntity<BedOccupancyResponse> occupy(
            @RequestBody BedOccupancyRequest request) {

        return ResponseEntity.ok(
                occupancyService.occupyBed(request)
        );
    }
    @PreAuthorize("hasAnyRole('NURSE')")
    @PostMapping("/bed/occupancy/free/{bedId}")
    public ResponseEntity<Void> free(@PathVariable Long bedId) {
        occupancyService.freeBed(bedId);
        return ResponseEntity.ok().build();
    }
}


