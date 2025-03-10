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
        List<AuthorDTO> authors = this.authorService.getAllAuthors();
        if (authors.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(authors);
        }
    }

    @GetMapping("/first-name/{letter}")
    public ResponseEntity<List<AuthorDTO>> getByFirstName(@PathVariable("letter") String letter) {
        List<AuthorDTO> authors = this.authorService.getAuthorsByFirstNameStartingWith(letter);
        if (authors.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(authors);
        }
    }

    @GetMapping("/last-name/{letter}")
    public ResponseEntity<List<AuthorDTO>> getByLastName(@PathVariable("letter") String letter) {
        List<AuthorDTO> authors = this.authorService.getAuthorsByLastNameStartingWith(letter);
        if (authors.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(authors);
        }
    }
}