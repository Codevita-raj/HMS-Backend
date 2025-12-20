package com.projecth.hms.lab.controller;

import com.projecth.hms.lab.dto.labOrder.LabResultRequest;
import com.projecth.hms.lab.dto.labOrder.SampleCollectRequest;
import com.projecth.hms.lab.service.LabSampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LabSampleController {

    private final LabSampleService labSampleService;

    @PatchMapping("/lab/samples/{itemId}/collect")
    public ResponseEntity<Void> collectSample(
            @PathVariable Long itemId,
            @RequestBody SampleCollectRequest request) {

        labSampleService.collectSample(itemId, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/lab/samples/{itemId}/result")
    public ResponseEntity<Void> enterResult(
            @PathVariable Long itemId,
            @RequestBody LabResultRequest request) {

        labSampleService.enterResult(itemId, request);
        return ResponseEntity.ok().build();
    }
}

