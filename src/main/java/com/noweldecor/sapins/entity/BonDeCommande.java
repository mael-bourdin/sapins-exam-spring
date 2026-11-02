package com.noweldecor.sapins.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BonDeCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String adresse;

    private int coutTotal;

    private int poidsTotal;

    @OneToOne
    private Sapin sapin;

    @ManyToOne
    private Client client;

}
