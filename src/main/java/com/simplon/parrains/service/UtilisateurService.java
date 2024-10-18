package com.simplon.parrains.service;

import com.simplon.parrains.model.Parrain;
import com.simplon.parrains.model.Plateforme;
import com.simplon.parrains.model.Porteur;
import com.simplon.parrains.model.Utilisateur;
import com.simplon.parrains.model.dto.UtilisateurDto;
import com.simplon.parrains.model.enums.Role;
import com.simplon.parrains.repository.UtilisateurRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurService(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UtilisateurDto> getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id).map(this::convertToDto);
    }

    public UtilisateurDto createUtilisateur(UtilisateurDto utilisateurDto) {
        Utilisateur utilisateur = createUtilisateurFromDto(utilisateurDto);
        utilisateur.setPassword(passwordEncoder.encode(utilisateurDto.getPassword()));
        return convertToDto(utilisateurRepository.save(utilisateur));
    }

    public Optional<UtilisateurDto> updateUtilisateur(Long id, UtilisateurDto utilisateurDto) {
        return utilisateurRepository.findById(id).map(existingUtilisateur -> {
            updateUtilisateurFromDto(existingUtilisateur, utilisateurDto);
            return convertToDto(utilisateurRepository.save(existingUtilisateur));
        });
    }

    public void deleteUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
    }

    public List<UtilisateurDto> getAllUtilisateurs() {
        return utilisateurRepository.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }

    private Utilisateur createUtilisateurFromDto(UtilisateurDto utilisateurDto) {
        Utilisateur utilisateur;

        switch (utilisateurDto.getRole()) {
            case PORTEUR:
                utilisateur = new Porteur();
                ((Porteur) utilisateur).setDisponibilite("Disponible le mercredi après-midi");
                break;
            case PARRAIN:
                utilisateur = new Parrain();
                ((Parrain) utilisateur).setExpertise("Je suis très bon pour trouver des subventions européennes");
                ((Parrain) utilisateur).setParcours("Mon parcours est suffisant pour vous humilier dans tous les domaines");
                break;
            case PLATEFORME:
                utilisateur = new Plateforme();
                ((Plateforme) utilisateur).setTelephone("0679875609");
                break;
            default:
                throw new IllegalArgumentException("Rôle non reconnu: " + utilisateurDto.getRole());
        }

        utilisateur.setRole(utilisateurDto.getRole());
        utilisateur.setUsername(utilisateurDto.getUsername());
        utilisateur.setFirstname(utilisateurDto.getFirstname());
        utilisateur.setLastname(utilisateurDto.getLastname());
        utilisateur.setEmail(utilisateurDto.getEmail());
        utilisateur.setCompany(utilisateurDto.getCompany());

        return utilisateur;
    }

    private void updateUtilisateurFromDto(Utilisateur utilisateur, UtilisateurDto utilisateurDto) {
        utilisateur.setUsername(Optional.ofNullable(utilisateurDto.getUsername()).orElse(utilisateur.getUsername()));
        utilisateur.setFirstname(Optional.ofNullable(utilisateurDto.getFirstname()).orElse(utilisateur.getFirstname()));
        utilisateur.setLastname(Optional.ofNullable(utilisateurDto.getLastname()).orElse(utilisateur.getLastname()));
        utilisateur.setEmail(Optional.ofNullable(utilisateurDto.getEmail()).orElse(utilisateur.getEmail()));
        utilisateur.setCompany(Optional.ofNullable(utilisateurDto.getCompany()).orElse(utilisateur.getCompany()));

        if (utilisateurDto.getPassword() != null) {
            utilisateur.setPassword(passwordEncoder.encode(utilisateurDto.getPassword()));
        }

        if (utilisateur instanceof Porteur && utilisateurDto.getRole() == Role.PORTEUR) {
            ((Porteur) utilisateur).setDisponibilite("Disponible le mercredi après-midi");
        } else if (utilisateur instanceof Parrain && utilisateurDto.getRole() == Role.PARRAIN) {
            ((Parrain) utilisateur).setExpertise("Je suis très bon pour trouver des subventions européennes");
            ((Parrain) utilisateur).setParcours("Mon parcours est suffisant pour vous humilier dans tous les domaines");
        } else if (utilisateur instanceof Plateforme && utilisateurDto.getRole() == Role.PLATEFORME) {
            ((Plateforme) utilisateur).setTelephone("0679875609");
        }
    }

    private UtilisateurDto convertToDto(Utilisateur utilisateur) {
        return UtilisateurDto.builder()
                .username(utilisateur.getUsername())
                .firstname(utilisateur.getFirstname())
                .lastname(utilisateur.getLastname())
                .email(utilisateur.getEmail())
                .role(utilisateur.getRole())
                .company(utilisateur.getCompany())
                .build();
    }
}
