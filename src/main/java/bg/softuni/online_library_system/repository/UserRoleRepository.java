package bg.softuni.online_library_system.repository;

import bg.softuni.online_library_system.model.entity.UserRoleEntity;
import bg.softuni.online_library_system.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
    UserRoleEntity findByRole(UserRoleEnum userRoleEnum);
}