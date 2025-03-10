package bg.softuni.online_library_system.web.rest;

import bg.softuni.online_library_system.model.dto.AuthorDTO;
import bg.softuni.online_library_system.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/authors")
public class AuthorsRestController {
    private final AuthorService authorService;

    public AuthorsRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AuthorDTO>> getAll() {
        return ResponseEntity.ok(this.authorService.getAllAuthors());
    }

    @GetMapping("/first-name/{letter}")
    public ResponseEntity<List<AuthorDTO>> getByFirstName(@PathVariable("letter") String letter) {
        return ResponseEntity.ok(this.authorService.getAuthorsByFirstNameStartingWith(letter));
    }

    @GetMapping("/last-name/{letter}")
    public ResponseEntity<List<AuthorDTO>> getByLastName(@PathVariable("letter") String letter) {
        return ResponseEntity.ok(this.authorService.getAuthorsByLastNameStartingWith(letter));
    }
}