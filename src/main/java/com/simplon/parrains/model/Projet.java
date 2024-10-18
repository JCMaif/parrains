package com.simplon.parrains.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projet")
public class Projet extends BaseEntity{

    private LocalDate startingDate; // Date initiale (du lancement ou de la reprise d’activité)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "projet_besoin",
            joinColumns = @JoinColumn(name = "projet_id"),
            inverseJoinColumns = @JoinColumn(name = "besoin_id")
    )
    private List<Besoin> besoins;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "projet_domaine",
            joinColumns = @JoinColumn(name = "projet_id"),
            inverseJoinColumns = @JoinColumn(name = "domaine_id")
    )
    private List<Domaine> domaines;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "porteur_id", nullable = false)
    private Porteur porteur;
}
