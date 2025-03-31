package bg.softuni.online_library_system.web;

import bg.softuni.online_library_system.model.dto.UserChangeRoleDTO;
import bg.softuni.online_library_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getAllUsers(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "6") int size,
                              Model model) {

        Page<UserChangeRoleDTO> booksPage = this.userService.getAllUsersOrderByFirstName(page, size);

        model.addAttribute("usersCount", booksPage.getTotalElements());
        model.addAttribute("users", booksPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", booksPage.getTotalPages());

        return "users";
    }
}