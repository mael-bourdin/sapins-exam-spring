package com.noweldecor.sapins.service;

import com.noweldecor.sapins.entity.Decoration;
import com.noweldecor.sapins.entity.Sapin;
import com.noweldecor.sapins.repository.DecorationRepository;
import com.noweldecor.sapins.repository.SapinRepository;
import org.springframework.stereotype.Service;

@Service
public class SapinService {

    private final SapinRepository sapinRepository;
    private final DecorationRepository decorationRepository;

    public SapinService(SapinRepository sapinRepository, DecorationRepository decorationRepository) {
        this.sapinRepository = sapinRepository;
        this.decorationRepository = decorationRepository;
    }

    public Sapin getSapin(Long id) {
        return sapinRepository.findById(id).orElse(null);
    }

    public long createSapin() {
        sapinRepository.save(new Sapin());
        return sapinRepository.count();
    }

    public boolean addDecoration(Long idSapin, Long idDecoration) {
        Sapin sapin = getSapin(idSapin);
        Decoration decoration = decorationRepository.findById(idDecoration).orElse(null);

        // on refuse si le sapin ou la decoration existe pas ou si le sapin est deja vendu
        if (sapin == null || decoration == null || sapin.isVendu()) {
            return false;
        }

        sapin.getDecorations().add(decoration);
        sapinRepository.save(sapin);
        return true;
    }

}
