package bg.softuni.online_library_system.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "most_readed_books")
public class MostReadBookEntity extends BaseEntity {

    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "image_url", nullable = false)
    private String imageURL;

    @Column(name = "borrow_counter")
    private int borrowCounter;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Column(name = "author_first_name", nullable = false)
    private String authorFirstName;

    @Column(name = "author_last_name", nullable = false)
    private String authorLastName;

    public MostReadBookEntity() {
    }

    public Long getBookId() {
        return bookId;
    }

    public MostReadBookEntity setBookId(Long bookId) {
        this.bookId = bookId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MostReadBookEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public MostReadBookEntity setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public int getBorrowCounter() {
        return borrowCounter;
    }

    public MostReadBookEntity setBorrowCounter(int borrowCounter) {
        this.borrowCounter = borrowCounter;
        return this;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public MostReadBookEntity setAuthorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public MostReadBookEntity setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
        return this;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public MostReadBookEntity setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
        return this;
    }
}