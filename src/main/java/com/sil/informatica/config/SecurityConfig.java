package com.sil.informatica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

/// Configuração central de segurança do sistema SIL-Informatica.
///
/// Define as políticas de acesso, o fluxo de login/logout e a criptografia de senhas.
@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Padrão 2026: Segurança em profundidade nos métodos de serviço
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(auth -> auth
                                                // Apenas telas de autenticação e recursos estáticos são públicos
                                                .requestMatchers("/auth/**", "/css/**", "/js/**", "/img/**", "/favicon.ico")
                                                .permitAll()
                                                // Área administrativa exige perfil ADMIN ou USER (Intérpretes)
                                                .requestMatchers("/admin/signs/delete/**").hasRole("ADMIN")
                                                .requestMatchers("/admin/signs", "/admin/signs/**").hasAnyRole("ADMIN", "USER")
                                                .requestMatchers("/admin/users", "/admin/users/**").hasRole("ADMIN")
                                                // Qualquer outra requisição (Dashboard, Favoritos) exige autenticação
                                                .anyRequest().authenticated())
                                .formLogin(login -> login
                                                .loginPage("/auth/login")
                                                .defaultSuccessUrl("/users/dashboard", true)
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutUrl("/auth/logout")
                                                .logoutRequestMatcher(PathPatternRequestMatcher.pathPattern(org.springframework.http.HttpMethod.GET, "/auth/logout"))
                                                .logoutSuccessUrl("/auth/login?logout")
                                                .invalidateHttpSession(true)
                                                .deleteCookies("JSESSIONID")
                                                .permitAll())
                                .sessionManagement(session -> session
                                                .sessionFixation().migrateSession() // Proteção contra fixação de sessão
                                                .maximumSessions(1)) // Evita logins simultâneos (opcional, mas
                                                                     // profissional)
                                .headers(headers -> headers
                                                .frameOptions(frame -> frame.sameOrigin()) // Protege contra
                                                                                           // Clickjacking
                                                .xssProtection(org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.XXssConfig::disable) // Browsers
                                                                                                                                                                     // modernos
                                                                                                                                                                     // já
                                                                                                                                                                     // fazem
                                                                                                                                                                     // isso
                                                .contentSecurityPolicy(csp -> csp.policyDirectives(
                                                                "default-src 'self'; " +
                                                                "script-src 'self' 'unsafe-inline' 'unsafe-eval' blob: https://fonts.googleapis.com https://*.vlibras.gov.br https://vlibras.gov.br https://cdn.jsdelivr.net; " +
                                                                "style-src 'self' 'unsafe-inline' https://fonts.googleapis.com https://cdn.jsdelivr.net; " +
                                                                "font-src 'self' data: https://fonts.gstatic.com https://*.vlibras.gov.br https://vlibras.gov.br https://cdn.jsdelivr.net; " +
                                                                "frame-src 'self' https://www.youtube.com https://youtube.com; " +
                                                                "img-src 'self' data: blob: https://img.youtube.com https://*.vlibras.gov.br https://vlibras.gov.br https://cdn.jsdelivr.net; " +
                                                                "connect-src 'self' https://*.vlibras.gov.br https://vlibras.gov.br https://cdn.jsdelivr.net; " +
                                                                "worker-src 'self' blob:; " +
                                                                "media-src 'self' blob:;")))
                                .csrf(csrf -> csrf
                                                .csrfTokenRepository(org.springframework.security.web.csrf.CookieCsrfTokenRepository.withHttpOnlyFalse()));

                return http.build();
        }

        /// Bean para gerenciar a autenticação, exposto para uso em outros componentes
        /// (Padrão 2026).
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }

        /// Bean para criptografia de senhas.
        /// Utiliza o DelegatingPasswordEncoder (padrão 2026) que suporta múltiplos
        /// algoritmos.
        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}
