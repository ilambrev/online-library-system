package bg.softuni.online_library_system.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserProfileController {

    @GetMapping("/profile")
    public String getUserProfile(@RequestParam String userName) {

        return "profile";
    }
}