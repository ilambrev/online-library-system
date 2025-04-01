package bg.softuni.online_library_system.web;

import bg.softuni.online_library_system.model.dto.BookCartDTO;
import bg.softuni.online_library_system.model.security.CustomUserDetails;
import bg.softuni.online_library_system.service.BookService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static bg.softuni.online_library_system.common.constant.CartConstants.MAX_NUMBER_OF_BORROWED_BOOKS;

@Controller
@RequestMapping("/cart")
public class BookSelectionController {
    private final BookService bookService;

    @Autowired
    public BookSelectionController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/add")
    public String addBookToCart(@RequestParam Long id,
                                @AuthenticationPrincipal CustomUserDetails userDetails,
                                HttpSession session) {
        if (userDetails.getBorrowedBooks() == MAX_NUMBER_OF_BORROWED_BOOKS) {

            return "cart";
        }
        List<Long> selectedBooksIds = new ArrayList<>();
        if (session.getAttribute("selectedBooks") == null) {
            session.setAttribute("selectedBooks", selectedBooksIds);
        } else {
            selectedBooksIds = getBooksIdsFromSession(session.getAttribute("selectedBooks"));
        }
        int borrowedBooks = userDetails.getBorrowedBooks() + selectedBooksIds.size();
        if (borrowedBooks > 2) {

            return "redirect:/cart/content";
        }
        if (this.bookService.isBookAvailable(id)) {
            selectedBooksIds.add(id);
            session.setAttribute("selectedBooks", selectedBooksIds);
            int cartCount = (int) session.getAttribute("cartCount");
            cartCount++;
            session.setAttribute("cartCount", cartCount);
            boolean isBookAvailable = false;
            this.bookService.changeBookStatus(id, isBookAvailable);
        }

        return String.format("redirect:/books/%d/about", id);
    }

    @GetMapping("/content")
    public String getCartContent(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 HttpSession session, Model model) {

        List<Long> selectedBooksIds = getBooksIdsFromSession(session.getAttribute("selectedBooks"));
        List<BookCartDTO> books = new ArrayList<>();
        if (!selectedBooksIds.isEmpty()) {
            books = this.bookService.getAllBooksById(selectedBooksIds);
        }
        model.addAttribute("selectedBooks", books);
        int remainingNumberOfBooksToBorrow =
                MAX_NUMBER_OF_BORROWED_BOOKS - (books.size() + userDetails.getBorrowedBooks());
        model.addAttribute("remainingNumberOfBooks", remainingNumberOfBooksToBorrow);
        model.addAttribute("borrowedBooks", userDetails.getBorrowedBooks());

        return "cart";
    }

    @GetMapping("/remove")
    public String deleteBookFromCart(@RequestParam Long id, HttpSession session) {
        if (session.getAttribute("selectedBooks") != null) {
            List<Long> selectedBooksIds = getBooksIdsFromSession(session.getAttribute("selectedBooks"));
            int indexOfBookToRemove = selectedBooksIds.indexOf(id);
            if (indexOfBookToRemove >= 0) {
                selectedBooksIds.remove(indexOfBookToRemove);
                session.setAttribute("selectedBooks", selectedBooksIds);
                boolean isBookAvailable = true;
                this.bookService.changeBookStatus(id, isBookAvailable);
                int cartCount = (int) session.getAttribute("cartCount");
                if (cartCount > 0) {
                    cartCount--;
                }
                session.setAttribute("cartCount", cartCount);
            }
        }

        return "redirect:/cart/content";
    }

    @PostMapping("/reserve")
    public String reserveBooks(HttpSession session) {
        this.bookService.reserveBooks(getBooksIdsFromSession(session.getAttribute("selectedBooks")));
        session.setAttribute("selectedBooks", new ArrayList<Long>());
        session.setAttribute("cartCount", 0);

        return "redirect:/";
    }

    private List<Long> getBooksIdsFromSession(Object selectedBooksObject) {
        List<Long> selectedBooksIds = new ArrayList<>();
        if (selectedBooksObject instanceof List<?>) {
            selectedBooksIds = ((List<?>) selectedBooksObject).stream()
                    .filter(item -> item instanceof Long)
                    .map(item -> (Long) item)
                    .collect(Collectors.toList());
        }

        return selectedBooksIds;
    }
}