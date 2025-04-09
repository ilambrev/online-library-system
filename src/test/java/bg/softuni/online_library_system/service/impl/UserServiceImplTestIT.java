package bg.softuni.online_library_system.service.impl;

import bg.softuni.online_library_system.exception.ObjectNotFoundException;
import bg.softuni.online_library_system.model.dto.UserChangeRoleDTO;
import bg.softuni.online_library_system.model.dto.UserProfileDTO;
import bg.softuni.online_library_system.model.dto.UserRegistrationDTO;
import bg.softuni.online_library_system.model.entity.UserEntity;
import bg.softuni.online_library_system.model.entity.UserRoleEntity;
import bg.softuni.online_library_system.model.enums.GenderEnum;
import bg.softuni.online_library_system.model.enums.UserRoleEnum;
import bg.softuni.online_library_system.repository.UserRepository;
import bg.softuni.online_library_system.repository.UserRoleRepository;
import bg.softuni.online_library_system.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceImplTestIT {

    @Autowired
    private UserService userServiceToTest;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
    }

    @Test
    void testRegisterUser() throws IOException {
        UserRegistrationDTO testUserRegistrationDTO = createTestUserRegistrationDTO();

        assertTrue(userServiceToTest.registerUser(testUserRegistrationDTO));
    }

    @Test
    void testGetUserProfileDataWithExistingUsername() {
        UserRoleEntity testUserRoleEntity = new UserRoleEntity()
                .setRole(UserRoleEnum.USER)
                .setDescription("User");

        UserRoleEntity savedTestUserRole = userRoleRepository.save(testUserRoleEntity);

        UserEntity testUserEntity = createTestUserEntity();
        testUserEntity.setPassword(passwordEncoder.encode("1234567890"));
        testUserEntity.setRole(savedTestUserRole);

        UserEntity savedTestUserEntity = userRepository.save(testUserEntity);

        UserProfileDTO result = userServiceToTest.getUserProfileData(savedTestUserEntity.getUsername());

        assertNotNull(result);
        assertEquals("Dave", result.getFirstName());
        assertEquals("Lombardo", result.getLastName());
        assertEquals("lombardo", result.getUsername());
        assertEquals(savedTestUserEntity.getPassword(), result.getPassword());
        assertNull(result.getNewPassword());
        assertEquals("lombardo@test.com", result.getEmail());
        assertEquals("+359555111222", result.getPhoneNumber());
        assertEquals("Somewhere", result.getAddress());
        assertEquals("https://someimage.com", result.getImageURL());
        assertEquals(GenderEnum.MALE, result.getGender());
        assertEquals(UserRoleEnum.USER.name(), result.getRole().name());
        assertNull(result.getImageFile());
    }

    @Test
    void testGetUserProfileDataWithNonExistingUsername() {
        String username = "non-existing";

        assertNull(userServiceToTest.getUserProfileData(username));
    }

    @Test
    void testGetUserDataToChangeRoleWithExistingUserId() {
        UserRoleEntity testUserRoleEntity = new UserRoleEntity()
                .setRole(UserRoleEnum.USER)
                .setDescription("User");

        UserRoleEntity savedTestUserRole = userRoleRepository.save(testUserRoleEntity);

        UserEntity testUserEntity = createTestUserEntity();
        testUserEntity.setPassword(passwordEncoder.encode("1234567890"));
        testUserEntity.setRole(savedTestUserRole);

        UserEntity savedTestUserEntity = userRepository.save(testUserEntity);

        UserChangeRoleDTO result = userServiceToTest.getUserDataToChangeRole(savedTestUserEntity.getId());

        assertEquals(savedTestUserEntity.getId(), result.getId());
        assertEquals("Dave", result.getFirstName());
        assertEquals("Lombardo", result.getLastName());
        assertEquals("lombardo", result.getUsername());
        assertEquals("lombardo@test.com", result.getEmail());
        assertEquals("+359555111222", result.getPhoneNumber());
        assertEquals("Somewhere", result.getAddress());
        assertEquals("https://someimage.com", result.getImageURL());
        assertEquals(GenderEnum.MALE, result.getGender());
        assertEquals(UserRoleEnum.USER.name(), result.getRole().name());
    }

    @Test
    void testGetUserDataToChangeRoleWithNonExistingUserId() {
        Long id = 0L;

        assertThrows(ObjectNotFoundException.class,
                () -> userServiceToTest.getUserDataToChangeRole(id));
    }

    private static UserEntity createTestUserEntity() {
        return new UserEntity()
                .setFirstName("Dave")
                .setLastName("Lombardo")
                .setUsername("lombardo")
                .setEmail("lombardo@test.com")
                .setPhoneNumber("+359555111222")
                .setAddress("Somewhere")
                .setImageURL("https://someimage.com")
                .setGender(GenderEnum.MALE)
                .setActive(true);
    }

    private static UserRegistrationDTO createTestUserRegistrationDTO() {
        MultipartFile emptyFile = new MockMultipartFile(
                "file",
                "",
                "text/plain",
                new byte[0]);

        return new UserRegistrationDTO()
                .setFirstName("Dave")
                .setLastName("Lombardo")
                .setUsername("lombardo")
                .setEmail("lombardo@test.com")
                .setPhoneNumber("+359555111222")
                .setAddress("Somewhere")
                .setPassword("MySecret&23")
                .setConfirmPassword("MySecret&23")
                .setGender(GenderEnum.MALE)
                .setImageFile(emptyFile);
    }
}