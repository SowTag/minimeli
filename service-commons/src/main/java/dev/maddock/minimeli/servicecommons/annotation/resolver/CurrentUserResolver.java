package dev.maddock.minimeli.servicecommons.annotation.resolver;

import dev.maddock.minimeli.servicecommons.annotation.CurrentUser;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.UUID;


/**
 * Resolver for the {@link CurrentUser} annotation, resolves the authenticated principal username as a UUID and
 * allows using it as a controller method argument, like
 * {@code @GetMapping void getCurrentUser(@CurrentUserUUID UUID userId)}
 *
 * @see org.springframework.web.method.support.HandlerMethodArgumentResolver
 */
@Component
public class CurrentUserResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class) &&
                parameter.getParameterType().equals(UUID.class);
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public UUID resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        try {
            return UUID.fromString(auth.getName());
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("Principal is not authenticated with a valid UUID as its username");
        }
    }
}
