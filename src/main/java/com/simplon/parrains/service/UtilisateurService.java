package com.simplon.parrains.service;

import com.simplon.parrains.model.Admin;
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
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class UtilisateurService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurService(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UtilisateurDto> getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id).map(this::convertToDto);
    }

    public Optional<UtilisateurDto> createUtilisateurWithActivationToken(UtilisateurDto utilisateurDto) {
        try {
            Utilisateur utilisateur = createUtilisateurFromDto(utilisateurDto);
            utilisateur.setEnabled(false);
            utilisateur.setActivationToken(generateActivationCode());
            Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
            return Optional.of(convertToDto(savedUtilisateur));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
    
    public Optional<UtilisateurDto> updateUtilisateur(Long id, UtilisateurDto utilisateurDto) {
        return utilisateurRepository.findById(id).map(existingUtilisateur -> {
            updateUtilisateurFromDto(existingUtilisateur, utilisateurDto);
            return convertToDto(utilisateurRepository.save(existingUtilisateur));
        });
    }

    public Optional<UtilisateurDto> finalizeRegistration(String activationToken, String password) {
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findByActivationToken(activationToken);

        if (optionalUtilisateur.isPresent()) {
            Utilisateur utilisateur = optionalUtilisateur.get();
            utilisateur.setPassword(passwordEncoder.encode(password));
            utilisateur.setEnabled(true);
            utilisateur.setActivationToken(null);
            Utilisateur updatedUtilisateur = utilisateurRepository.save(utilisateur);
            return Optional.of(convertToDto(updatedUtilisateur));
        }
        return Optional.empty();
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
            case PORTEUR -> {
                Porteur porteur = new Porteur();
                porteur.setDisponibilite(utilisateurDto.getDisponibilite());
                utilisateur = porteur;
            }
            case PARRAIN -> {
                Parrain parrain = new Parrain();
                parrain.setExpertise(utilisateurDto.getExpertise());
                parrain.setParcours(utilisateurDto.getParcours());
                utilisateur = parrain;
            }
            case PLATEFORME -> {
                utilisateur = new Plateforme();
            }
            case ADMIN -> {
                utilisateur = new Admin();
            }
            default -> throw new IllegalArgumentException("Rôle non reconnu: " + utilisateurDto.getRole());
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
            ((Porteur) utilisateur).setDisponibilite(utilisateurDto.getDisponibilite());
        } else if (utilisateur instanceof Parrain && utilisateurDto.getRole() == Role.PARRAIN) {
            ((Parrain) utilisateur).setExpertise(utilisateurDto.getExpertise());
            ((Parrain) utilisateur).setParcours(utilisateurDto.getParcours());
        }
    }

    private String generateActivationCode() {
        return UUID.randomUUID().toString();
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur inconnu"));
    }
}
