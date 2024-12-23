package com.simplon.parrains.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simplon.parrains.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UtilisateurDto {

    @NotNull(message = "Username is required")
    private String username;
    private String firstname;
    private String lastname;
    private String company;

    @Email(message = "Invalid email address")
    @NotNull(message = "Email is required")
    private String email;

    @NotNull
    private Role role;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String disponibilite; // pour Porteur
    private String expertise;     // pour Parrain
    private String parcours;      // pour Parrain
}
