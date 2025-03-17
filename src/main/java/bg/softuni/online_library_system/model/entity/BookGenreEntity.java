package bg.softuni.online_library_system.model.entity;

import bg.softuni.online_library_system.model.enums.BookGenreEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "genres")
public class BookGenreEntity extends BaseEntity {
    @Column(name = "genre", nullable = false)
    @Enumerated(EnumType.STRING)
    private BookGenreEnum genre;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    public BookGenreEntity() {
    }

    public BookGenreEnum getGenre() {
        return genre;
    }

    public BookGenreEntity setGenre(BookGenreEnum genre) {
        this.genre = genre;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BookGenreEntity setDescription(String description) {
        this.description = description;
        return this;
    }
}