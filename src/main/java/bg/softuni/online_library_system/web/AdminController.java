package bg.softuni.online_library_system.web;

import bg.softuni.online_library_system.model.dto.UserChangeRoleDTO;
import bg.softuni.online_library_system.model.enums.GenderEnum;
import bg.softuni.online_library_system.model.enums.UserRoleEnum;
import bg.softuni.online_library_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/users/{id}/change-role")
    public String changeUserRole(@PathVariable("id") Long id, Model model) {
        model.addAttribute("userChangeRoleDTO", this.userService.getUserDataToChangeRole(id));
        model.addAttribute("roles", UserRoleEnum.values());

        return "profile-change-role";
    }

    @PatchMapping("/users/{id}/change-role")
    public String changeUserRole(@PathVariable("id") Long id, UserChangeRoleDTO userChangeRoleDTO) {
        this.userService.changeUserRole(id, userChangeRoleDTO.getRole());

        return String.format("redirect:/admin/users/%d/change-role", id);
    }
}