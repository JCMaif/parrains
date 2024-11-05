package com.simplon.parrains.service;

import com.simplon.parrains.model.Parrain;
import com.simplon.parrains.model.Porteur;
import com.simplon.parrains.model.Utilisateur;
import com.simplon.parrains.model.dto.UtilisateurDto;
import com.simplon.parrains.model.enums.Role;
import com.simplon.parrains.repository.UtilisateurRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class UtilisateurServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @InjectMocks
    private UtilisateurService utilisateurService;

    @Test
    @DisplayName("Retourner tous les utilisateurs")
    void shouldReturnAllUtilisateurs() {
      //Arrange
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

        //Act
        List<UtilisateurDto> result = utilisateurService.getAllUtilisateurs();

        //Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getUsername()).isEqualTo("porteur1");
        assertThat(result.get(1).getUsername()).isEqualTo("parrain1");

        Mockito.verify(utilisateurRepository).findAll();
    }

    @Test
    @DisplayName("Retourner un utilisateur par son id")
    void shouldReturnUtilisateurById() {
        // Arrange
        Utilisateur porteur = new Porteur();
        porteur.setId(1L); 
        porteur.setUsername("porteur1");
        porteur.setFirstname("John");
        porteur.setLastname("Doe");
        porteur.setCompany("Startup Inc.");
        porteur.setEmail("porteur1@example.com");
        porteur.setRole(Role.PORTEUR);
        porteur.setDeleted(false);
        
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(porteur));

        // Act
        Optional<UtilisateurDto> foundUser = utilisateurService.getUtilisateurById(1L);

        // Assert
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get()).usingRecursiveComparison().isEqualTo(porteur);

        Mockito.verify(utilisateurRepository).findById(1L);
    }
        

}
