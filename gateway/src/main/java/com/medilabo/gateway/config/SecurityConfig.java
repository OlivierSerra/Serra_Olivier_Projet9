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
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                // désactivation car authentification HTTP, ss session,  peu d'intérêt d'augmenter la securité
                // donc pas la peine d'avoir un loginform donc greenIT indique la légèreté
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                .authorizeExchange(exchanges -> exchanges
                        // autorise les routes sans identifications
                        .pathMatchers("/actuator/health", "/favicon.ico",
                                "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        // autorise les routes au seul admin
                        .pathMatchers("/patients/ui/**", "/notes/ui/**", "/risk/ui/**").permitAll()
                        // autorise les routes au user
                        .pathMatchers("/actuator/**").hasRole("ADMIN")
                        .pathMatchers("/patients/**", "/notes/**", "/risk/**").hasRole("USER")

                        .anyExchange().authenticated()
                )
                // activation de l'authentification HTTP Basic -1er n iveau d'authentification ->
                // a chq requete, vérification du password
                .httpBasic(Customizer.withDefaults())

                //absence de session HTTP ou Oauth
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)

                .logout(logout -> logout
                        .requiresLogout(ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/logout"))
                        .logoutSuccessHandler((webFilterExchange, authentication) -> {
                            var response = webFilterExchange.getExchange().getResponse();
                            response.setStatusCode(HttpStatus.UNAUTHORIZED);
                            response.getHeaders().set(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Realm\"");
                            return response.setComplete();
                        })
                )

                .build();
    }

    @Bean
    //porte le nom de l'import MapReactiveUserDetailsService
    //userDetailsService adapté auwebFlux. = c'est le InMemoryUserDetailsManager en spring MCV
    public MapReactiveUserDetailsService users(PasswordEncoder encoder) {
        return new MapReactiveUserDetailsService(
                User.withUsername("user").password(encoder.encode("root")).roles("USER").build(),
                User.withUsername("admin").password(encoder.encode("root")).roles("ADMIN").build()
        );
    }

    @Bean
    //encodage du mot de passe
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
