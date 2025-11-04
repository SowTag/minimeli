package dev.maddock.minimeli.servicecommons.config;

import dev.maddock.minimeli.servicecommons.config.customizers.HttpSecurityOAuth2ResourceServerCustomizer;
import dev.maddock.minimeli.servicecommons.config.customizers.HttpSecurityRequestMatchersCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(List<Customizer<HttpSecurity>> customizers, HttpSecurity http) throws Exception {

        for (Customizer<HttpSecurity> customizer : customizers) {
            customizer.customize(http);
        }

        return http.build();
    }

    @Bean
    @ConditionalOnMissingBean(HttpSecurityOAuth2ResourceServerCustomizer.class)
    public HttpSecurityOAuth2ResourceServerCustomizer oauth2ResourceServerConfig() {
        return http -> {
            try {
                http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(HttpSecurityRequestMatchersCustomizer.class)
    public HttpSecurityRequestMatchersCustomizer requestMatchersConfig() {
        return http -> {
            try {
                http.authorizeHttpRequests(req -> {
                    req.requestMatchers("/error", "/actuator/**").permitAll();
                    req.anyRequest().authenticated();
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
