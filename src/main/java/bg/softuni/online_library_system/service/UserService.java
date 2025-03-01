package bg.softuni.online_library_system.service;

import bg.softuni.online_library_system.model.dto.UserRegistrationDTO;

public interface UserService {
    boolean registerUser(UserRegistrationDTO userRegistrationDTO);
}