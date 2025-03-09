package bg.softuni.online_library_system.repository;

import bg.softuni.online_library_system.model.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

    List<AuthorEntity> findAllByOrderByFirstNameAsc();

    List<AuthorEntity> findByFirstNameStartingWith(String letter);

    List<AuthorEntity> findByLastNameStartingWith(String letter);
}