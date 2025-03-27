package bg.softuni.online_library_system.model.entity;

import bg.softuni.online_library_system.model.enums.BookStatusEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "books_statuses")
public class BookStatusEntity extends BaseEntity {

    @ManyToOne(targetEntity = BookEntity.class)
    @JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false)
    private BookEntity book;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @Column(name = "borrow_date")
    private LocalDateTime borrowDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @Column(name = "reservation_date")
    private LocalDateTime reservationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookStatusEnum status;

    public BookStatusEntity() {
    }

    public BookEntity getBook() {
        return book;
    }

    public BookStatusEntity setBook(BookEntity book) {
        this.book = book;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public BookStatusEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }

    public BookStatusEntity setBorrowDate(LocalDateTime borrowDate) {
        this.borrowDate = borrowDate;
        return this;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public BookStatusEntity setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
        return this;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public BookStatusEntity setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
        return this;
    }

    public BookStatusEnum getStatus() {
        return status;
    }

    public BookStatusEntity setStatus(BookStatusEnum status) {
        this.status = status;
        return this;
    }
}