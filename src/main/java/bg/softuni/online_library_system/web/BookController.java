package bg.softuni.online_library_system.web;

import bg.softuni.online_library_system.model.dto.*;
import bg.softuni.online_library_system.model.enums.BookGenreEnum;
import bg.softuni.online_library_system.model.enums.BookStatusEnum;
import bg.softuni.online_library_system.model.security.CustomUserDetails;
import bg.softuni.online_library_system.service.BookService;
import bg.softuni.online_library_system.service.BookStatusService;
import bg.softuni.online_library_system.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final UserService userService;
    private final BookStatusService bookStatusService;

    @Autowired
    public BookController(BookService bookService, UserService userService,
                          BookStatusService bookStatusService) {
        this.bookService = bookService;
        this.userService = userService;
        this.bookStatusService = bookStatusService;
    }

    @GetMapping("/add")
    public String addBook(Model model) {
        model.addAttribute("addBookDTO", new AddBookDTO());
        model.addAttribute("bookGenres", BookGenreEnum.values());

        return "book-add";
    }

    @PostMapping("/add")
    public String addBook(AddBookDTO addBookDTO) throws IOException {
        Long id = this.bookService.addBook(addBookDTO);

        return String.format("redirect:/books/%d/about", id);
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

    @GetMapping("/{id}/about")
    public String aboutBook(@PathVariable("id") Long id, Model model) {
        BookAboutDTO book = this.bookService.getBookById(id);

        if (book != null) {
            model.addAttribute("bookAboutDTO", book);

            return "book-about";
        }

        return "redirect:/books/all";
    }

    @GetMapping("/reservations")
    public String searchBookReservations(@RequestParam(required = false) String username, Model model) {
        boolean isSearchMade = username != null && !username.isEmpty();
        List<BookStatusDTO> reservations = new ArrayList<>();

        if (isSearchMade) {
            reservations = this.bookStatusService.getAllByUserIdAndStatus(username, BookStatusEnum.RESERVED);
            model.addAttribute("user", username);
        }
        model.addAttribute("isSearchMade", isSearchMade);
        model.addAttribute("reservations", reservations);
        model.addAttribute("userSearchDTO", new UserSearchDTO());

        return "book-reservations";
    }

    @PatchMapping("/reservations/confirm")
    public String confirmBookReservations(@RequestParam List<Long> id,
                                          @RequestParam String username) {


        return String.format("redirect:/books/reservations?username=%s", username);
    }

    @PatchMapping("/reservations/cancel")
    public String cancelBookReservation(@RequestParam Long id,
                                        @RequestParam String username,
                                        @AuthenticationPrincipal CustomUserDetails userDetails,
                                        HttpServletRequest request, HttpServletResponse response) {
        this.bookStatusService.cancelReservation(id);

        if (userDetails.getUsername().equals(username)) {
            this.userService.refreshAuthenticatedUser(userDetails.getUsername(), request, response);
        }

        return String.format("redirect:/books/reservations?username=%s", username);
    }

    @GetMapping("/overdue-warning")
    public String overdueWarning(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 Model model) {

        model.addAttribute("overdueBooks",
                this.bookStatusService.getUserOverdueBooks(userDetails.getUsername()));

        return "overdue-warning";
    }
}