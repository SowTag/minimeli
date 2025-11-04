package dev.maddock.minimeli.servicecommons.config.customizers;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

@FunctionalInterface
public interface AuthorizationRequestMatcherCustomizer extends Customizer
        <AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> {
}
