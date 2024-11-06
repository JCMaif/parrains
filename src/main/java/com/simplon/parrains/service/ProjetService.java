package com.simplon.parrains.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplon.parrains.model.Projet;
import com.simplon.parrains.repository.ProjetRepository;

@Service
public class ProjetService {

    @Autowired
    private ProjetRepository projetRepository;

    public List<Projet> getAllProjets() {
        return projetRepository.findAll();
    }

}
