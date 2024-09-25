package com.simplon.parrains.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.simplon.parrains.model.enums.Privilege.*;

@RequiredArgsConstructor
public enum Role {
    ADMIN (
            Set.of(READ_PRIVILEGE, WRITE_PRIVILEGE, UPDATE_PRIVILEGE, DELETE_PRIVILEGE)
    ),
    PORTEUR (
            Set.of(READ_PRIVILEGE, WRITE_PRIVILEGE, UPDATE_PRIVILEGE, DELETE_PRIVILEGE)
    ),
    PARRAIN (
            Set.of(READ_PRIVILEGE, WRITE_PRIVILEGE, UPDATE_PRIVILEGE, DELETE_PRIVILEGE)
    );

    @Getter
    private final Set<Privilege> privileges;

    public List<SimpleGrantedAuthority> getAuthorities(){
        List<SimpleGrantedAuthority> authorities = getPrivileges()
                .stream()
                .map(privilege -> new SimpleGrantedAuthority(privilege.name()))
                .collect(Collectors.toList());
            authorities.add(new SimpleGrantedAuthority("Role_"+this.name()));
            return authorities;
    }
}
