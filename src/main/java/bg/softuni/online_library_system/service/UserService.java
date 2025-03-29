package bg.softuni.online_library_system.service;

import bg.softuni.online_library_system.model.dto.UserChangePasswordDTO;
import bg.softuni.online_library_system.model.dto.UserProfileDTO;
import bg.softuni.online_library_system.model.dto.UserRegistrationDTO;
import bg.softuni.online_library_system.model.entity.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface UserService {

    boolean registerUser(UserRegistrationDTO userRegistrationDTO) throws IOException;

    UserProfileDTO getUserProfileData(String username);

    boolean editUser(UserProfileDTO userProfileDTO, HttpServletRequest request,
                     HttpServletResponse response) throws IOException;

    boolean changeUserPassword(String username, UserChangePasswordDTO userChangePasswordDTO);

    UserEntity getUserByUsername(String username);
}