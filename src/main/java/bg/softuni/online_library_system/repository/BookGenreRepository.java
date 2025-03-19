package bg.softuni.online_library_system.repository;

import bg.softuni.online_library_system.model.entity.BookGenreEntity;
import bg.softuni.online_library_system.model.enums.BookGenreEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookGenreRepository extends JpaRepository<BookGenreEntity, Long> {

    BookGenreEntity findByGenre(BookGenreEnum bookGenreEnum);
}