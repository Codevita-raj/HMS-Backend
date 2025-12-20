package com.projecth.hms.wardmanagement.service;

import com.projecth.hms.shared.enums.BedStatus;
import com.projecth.hms.wardmanagement.dto.bed.BedRequest;
import com.projecth.hms.wardmanagement.dto.bed.BedResponse;
import com.projecth.hms.wardmanagement.entity.Bed;
import com.projecth.hms.wardmanagement.repository.BedRepository;
import com.projecth.hms.wardmanagement.repository.WardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BedService {

    private final BedRepository bedRepo;
    private final WardRepository wardRepo;

    public BedResponse create(BedRequest req) {

        wardRepo.findById(req.getWardId())
                .orElseThrow(() -> new RuntimeException("Ward not found"));

        if (bedRepo.existsByWardIdAndBedNumber(req.getWardId(), req.getBedNumber())) {
            throw new RuntimeException("Bed already exists");
        }

        Bed bed = new Bed();
        bed.setBedNumber(req.getBedNumber());
        bed.setWardId(req.getWardId());
        bed.setStatus(BedStatus.AVAILABLE);

        return map(bedRepo.save(bed));
    }

    public BedResponse get(Long id) {
        return map(getBed(id));
    }

    public List<BedResponse> getByWard(Long wardId) {
        return bedRepo.findAll().stream()
                .filter(b -> b.getWardId().equals(wardId))
                .map(this::map)
                .toList();
    }

    public BedResponse update(Long id, BedRequest req) {

        Bed bed = getBed(id);

        if (bed.getStatus() == BedStatus.OCCUPIED) {
            throw new RuntimeException("Cannot update occupied bed");
        }

        bed.setBedNumber(req.getBedNumber());
        return map(bedRepo.save(bed));
    }

    public void markMaintenance(Long id) {
        Bed bed = getBed(id);
        bed.setStatus(BedStatus.MAINTENANCE);
        bedRepo.save(bed);
    }

    private Bed getBed(Long id) {
        return bedRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Bed not found"));
    }

    private BedResponse map(Bed bed) {
        BedResponse r = new BedResponse();
        r.setId(bed.getId());
        r.setBedNumber(bed.getBedNumber());
        r.setStatus(bed.getStatus());
        r.setWardId(bed.getWardId());
        return r;
    }
}

