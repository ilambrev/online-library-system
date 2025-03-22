package bg.softuni.online_library_system.web.rest;

import bg.softuni.online_library_system.model.dto.AuthorRestDTO;
import bg.softuni.online_library_system.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/authors")
public class AuthorsRestController {
    private final AuthorService authorService;

    @Autowired
    public AuthorsRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AuthorRestDTO>> getAllAuthors() {
        return ResponseEntity.ok(this.authorService.getAllAuthors());
    }

    @GetMapping("/first-name/{letter}")
    public ResponseEntity<List<AuthorRestDTO>> getByFirstName(@PathVariable("letter") String letter) {
        return ResponseEntity.ok(this.authorService.getAuthorsByFirstNameStartingWith(letter));
    }

    @GetMapping("/last-name/{letter}")
    public ResponseEntity<List<AuthorRestDTO>> getByLastName(@PathVariable("letter") String letter) {
        return ResponseEntity.ok(this.authorService.getAuthorsByLastNameStartingWith(letter));
    }
}