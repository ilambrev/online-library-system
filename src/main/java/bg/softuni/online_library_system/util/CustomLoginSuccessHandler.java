package bg.softuni.online_library_system.util;

import bg.softuni.online_library_system.model.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        boolean hasOverdueBooks = userDetails.hasOverdueBooks();

        if (hasOverdueBooks) {
            response.sendRedirect("/books/overdue-warning");
        } else {
            response.sendRedirect("/");
        }
    }
}