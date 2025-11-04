package dev.maddock.minimeli.servicecommons.config.customizers;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface HttpSecurityOAuth2ResourceServerCustomizer extends Customizer<HttpSecurity> {
}
