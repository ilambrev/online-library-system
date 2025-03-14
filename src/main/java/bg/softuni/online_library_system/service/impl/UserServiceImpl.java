package bg.softuni.online_library_system.service.impl;

import bg.softuni.online_library_system.model.dto.UserLoginDTO;
import bg.softuni.online_library_system.model.dto.UserRegistrationDTO;
import bg.softuni.online_library_system.model.entity.UserEntity;
import bg.softuni.online_library_system.model.entity.UserRoleEntity;
import bg.softuni.online_library_system.model.enums.GenderEnum;
import bg.softuni.online_library_system.model.enums.UserRoleEnum;
import bg.softuni.online_library_system.repository.UserRepository;
import bg.softuni.online_library_system.repository.UserRoleRepository;
import bg.softuni.online_library_system.service.CloudinaryService;
import bg.softuni.online_library_system.service.UserService;
import bg.softuni.online_library_system.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

import static bg.softuni.online_library_system.common.constant.CloudinaryConstants.USERS_IMAGES_DIRECTORY;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final CloudinaryService cloudinaryService;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUser currentUser;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, CloudinaryService cloudinaryService,
                           PasswordEncoder passwordEncoder, CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.cloudinaryService = cloudinaryService;
        this.passwordEncoder = passwordEncoder;
        this.currentUser = currentUser;
    }

    @Override
    public boolean registerUser(UserRegistrationDTO userRegistrationDTO) throws IOException {
        if (!userRegistrationDTO.getPassword().equals(userRegistrationDTO.getConfirmPassword())) {
            return false;
        }
        Optional<UserEntity> optionalUser = this.userRepository.findByUsername(userRegistrationDTO.getUsername());
        if (optionalUser.isPresent()) {
            return false;
        }
        UserRoleEntity userRole = this.userRoleRepository.findByRole(UserRoleEnum.USER);

        UserEntity newUser = new UserEntity()
                .setFirstName(userRegistrationDTO.getFirstName())
                .setLastName(userRegistrationDTO.getLastName())
                .setUsername(userRegistrationDTO.getUsername())
                .setPassword(this.passwordEncoder.encode(userRegistrationDTO.getPassword()))
                .setEmail(userRegistrationDTO.getEmail())
                .setPhoneNumber(userRegistrationDTO.getPhoneNumber())
                .setAddress(userRegistrationDTO.getAddress())
                .setGender(userRegistrationDTO.getGender())
                .setRole(userRole)
                .setActive(true);

        if (userRegistrationDTO.getImageFile().isEmpty()) {
            newUser.setImageURL(newUser.getGender().equals(GenderEnum.MALE) ?
                    "/images/user_m.png" : "/images/user_f.png");
        } else {
            String imageUrl = this.cloudinaryService.uploadFile(userRegistrationDTO.getImageFile(), USERS_IMAGES_DIRECTORY);
            newUser.setImageURL(imageUrl);
        }

        this.userRepository.save(newUser);

        return true;
    }

    @Override
    public boolean loginUser(UserLoginDTO userLoginDTO) {
        Optional<UserEntity> optionalUser = this.userRepository.findByUsername(userLoginDTO.getUsername());
        if (optionalUser.isEmpty()) {
            return false;
        }
        UserEntity existingUser = optionalUser.get();
        if (!this.passwordEncoder.matches(userLoginDTO.getPassword(), existingUser.getPassword())) {
            return false;
        }

        this.currentUser.setUsername(existingUser.getUsername())
                .setFirstName(existingUser.getFirstName())
                .setLastName(existingUser.getLastName())
                .setImageURL(existingUser.getImageURL())
                .setRole(existingUser.getRole().getRole().name())
                .setLogged(true);

        return true;
    }

    @Override
    public void logoutUser() {
        this.currentUser.logout();
    }
}