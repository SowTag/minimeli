package dev.maddock.minimeli.servicecommons.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

class KeycloakJwtAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private static final String RESOURCE_ACCESS_KEY = "resource_access";
    private static final String ROLES_KEY = "roles";

    private final String clientId;

    public KeycloakJwtAuthoritiesConverter(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {

        if (clientId == null || clientId.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, Object> resourceAccess = source.getClaimAsMap(RESOURCE_ACCESS_KEY);

        if (resourceAccess == null || resourceAccess.isEmpty() || !resourceAccess.containsKey(clientId)) {
            return Collections.emptyList();
        }

        // $.resource_access.<client ID>
        Object clientAccessObject = resourceAccess.get(clientId);
        if (!(clientAccessObject instanceof Map<?, ?> clientAccess)) {
            return Collections.emptyList();
        }

        // $.resource_access.<client ID>.roles
        Object rolesObject = clientAccess.get(ROLES_KEY);
        if (!(rolesObject instanceof Collection<?> rolesCollection)) {
            return Collections.emptyList();
        }

        return rolesCollection.stream()
                .filter(String.class::isInstance)       // Check that element is a String
                .map(String.class::cast)                // Cast to String (now safe)
                .map(SimpleGrantedAuthority::new)       // Create the authority
                .collect(Collectors.toList());
    }
}
