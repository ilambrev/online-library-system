package bg.softuni.online_library_system.service.impl;

import bg.softuni.online_library_system.model.entity.BookGenreEntity;
import bg.softuni.online_library_system.model.enums.BookGenreEnum;
import bg.softuni.online_library_system.repository.BookGenreRepository;
import bg.softuni.online_library_system.service.BookGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookGenreServiceImpl implements BookGenreService {
    private final BookGenreRepository bookGenreRepository;

    @Autowired
    public BookGenreServiceImpl(BookGenreRepository bookGenreRepository) {
        this.bookGenreRepository = bookGenreRepository;
    }

    @Override
    public BookGenreEntity getBookGenreByName(BookGenreEnum bookGenreEnum) {
        return this.bookGenreRepository.findByGenre(bookGenreEnum);
    }
}