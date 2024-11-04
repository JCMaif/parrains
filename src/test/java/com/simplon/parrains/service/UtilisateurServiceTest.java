package com.simplon.parrains.service;

import com.simplon.parrains.model.Parrain;
import com.simplon.parrains.model.Porteur;
import com.simplon.parrains.model.Utilisateur;
import com.simplon.parrains.model.dto.UtilisateurDto;
import com.simplon.parrains.model.enums.Role;
import com.simplon.parrains.repository.UtilisateurRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class UtilisateurServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @InjectMocks
    private UtilisateurService utilisateurService;

    @Test
    void shouldReturnAllUtilisateurs() {
      
        Utilisateur porteur = new Porteur();
        porteur.setUsername("porteur1");
        porteur.setFirstname("John");
        porteur.setLastname("Doe");
        porteur.setCompany("Startup Inc.");
        porteur.setEmail("porteur1@example.com");
        porteur.setRole(Role.PORTEUR);

        Utilisateur parrain = new Parrain();
        parrain.setUsername("parrain1");
        parrain.setFirstname("Jane");
        parrain.setLastname("Smith");
        parrain.setCompany("Mentor Ltd.");
        parrain.setEmail("parrain1@example.com");
        parrain.setRole(Role.PARRAIN);

        when(utilisateurRepository.findAll()).thenReturn(List.of(porteur, parrain));

        List<UtilisateurDto> result = utilisateurService.getAllUtilisateurs();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getUsername()).isEqualTo("porteur1");
        assertThat(result.get(1).getUsername()).isEqualTo("parrain1");

        Mockito.verify(utilisateurRepository).findAll();
    }
}
