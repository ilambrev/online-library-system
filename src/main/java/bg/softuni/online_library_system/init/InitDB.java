package bg.softuni.online_library_system.init;

import bg.softuni.online_library_system.model.entity.UserEntity;
import bg.softuni.online_library_system.model.entity.UserRoleEntity;
import bg.softuni.online_library_system.model.enums.GenderEnum;
import bg.softuni.online_library_system.model.enums.UserRoleEnum;
import bg.softuni.online_library_system.repository.UserRepository;
import bg.softuni.online_library_system.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static bg.softuni.online_library_system.util.AdministratorDataConstants.*;
import static bg.softuni.online_library_system.util.UserRoleDescriptionConstants.*;

@Component
public class InitDB implements CommandLineRunner {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public InitDB(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (this.userRoleRepository.count() == 0) {
            List<UserRoleEntity> roles = Arrays.stream(UserRoleEnum.values())
                    .map(UserRoleEntity::new)
                    .toList();
            roles.get(0).setDescription(USER_ROLE_DESCRIPTION);
            roles.get(1).setDescription(MODERATOR_ROLE_DESCRIPTION);
            roles.get(2).setDescription(ADMINISTRATOR_ROLE_DESCRIPTION);

            this.userRoleRepository.saveAll(roles);
        }

        if (this.userRepository.count() == 0) {
            UserRoleEntity administratorRole = this.userRoleRepository.findByRole(UserRoleEnum.ADMINISTRATOR);
            UserEntity administrator = new UserEntity()
                    .setFirstName(FIRST_NAME)
                    .setLastName(LAST_NAME)
                    .setUsername(USER_NAME)
                    .setPassword(this.passwordEncoder.encode(PASSWORD))
                    .setEmail(EMAIL)
                    .setPhoneNumber(PHONE_NUMBER)
                    .setAddress(ADDRESS)
                    .setGender(GenderEnum.MALE)
                    .setRole(administratorRole)
                    .setActive(true);

            this.userRepository.save(administrator);
        }
    }
}