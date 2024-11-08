package com.simplon.parrains.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.simplon.parrains.model.Admin;
import com.simplon.parrains.model.Parrain;
import com.simplon.parrains.model.Plateforme;
import com.simplon.parrains.model.Porteur;
import com.simplon.parrains.model.Utilisateur;
import com.simplon.parrains.model.enums.Role;
import com.simplon.parrains.repository.UtilisateurRepository;

@Configuration
public class UserInitializer {

    @Bean
    public CommandLineRunner initUsers(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Création de l'utilisateur Admin si non existant
            if (utilisateurRepository.findByUsername("adminuser").isEmpty()) {
                Utilisateur admin = new Admin();
                admin.setUsername("Admin");
                admin.setFirstname("Admin");
                admin.setLastname("Admin");
                admin.setPassword(passwordEncoder.encode("adminpassword"));
                admin.setEmail("admin@example.com");
                admin.setRole(Role.ADMIN);
                admin.setEnabled(true);
                utilisateurRepository.save(admin);
            }

            // Création du Porteur si non existant
            if (utilisateurRepository.findByUsername("porteuruser").isEmpty()) {
                Porteur porteur = new Porteur();
                porteur.setUsername("porteuruser");
                porteur.setFirstname("Porteur");
                porteur.setLastname("User");
                porteur.setPassword(passwordEncoder.encode("porteurpassword"));
                porteur.setEmail("porteur@example.com");
                porteur.setRole(Role.PORTEUR);
                porteur.setEnabled(true);
                porteur.setDisponibilite("Disponible les weekends");
                utilisateurRepository.save(porteur);
            }

            // Création du Parrain si non existant
            if (utilisateurRepository.findByUsername("parrainuser").isEmpty()) {
                Parrain parrain = new Parrain();
                parrain.setUsername("parrainuser");
                parrain.setFirstname("Parrain");
                parrain.setLastname("User");
                parrain.setPassword(passwordEncoder.encode("parrainpassword"));
                parrain.setEmail("parrain@example.com");
                parrain.setRole(Role.PARRAIN);
                parrain.setEnabled(true);
                parrain.setParcours("Ingénieur");
                parrain.setExpertise("Développement logiciel");
                parrain.setDeplacements("Région Nouvelle-Aquitaine");
                parrain.setDisponibilites("Lundi et mercredi");
                utilisateurRepository.save(parrain);
            }

            // Création de la Plateforme si non existant
            if (utilisateurRepository.findByUsername("platformeuser").isEmpty()) {
                Plateforme plateforme = new Plateforme();
                plateforme.setUsername("platformeuser");
                plateforme.setFirstname("Initiative");
                plateforme.setLastname("Deux Sèvres");
                plateforme.setPassword(passwordEncoder.encode("platformepassword"));
                plateforme.setEmail("accompagnement@initiativedeuxsevres.fr");
                plateforme.setRole(Role.PLATEFORME);
                plateforme.setEnabled(true);
                plateforme.setTelephone("0679875609");
                utilisateurRepository.save(plateforme);
            }
        };
    }
}
