package com.noweldecor.sapins.service;

import com.noweldecor.sapins.entity.Sapin;
import com.noweldecor.sapins.repository.SapinRepository;
import org.springframework.stereotype.Service;

@Service
public class SapinService {

    private final SapinRepository sapinRepository;

    public SapinService(SapinRepository sapinRepository) {
        this.sapinRepository = sapinRepository;
    }

    public Sapin getSapin(Long id) {
        return sapinRepository.findById(id).orElse(null);
    }

    public long createSapin() {
        sapinRepository.save(new Sapin());
        return sapinRepository.count();
    }

}
