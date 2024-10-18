package com.simplon.parrains.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "deleted", nullable = false)
    private boolean isDeleted = false;

    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @PrePersist
    public void onPrePersist() {
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
        this.createdBy = getCurrentUser();
        this.updatedBy = getCurrentUser();
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updatedAt = LocalDate.now();
        this.updatedBy = getCurrentUser();
    }

    private String getCurrentUser() {
        try {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e) {
            return "SYSTEM";  // Valeur par défaut si l'utilisateur n'est pas authentifié
        }
    }
}
