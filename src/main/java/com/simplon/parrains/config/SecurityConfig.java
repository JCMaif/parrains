package com.simplon.parrains.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
    //    return http
    //             .authorizeHttpRequests(auth -> auth
    //                     .requestMatchers("/", "/login", "/signup/**", "/css/**", "/img/favicon.ico", "/img/**").permitAll()
    //                     .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
    //                     .requestMatchers("/admin").hasRole("ADMIN")
    //             )
    //             .formLogin(login -> login
    //                     .loginPage("/login")
    //                     .loginProcessingUrl("/login")
    //                     // .defaultSuccessUrl("/restaurants-list", true)
    //                     .permitAll()
    //             )
    //             .logout(logout -> logout
    //                     .logoutSuccessUrl("/")
    //                     .permitAll()
    //             )
    //             .build();
    // }
}
