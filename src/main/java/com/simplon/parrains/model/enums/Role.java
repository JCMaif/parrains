package com.simplon.parrains.model.enums;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;


@RequiredArgsConstructor
public enum Role {
    ADMIN ,
    PORTEUR,
    PLATEFORME,
    PARRAIN ;


    public List<SimpleGrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.name()));
    }

}
