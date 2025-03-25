package bg.softuni.online_library_system.service;

import bg.softuni.online_library_system.model.dto.UserChangePasswordDTO;
import bg.softuni.online_library_system.model.dto.UserProfileDTO;
import bg.softuni.online_library_system.model.dto.UserRegistrationDTO;
import bg.softuni.online_library_system.model.entity.UserEntity;

import java.io.IOException;

public interface UserService {

    boolean registerUser(UserRegistrationDTO userRegistrationDTO) throws IOException;

    UserProfileDTO getUserProfileData(String username);

    boolean editUser(UserProfileDTO userProfileDTO) throws IOException;

    boolean changeUserPassword(String username, UserChangePasswordDTO userChangePasswordDTO);

    UserEntity getUserByUsername(String username);
}