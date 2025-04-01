package bg.softuni.online_library_system.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SessionController {

    @GetMapping("session-expired")
    public String sessionExpired() {

        return "session-expired";
    }
}