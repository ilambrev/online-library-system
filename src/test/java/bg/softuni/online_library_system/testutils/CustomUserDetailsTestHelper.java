package bg.softuni.online_library_system.testutils;

import bg.softuni.online_library_system.model.security.CustomUserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class CustomUserDetailsTestHelper {
    public static CustomUserDetails createMockUser() {
        return new CustomUserDetails(
                "lombardo",
                "password",
                "Dave",
                "Lombardo",
                "/images/user_f.png",
                1,
                1,
                false,
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
    }
}