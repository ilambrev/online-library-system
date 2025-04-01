package bg.softuni.online_library_system.util;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import static bg.softuni.online_library_system.common.constant.CartConstants.INITIAL_CART_COUNT;

@Component
public class AuthenticationSuccessListener {
    private final HttpSession session;

    @Autowired
    public AuthenticationSuccessListener(HttpSession session) {
        this.session = session;
    }

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();

        if (authentication.isAuthenticated()) {
            session.setAttribute("cartCount", INITIAL_CART_COUNT);
        }
    }
}