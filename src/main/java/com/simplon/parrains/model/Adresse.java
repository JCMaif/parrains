package com.simplon.parrains.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "adresse")
public class Adresse extends BaseEntity{
    private String rue;
    private String cpostal;
    private String ville;
}
