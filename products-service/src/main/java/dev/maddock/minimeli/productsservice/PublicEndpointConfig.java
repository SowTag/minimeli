package dev.maddock.minimeli.productsservice;

import dev.maddock.minimeli.servicecommons.config.customizers.AuthorizationRequestMatcherCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PublicEndpointConfig {
    @Bean
    public AuthorizationRequestMatcherCustomizer publicEndpoints() {
        return req -> {
            req.requestMatchers("/public").permitAll();
        };
    }
}
