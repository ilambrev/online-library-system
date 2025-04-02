package bg.softuni.online_library_system.repository;

import bg.softuni.online_library_system.model.entity.BookStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookStatusRepository extends JpaRepository<BookStatusEntity, Long> {

}