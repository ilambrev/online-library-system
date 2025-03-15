package bg.softuni.online_library_system.service;

import bg.softuni.online_library_system.model.dto.UserLoginDTO;
import bg.softuni.online_library_system.model.dto.UserProfileDTO;
import bg.softuni.online_library_system.model.dto.UserRegistrationDTO;

import java.io.IOException;

public interface UserService {

    boolean registerUser(UserRegistrationDTO userRegistrationDTO) throws IOException;

    boolean loginUser(UserLoginDTO userLoginDTO);

    void logoutUser();

    UserProfileDTO getUserByUsername(String username);
}