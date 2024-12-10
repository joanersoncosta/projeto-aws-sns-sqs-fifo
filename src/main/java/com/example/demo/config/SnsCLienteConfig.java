package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import software.amazon.awssdk.services.sns.SnsClient;

@Configuration
@Profile("!local")
public class SnsCLienteConfig {
    @Bean
    public SnsClient snsClient() {
        return SnsClient.create();
    }
}