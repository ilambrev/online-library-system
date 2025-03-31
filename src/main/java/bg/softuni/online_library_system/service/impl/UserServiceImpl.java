package bg.softuni.online_library_system.service.impl;

import bg.softuni.online_library_system.exception.ObjectNotFoundException;
import bg.softuni.online_library_system.model.dto.UserChangePasswordDTO;
import bg.softuni.online_library_system.model.dto.UserChangeRoleDTO;
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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static bg.softuni.online_library_system.common.constant.CloudinaryConstants.USERS_IMAGES_DIRECTORY;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserDetailsService userDetailsService;
    private final SecurityContextRepository securityContextRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository,
                           UserDetailsService userDetailsService, SecurityContextRepository securityContextRepository,
                           ModelMapper modelMapper, CloudinaryService cloudinaryService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.userDetailsService = userDetailsService;
        this.securityContextRepository = securityContextRepository;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
        this.passwordEncoder = passwordEncoder;
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
    public UserProfileDTO getUserProfileData(String username) {
        UserEntity user = getUserByUsername(username);

        return user == null ? null :
                this.modelMapper.map(user, UserProfileDTO.class);
    }

    @Override
    public boolean editUser(UserProfileDTO userProfileDTO, HttpServletRequest request,
                            HttpServletResponse response) throws IOException {
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

        refreshAuthenticatedUser(userProfileDTO.getUsername(), request, response);

        return true;
    }

    @Override
    public boolean changeUserPassword(String username, UserChangePasswordDTO userChangePasswordDTO) {
        UserEntity user = getUserByUsername(username);
        if (!this.passwordEncoder.matches(userChangePasswordDTO.getPassword(), user.getPassword())) {
            return false;
        }
        user.setPassword(this.passwordEncoder.encode(userChangePasswordDTO.getNewPassword()));
        this.userRepository.save(user);

        return true;
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return this.userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Page<UserChangeRoleDTO> getAllUsersOrderByFirstName(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("firstName").ascending());

        return this.userRepository.findAll(pageable)
                .map(userEntity -> this.modelMapper.map(userEntity, UserChangeRoleDTO.class));
    }

    @Override
    public UserChangeRoleDTO getUserDataToChangeRole(Long id) {
        return this.userRepository.findById(id)
                .map(userEntity -> this.modelMapper.map(userEntity, UserChangeRoleDTO.class))
                .orElseThrow(() -> new ObjectNotFoundException(String.format("User with id %d not found.", id)));
    }

    @Override
    public void changeUserRole(Long id, UserRoleEnum role) {
        UserEntity user = this.userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("User with id %d not found.", id)));
        UserRoleEntity newRole = this.userRoleRepository.findByRole(role);
        user.setRole(newRole);
        this.userRepository.save(user);
    }

    private void refreshAuthenticatedUser(String username, HttpServletRequest request, HttpServletResponse response) {
        UserDetails updatedUserDetails = this.userDetailsService.loadUserByUsername(username);

        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                updatedUserDetails,
                updatedUserDetails.getPassword(),
                updatedUserDetails.getAuthorities()
        );

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(newAuth);
        SecurityContextHolder.setContext(securityContext);

        this.securityContextRepository.saveContext(securityContext, request, response);
    }
}