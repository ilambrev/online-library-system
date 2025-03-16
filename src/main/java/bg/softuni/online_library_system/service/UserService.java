package bg.softuni.online_library_system.service;

import bg.softuni.online_library_system.model.dto.UserLoginDTO;
import bg.softuni.online_library_system.model.dto.UserProfileDTO;
import bg.softuni.online_library_system.model.dto.UserRegistrationDTO;
import bg.softuni.online_library_system.model.entity.UserEntity;

import java.io.IOException;

public interface UserService {

    boolean registerUser(UserRegistrationDTO userRegistrationDTO) throws IOException;

    boolean loginUser(UserLoginDTO userLoginDTO);

    UserProfileDTO getUserProfileData(String username);

    boolean editUser(UserProfileDTO userProfileDTO) throws IOException;

    UserEntity getUserByUsername(String username);

    void logoutUser();
}