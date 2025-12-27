package com.medilabo.RiskService.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    // ouvre connexion => env req =>retour reponse =>lit donnees=> dispatch données
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
