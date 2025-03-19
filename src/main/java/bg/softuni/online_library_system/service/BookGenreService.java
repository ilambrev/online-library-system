package bg.softuni.online_library_system.service;

import bg.softuni.online_library_system.model.entity.BookGenreEntity;
import bg.softuni.online_library_system.model.enums.BookGenreEnum;

public interface BookGenreService {

    BookGenreEntity getBookGenreByName(BookGenreEnum bookGenreEnum);
}