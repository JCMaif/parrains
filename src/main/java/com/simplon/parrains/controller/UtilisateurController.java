package com.simplon.parrains.controller;

import com.simplon.parrains.model.dto.UtilisateurDto;
import com.simplon.parrains.service.UtilisateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@Tag(name = "Gestion des utilisateurs", description = "Opérations liées aux utilisateurs")
@RequestMapping("/admin/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<UtilisateurDto>> createUtilisateur(@RequestBody UtilisateurDto utilisateurDto) {
        Optional<UtilisateurDto> createdUtilisateur = utilisateurService.createUtilisateurWithActivationToken(utilisateurDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUtilisateur);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UtilisateurDto> updateUtilisateur(@PathVariable Long id, @RequestBody UtilisateurDto utilisateurDto) {
        Optional<UtilisateurDto> updatedUtilisateur = utilisateurService.updateUtilisateur(id, utilisateurDto);
        return updatedUtilisateur.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        utilisateurService.deleteUtilisateur(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Liste des utilisateurs", description = "Récupère la liste de tous les utilisateurs enregistrés")
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<UtilisateurDto> getAllUtilisateurs() {
        return utilisateurService.getAllUtilisateurs();
    }

    @Operation(summary = "Détails d'un utilisateur", description = "Récupère les détails d'un utilisateur en fonction de son identifiant")
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UtilisateurDto> getUtilisateurById(@PathVariable Long id) {
        Optional<UtilisateurDto> utilisateurDto = utilisateurService.getUtilisateurById(id);
        return utilisateurDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Finaliser l'inscription", description = "Permet à un utilisateur de finaliser son inscription en utilisant un token d'activation")
    @PostMapping("/signup")
    public ResponseEntity<?> finaliserProfil(@RequestBody Map<String, String> requestBody) {
        String activationToken = requestBody.get("activationToken");
        String password = requestBody.get("password");
        Optional<UtilisateurDto> utilisateurDto = utilisateurService.finalizeRegistration(activationToken, password);
        
        return ResponseEntity.status(HttpStatus.OK).body(utilisateurDto);
    }
    
}
