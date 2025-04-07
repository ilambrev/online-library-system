package bg.softuni.online_library_system.service.impl;

import bg.softuni.online_library_system.exception.ObjectNotFoundException;
import bg.softuni.online_library_system.model.dto.BookDTO;
import bg.softuni.online_library_system.model.entity.BookEntity;
import bg.softuni.online_library_system.model.entity.BookStatusEntity;
import bg.softuni.online_library_system.model.entity.UserEntity;
import bg.softuni.online_library_system.model.enums.BookStatusEnum;
import bg.softuni.online_library_system.repository.BookRepository;
import bg.softuni.online_library_system.repository.BookStatusRepository;
import bg.softuni.online_library_system.service.BookSelectionService;
import bg.softuni.online_library_system.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookSelectionServiceImpl implements BookSelectionService {
    private final UserService userService;
    private final BookRepository bookRepository;
    private final BookStatusRepository bookStatusRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BookSelectionServiceImpl(UserService userService, BookRepository bookRepository,
                                    BookStatusRepository bookStatusRepository, ModelMapper modelMapper) {
        this.userService = userService;
        this.bookRepository = bookRepository;
        this.bookStatusRepository = bookStatusRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void changeBookStatus(Long id, boolean isBookAvailable) {
        BookEntity book = this.bookRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Book with id %d not found.", id)));
        book.setAvailable(isBookAvailable);

        this.bookRepository.save(book);
    }

    @Override
    public boolean isBookAvailable(Long id) {
        return this.bookRepository.findById(id).map(BookEntity::isAvailable).orElse(false);
    }

    @Override
    public List<BookDTO> getAllBooksById(List<Long> ids) {
        return this.bookRepository.findAllById(ids)
                .stream()
                .map(bookEntity -> this.modelMapper.map(bookEntity, BookDTO.class))
                .toList();
    }

    @Override
    public void makeBooksAvailable(List<Long> ids) {
        List<BookEntity> books = this.bookRepository.findBooksByIds(ids)
                .stream()
                .map(b -> b.setAvailable(true))
                .toList();

        this.bookRepository.saveAll(books);
    }

    @Override
    public void reserveBooks(List<Long> ids, String username) {
        UserEntity user = this.userService.getUserByUsername(username);
        List<BookEntity> books = this.bookRepository.findBooksByIds(ids);

        if (user != null && !books.isEmpty()) {
            List<BookStatusEntity> statuses = books.stream()
                    .map(book -> new BookStatusEntity()
                            .setBook(book)
                            .setUser(user)
                            .setReservationDate(LocalDateTime.now())
                            .setStatus(BookStatusEnum.RESERVED))
                    .toList();

            this.bookStatusRepository.saveAll(statuses);
        }
    }
}