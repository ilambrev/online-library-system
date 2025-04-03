package bg.softuni.online_library_system.repository;

import bg.softuni.online_library_system.model.entity.BookEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    @NonNull
    @Override
    @EntityGraph(attributePaths = {"author", "genre", "publisher"})
    Optional<BookEntity> findById(@NonNull Long id);

    @EntityGraph(attributePaths = {"genre", "publisher"})
    @Query("SELECT b FROM BookEntity b WHERE b.author.id IN :authorIds")
    List<BookEntity> findBooksByAuthorIds(@Param("authorIds") List<Long> authorIds);

    @Query("SELECT b FROM BookEntity b WHERE b.id IN :booksIds")
    List<BookEntity> findBooksByIds(@Param("booksIds") List<Long> booksIds);

    List<BookEntity> findByOrderByBorrowCounterDesc(Limit limit);
}