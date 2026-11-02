package com.noweldecor.sapins.service;

import com.noweldecor.sapins.entity.Client;
import com.noweldecor.sapins.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client getClient(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public Client createClient(String nom, String prenom, String sponsor) {
        Client client = Client.builder()
                .nom(nom)
                .prenom(prenom)
                .sponsor(sponsor)
                .pointsFidelite(0)
                .build();
        return clientRepository.save(client);
    }

}
