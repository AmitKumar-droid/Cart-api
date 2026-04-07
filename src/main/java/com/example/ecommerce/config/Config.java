package com.example.ecommerce.config;

import com.example.ecommerce.utility.JwtFilter;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class Config {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // Public APIs
                        .requestMatchers("/api/auth/**").permitAll()

                        // ADMIN only
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // USER + ADMIN
                        .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")

                        // All others need authentication
                        .anyRequest().authenticated()
                )
                .addFilterBefore((Filter) jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}