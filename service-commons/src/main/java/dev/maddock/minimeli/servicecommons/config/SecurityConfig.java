package dev.maddock.minimeli.servicecommons.config;

import dev.maddock.minimeli.servicecommons.config.customizers.AuthorizationRequestMatcherCustomizer;
import dev.maddock.minimeli.servicecommons.config.customizers.HttpSecurityOAuth2ResourceServerCustomizer;
import dev.maddock.minimeli.servicecommons.config.customizers.HttpSecurityRequestMatchersCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig {
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Value("${minimeli.auth.client-id}")
    private String clientId;

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
                http.oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        if (clientId == null || clientId.isBlank()) {
            log.error("Client ID not set. Authority converters may not work!");
        }

        Converter<Jwt, Collection<GrantedAuthority>> defaultAuthorityConverter = new JwtGrantedAuthoritiesConverter();

        Converter<Jwt, Collection<GrantedAuthority>> keycloakAuthorityConverter = new KeycloakJwtAuthoritiesConverter(clientId);

        Converter<Jwt, Collection<GrantedAuthority>> compositeAuthorityConverter = jwt -> {

            Collection<GrantedAuthority> defaultAuthorities = defaultAuthorityConverter.convert(jwt);

            if (defaultAuthorities == null) {
                defaultAuthorities = Collections.emptySet();
            }

            Collection<GrantedAuthority> keycloakAuthorities = keycloakAuthorityConverter.convert(jwt);

            if (keycloakAuthorities == null) {
                keycloakAuthorities = Collections.emptySet();
            }

            return Stream.concat(
                    defaultAuthorities.stream(),
                    keycloakAuthorities.stream()
            ).collect(Collectors.toSet());
        };

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(compositeAuthorityConverter);

        return converter;
    }

    @Bean
    @ConditionalOnMissingBean(HttpSecurityRequestMatchersCustomizer.class)
    public HttpSecurityRequestMatchersCustomizer requestMatchersConfig(List<AuthorizationRequestMatcherCustomizer> authorizationRequestMatcherCustomizers) {
        log.info("Applying {} AuthorizationRequestMatcherCustomizer(s)", authorizationRequestMatcherCustomizers.size());

        return http -> {
            try {
                http.authorizeHttpRequests(req -> {
                    for (AuthorizationRequestMatcherCustomizer customizer : authorizationRequestMatcherCustomizers) {
                        customizer.customize(req);
                    }

                    req.requestMatchers("/error", "/actuator/**").permitAll();
                    req.anyRequest().authenticated();
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
