package bg.softuni.online_library_system.repository;

import bg.softuni.online_library_system.model.entity.BookStatusEntity;
import bg.softuni.online_library_system.model.enums.BookStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookStatusRepository extends JpaRepository<BookStatusEntity, Long> {

    int countByUserIdAndStatus(Long id, BookStatusEnum status);

    List<BookStatusEntity> findAllByUserIdAndStatus(Long id, BookStatusEnum status);

    @Query("SELECT s FROM BookStatusEntity s WHERE s.id IN :bookStatusIds")
    List<BookStatusEntity> findBookStatusesByIds(List<Long> bookStatusIds);

    List<BookStatusEntity> findAllByUserId(Long id);
}