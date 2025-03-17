package bg.softuni.online_library_system.service.impl;

import bg.softuni.online_library_system.model.dto.UserLoginDTO;
import bg.softuni.online_library_system.model.dto.UserProfileDTO;
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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static bg.softuni.online_library_system.common.constant.CloudinaryConstants.USERS_IMAGES_DIRECTORY;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUser currentUser;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, ModelMapper modelMapper,
                           CloudinaryService cloudinaryService, PasswordEncoder passwordEncoder, CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
        this.passwordEncoder = passwordEncoder;
        this.currentUser = currentUser;
    }

    @Override
    public boolean registerUser(UserRegistrationDTO userRegistrationDTO) throws IOException {
        UserRoleEntity userRole = this.userRoleRepository.findByRole(UserRoleEnum.USER);
        UserEntity newUser = this.modelMapper.map(userRegistrationDTO, UserEntity.class);

        newUser.setPassword(this.passwordEncoder.encode(userRegistrationDTO.getPassword()))
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
        UserEntity user = getUserByUsername(userLoginDTO.getUsername());
        if (user == null) {
            return false;
        }
        if (!this.passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            return false;
        }

        this.currentUser.setUsername(user.getUsername())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setImageURL(user.getImageURL())
                .setRole(user.getRole().getRole().name())
                .setLogged(true);

        return true;
    }

    @Override
    public UserProfileDTO getUserProfileData(String username) {
        UserEntity user = getUserByUsername(username);

        return user == null ? null :
                this.modelMapper.map(user, UserProfileDTO.class);
    }

    @Override
    public boolean editUser(UserProfileDTO userProfileDTO) throws IOException {
        UserEntity user = getUserByUsername(userProfileDTO.getUsername());
        if (user == null) {
            return false;
        }
        if (!this.passwordEncoder.matches(userProfileDTO.getPassword(), user.getPassword())) {
            return false;
        }
        if (!user.getFirstName().equals(userProfileDTO.getFirstName())) {
            user.setFirstName(userProfileDTO.getFirstName());
        }
        if (!user.getLastName().equals(userProfileDTO.getLastName())) {
            user.setLastName(userProfileDTO.getLastName());
        }


        if (!user.getPhoneNumber().equals(userProfileDTO.getPhoneNumber())) {
            user.setPhoneNumber(userProfileDTO.getPhoneNumber());
        }
        if (!user.getAddress().equals(userProfileDTO.getAddress())) {
            user.setAddress(userProfileDTO.getAddress());
        }
        if (!user.getGender().equals(userProfileDTO.getGender())) {
            user.setGender(userProfileDTO.getGender());
        }
        if (!userProfileDTO.getImageFile().isEmpty()) {
            this.cloudinaryService.deleteFile(user.getImageURL());
            String imageUrl = this.cloudinaryService.uploadFile(userProfileDTO.getImageFile(), USERS_IMAGES_DIRECTORY);
            user.setImageURL(imageUrl);
        }

        this.userRepository.save(user);

        return true;
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return this.userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public void logoutUser() {
        this.currentUser.logout();
    }
}