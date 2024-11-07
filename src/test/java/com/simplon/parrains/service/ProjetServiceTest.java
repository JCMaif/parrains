package com.simplon.parrains.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import jakarta.persistence.EntityNotFoundException;

import com.simplon.parrains.model.Projet;
import com.simplon.parrains.repository.ProjetRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProjetServiceTest {
  
    @Mock
    ProjetRepository projetRepository;

    @InjectMocks
    ProjetService projetService;

    @Test
    @DisplayName("Retourne la liste des projets")
    void shouldReturnAllProjects() {
        //Arrange
        Projet projet1 = new Projet();
        projet1.setStartingDate(LocalDate.of(2024, 11, 05));
        projet1.setDescription("Super projet");

        Projet projet2 = new Projet();
        projet2.setStartingDate(LocalDate.of(2024, 12, 05));
        projet2.setDescription("Super méga projet");

        List<Projet> projets = List.of(projet1, projet2);

        when(projetRepository.findAll()).thenReturn(projets);

        //Act
        List<Projet> result = projetService.getAllProjets();

        //Assert
        assertEquals(2, result.size());
        assertEquals("Super projet", result.get(0).getDescription());
        assertEquals("Super méga projet", result.get(1).getDescription());
    }


    @Test
    @DisplayName("Retourne un projet par son id")
    void shouldReturnAProjetById() {
        //Arrange
        Projet projet1 = new Projet();
        projet1.setStartingDate(LocalDate.of(2024, 11, 05));
        projet1.setDescription("Super projet");

        when(projetRepository.findById(1L)).thenReturn(Optional.of(projet1));

        //Act
        Projet result = projetService.getProjetById(1L);

        //Assert
        assertNotNull(result);
        assertEquals("Super projet",result.getDescription());
    }

    @Test
    @DisplayName("Retourne une exception pour id introuvable")
    void shouldReturnAnExceptionProjetById() {
        
        when(projetRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            projetService.getProjetById(2L);

        });
        assertEquals("Projet non trouvé", exception.getMessage());
    }

}