package dev.maddock.minimeli.productsservice.config;

import dev.maddock.minimeli.servicecommons.config.customizers.AuthorizationRequestMatcherCustomizer;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

@Component
public class RequestAuthorizer implements AuthorizationRequestMatcherCustomizer {
    @Override
    public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizationManagerRequestMatcherRegistry) {
        authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll();
    }
}
