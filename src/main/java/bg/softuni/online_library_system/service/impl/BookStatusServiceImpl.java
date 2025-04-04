package bg.softuni.online_library_system.service.impl;

import bg.softuni.online_library_system.exception.ObjectNotFoundException;
import bg.softuni.online_library_system.model.dto.BookDTO;
import bg.softuni.online_library_system.model.dto.BookStatusDTO;
import bg.softuni.online_library_system.model.entity.BookEntity;
import bg.softuni.online_library_system.model.entity.BookStatusEntity;
import bg.softuni.online_library_system.model.entity.UserEntity;
import bg.softuni.online_library_system.model.enums.BookStatusEnum;
import bg.softuni.online_library_system.repository.BookRepository;
import bg.softuni.online_library_system.repository.BookStatusRepository;
import bg.softuni.online_library_system.service.BookStatusService;
import bg.softuni.online_library_system.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static bg.softuni.online_library_system.common.constant.CartConstants.BOOK_BORROW_PERIOD;

@Service
public class BookStatusServiceImpl implements BookStatusService {
    private final BookStatusRepository bookStatusRepository;
    private final BookRepository bookRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public BookStatusServiceImpl(BookStatusRepository bookStatusRepository, BookRepository bookRepository, UserService userService,
                                 ModelMapper modelMapper) {
        this.bookStatusRepository = bookStatusRepository;
        this.bookRepository = bookRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BookStatusDTO> getAllByUserIdAndStatus(String username, BookStatusEnum status) {
        List<BookStatusDTO> statuses = new ArrayList<>();
        UserEntity user = this.userService.getUserByUsername(username);

        if (user != null) {
            statuses = this.bookStatusRepository.findAllByUserIdAndStatus(user.getId(), status)
                    .stream()
                    .map(statusEntity -> this.modelMapper.map(statusEntity, BookStatusDTO.class))
                    .toList();
        }

        return statuses;
    }

    @Override
    public void cancelReservation(Long id) {
        BookStatusEntity bookStatus = this.bookStatusRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Status with id %d not found.", id)));
        BookEntity book = this.bookRepository.findById(bookStatus.getBook().getId())
                .orElseThrow(() -> new ObjectNotFoundException(String
                        .format("Book with id %d not found.", bookStatus.getBook().getId())));
        bookStatus.setStatus(BookStatusEnum.CANCELLED_RESERVATION);

        this.bookStatusRepository.save(bookStatus);

        book.setAvailable(true);

        this.bookRepository.save(book);
    }

    @Override
    public List<BookDTO> getUserOverdueBooks(String username) {
        UserEntity user = this.userService.getUserByUsername(username);
        List<BookDTO> overdueBooks = new ArrayList<>();
        if (user != null) {
            overdueBooks = this.bookStatusRepository.findAllByUserIdAndStatus(user.getId(), BookStatusEnum.BORROWED)
                    .stream()
                    .filter(b -> b.getBorrowDate().plusDays(BOOK_BORROW_PERIOD).isBefore(LocalDateTime.now()))
                    .map(bookStatusEntity -> this.modelMapper.map(bookStatusEntity.getBook(), BookDTO.class))
                    .toList();
        }

        System.out.println(overdueBooks);

        return overdueBooks;
    }

    @Override
    public void confirmBookReservations(List<Long> ids) {
        List<BookStatusEntity> bookStatuses = this.bookStatusRepository.findBookStatusesByIds(ids)
                .stream()
                .map(s -> s.setStatus(BookStatusEnum.BORROWED).setBorrowDate(LocalDateTime.now()))
                .toList();

        this.bookStatusRepository.saveAll(bookStatuses);
    }
}