package bg.softuni.online_library_system.repository;

import bg.softuni.online_library_system.model.entity.AuthorEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

    @NonNull
    @Override
    @EntityGraph(attributePaths = {"books"})
    Optional<AuthorEntity> findById(@NonNull Long id);

    List<AuthorEntity> findByFirstNameStartingWith(String letter);

    List<AuthorEntity> findByLastNameStartingWith(String letter);
}