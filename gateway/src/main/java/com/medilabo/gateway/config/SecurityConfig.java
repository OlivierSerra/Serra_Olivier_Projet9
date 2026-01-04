package com.medilabo.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

import java.net.URI;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                //Permet de ne pas utiliser de token
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                .authorizeExchange(exchanges -> exchanges
                        // tout le monde peut arriver sur page Login
                        //pour le reste, authentification exigée
                        .pathMatchers("/login", "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        .anyExchange().authenticated()
                )
                //authentification par formulaire
                .formLogin(form -> form.loginPage("/login"))
                //procédure de deconnexion => avec redirection vers /login
                //
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(
                                new RedirectServerLogoutSuccessHandler() {{
                                    setLogoutSuccessUrl(URI.create("/login"));
                                }}
                        )
                )
                //Pas authentoiocation HTTP Basic, meme si route protégée
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .build();
    }

    @Bean
    // MapReactiveUserDetailsService = UserDetailsService en reactive
    public MapReactiveUserDetailsService users(PasswordEncoder encoder) {
        return new MapReactiveUserDetailsService(
                User.withUsername("user")
                        .password(encoder.encode("root"))
                        .roles("USER")   // rôle “symbolique”, pas de gestion de droits
                        .build()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
