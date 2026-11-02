package com.noweldecor.sapins.service;

import com.noweldecor.sapins.entity.BonDeCommande;
import com.noweldecor.sapins.entity.Client;
import com.noweldecor.sapins.entity.Decoration;
import com.noweldecor.sapins.entity.Sapin;
import com.noweldecor.sapins.repository.BonDeCommandeRepository;
import com.noweldecor.sapins.repository.ClientRepository;
import com.noweldecor.sapins.repository.DecorationRepository;
import com.noweldecor.sapins.repository.SapinRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SapinService {

    private final SapinRepository sapinRepository;
    private final DecorationRepository decorationRepository;
    private final ClientRepository clientRepository;
    private final BonDeCommandeRepository bonDeCommandeRepository;

    public SapinService(SapinRepository sapinRepository, DecorationRepository decorationRepository,
                        ClientRepository clientRepository, BonDeCommandeRepository bonDeCommandeRepository) {
        this.sapinRepository = sapinRepository;
        this.decorationRepository = decorationRepository;
        this.clientRepository = clientRepository;
        this.bonDeCommandeRepository = bonDeCommandeRepository;
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

    @Transactional
    public BonDeCommande vente(Long idSapin, Long idClient) {
        Sapin sapin = getSapin(idSapin);
        Client client = clientRepository.findById(idClient).orElse(null);

        if (sapin == null || client == null || sapin.isVendu()) {
            return null;
        }

        sapin.setVendu(true);
        sapinRepository.save(sapin);

        // on additionne le prix et le poids de chaque decoration du sapin
        int coutTotal = 0;
        int poidsTotal = 0;
        for (Decoration decoration : sapin.getDecorations()) {
            coutTotal += decoration.getPrixEnCentime();
            poidsTotal += decoration.getPoidsEnGram();
        }

        BonDeCommande bonDeCommande = BonDeCommande.builder()
                .adresse("Mael rue du bonjour")
                .coutTotal(coutTotal)
                .poidsTotal(poidsTotal)
                .sapin(sapin)
                .client(client)
                .build();
        bonDeCommandeRepository.save(bonDeCommande);

        // le client gagne 10 pourcent de son achat en points, la division entiere arrondit vers le bas
        client.setPointsFidelite(client.getPointsFidelite() + coutTotal / 10);
        clientRepository.save(client);

        return bonDeCommande;
    }

    public BonDeCommande getBonDeCommande(Long id) {
        return bonDeCommandeRepository.findById(id).orElse(null);
    }

}
