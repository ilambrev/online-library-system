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
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static bg.softuni.online_library_system.common.constant.ValidationConstants.*;

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

    @ModelAttribute
    public void addErrorMessage(Model model) {
        model.addAttribute("errorMessage", INVALID_FILE_SIZE);
    }

    @GetMapping("/add")
    public String addBook(Model model) {
        if (!model.containsAttribute("addBookDTO")) {
            model.addAttribute("addBookDTO", new AddBookDTO());
        }
        if (!model.containsAttribute("bookGenres")) {
            model.addAttribute("bookGenres", BookGenreEnum.values());
        }
        if (!model.containsAttribute("isFileSizeExceeded")) {
            model.addAttribute("isFileSizeExceeded", false);
        }

        return "book-add";
    }

    @PostMapping("/add")
    public String addBook(@Valid AddBookDTO addBookDTO,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addBookDTO", addBookDTO);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_PATH.concat("addBookDTO"), bindingResult);

            return "redirect:/books/add";
        }

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

    @GetMapping("/borrow")
    public String searchBorrowedBooks(@RequestParam(required = false) String username, Model model) {
        boolean isSearchMade = username != null && !username.isEmpty();
        List<BookStatusDTO> borrowedBooks = new ArrayList<>();

        if (isSearchMade) {
            borrowedBooks = this.bookStatusService.getAllByUserIdAndStatus(username, BookStatusEnum.BORROWED);
            model.addAttribute("user", username);
        }
        model.addAttribute("isSearchMade", isSearchMade);
        model.addAttribute("borrowedBooks", borrowedBooks);
        model.addAttribute("userSearchDTO", new UserSearchDTO());

        return "book-return";
    }

    @PatchMapping("/borrow/return")
    public String returnBook(@RequestParam Long id,
                             @RequestParam String username,
                             @AuthenticationPrincipal CustomUserDetails userDetails,
                             HttpServletRequest request, HttpServletResponse response) {
        this.bookStatusService.returnBook(id);

        if (userDetails.getUsername().equals(username)) {
            this.userService.refreshAuthenticatedUser(userDetails.getUsername(), request, response);
        }

        return String.format("redirect:/books/borrow?username=%s", username);
    }

    @PatchMapping("/reservations/confirm")
    public String confirmBookReservations(@RequestParam List<Long> id,
                                          @RequestParam String username,
                                          @AuthenticationPrincipal CustomUserDetails userDetails,
                                          HttpServletRequest request, HttpServletResponse response) {

        this.bookStatusService.confirmBookReservations(id);

        if (userDetails.getUsername().equals(username)) {
            this.userService.refreshAuthenticatedUser(userDetails.getUsername(), request, response);
        }

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