package com.bibliotecario.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuracao de seguranca para desenvolvimento: libera todas as rotas e
 * desativa o CSRF para facilitar os testes da API (Postman / Insomnia).
 * Ajustar antes de ir para producao.
 */
@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
		return http.build();
	}
}
