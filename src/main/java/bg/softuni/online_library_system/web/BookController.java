package bg.softuni.online_library_system.web;

import bg.softuni.online_library_system.model.dto.AddBookDTO;
import bg.softuni.online_library_system.model.dto.AuthorDTO;
import bg.softuni.online_library_system.model.dto.BookDTO;
import bg.softuni.online_library_system.model.enums.BookGenreEnum;
import bg.softuni.online_library_system.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/add")
    public String addBook(Model model) {
        model.addAttribute("addBookDTO", new AddBookDTO());
        model.addAttribute("bookGenres", BookGenreEnum.values());

        return "book-add";
    }

    @PostMapping("/add")
    public String addBook(AddBookDTO addBookDTO) throws IOException {

        this.bookService.addBook(addBookDTO);

        return "redirect:/books/all";
    }

    @GetMapping("/all")
    public String getAllBooks(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "6") int size,
                              Model model) {

        Page<BookDTO> booksPage = this.bookService.getAllBooksOrderByFirstName(page, size);

        model.addAttribute("booksCount", booksPage.getTotalElements());
        model.addAttribute("books", booksPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", booksPage.getTotalPages());

        return "books";
    }
}