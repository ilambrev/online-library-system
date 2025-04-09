package bg.softuni.online_library_system.service;

import bg.softuni.online_library_system.model.dto.UserChangePasswordDTO;
import bg.softuni.online_library_system.model.dto.UserChangeRoleDTO;
import bg.softuni.online_library_system.model.dto.UserProfileDTO;
import bg.softuni.online_library_system.model.dto.UserRegistrationDTO;
import bg.softuni.online_library_system.model.entity.UserEntity;
import bg.softuni.online_library_system.model.enums.UserRoleEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface UserService {

    void registerUser(UserRegistrationDTO userRegistrationDTO) throws IOException;

    UserProfileDTO getUserProfileData(String username);

    void editUser(UserProfileDTO userProfileDTO, HttpServletRequest request,
                     HttpServletResponse response) throws IOException;

    void changeUserPassword(String username, UserChangePasswordDTO userChangePasswordDTO);

    UserEntity getUserByUsername(String username);

    Page<UserChangeRoleDTO> getAllUsersOrderByFirstName(int page, int size);

    UserChangeRoleDTO getUserDataToChangeRole(Long id);

    void changeUserRole(Long id, UserRoleEnum role);

    void refreshAuthenticatedUser(String username, HttpServletRequest request, HttpServletResponse response);
}