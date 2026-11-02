package com.noweldecor.sapins.controller;

import com.noweldecor.sapins.entity.Client;
import com.noweldecor.sapins.service.ClientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("get")
    public Client getClient(@RequestParam Long id) {
        return clientService.getClient(id);
    }

    @PostMapping("create")
    public Client createClient(@RequestParam String nom, @RequestParam String prenom,
                               @RequestParam(required = false) String sponsor) {
        return clientService.createClient(nom, prenom, sponsor);
    }

}
