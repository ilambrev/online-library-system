package bg.softuni.online_library_system.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEventPublisher implements AuthenticationEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public CustomAuthenticationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
        applicationEventPublisher.publishEvent(new AuthenticationSuccessEvent(authentication));
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        applicationEventPublisher.publishEvent(new AuthenticationFailureBadCredentialsEvent(authentication, exception));
    }
}