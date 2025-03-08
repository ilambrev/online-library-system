package bg.softuni.online_library_system.service.impl;

import bg.softuni.online_library_system.model.dto.AddAuthorDTO;
import bg.softuni.online_library_system.model.dto.AuthorDTO;
import bg.softuni.online_library_system.model.entity.AuthorEntity;
import bg.softuni.online_library_system.repository.AuthorRepository;
import bg.softuni.online_library_system.service.AuthorService;
import bg.softuni.online_library_system.service.CloudinaryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final CloudinaryService cloudinaryService;

    public AuthorServiceImpl(AuthorRepository authorRepository, CloudinaryService cloudinaryService) {
        this.authorRepository = authorRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public boolean addAuthor(AddAuthorDTO addAuthorDTO) throws IOException {
        String directory = "authors";
        String imageUrl = this.cloudinaryService.uploadFile(addAuthorDTO.getImageFile(), directory);
        System.out.println(imageUrl);

        AuthorEntity newAuthor = new AuthorEntity()
                .setFirstName(addAuthorDTO.getFirstName())
                .setLastName(addAuthorDTO.getLastName())
                .setPresentation(addAuthorDTO.getPresentation())
                .setImageURL(imageUrl);

        this.authorRepository.save(newAuthor);

        return true;
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        List<AuthorEntity> authors = this.authorRepository.findAllByOrderByFirstNameAsc();
        return authors.stream()
                .map(a -> new AuthorDTO()
                        .setFirstName(a.getFirstName())
                        .setLastName(a.getLastName())
                        .setPresentation(a.getPresentation())
                        .setImageURL(a.getImageURL()))
                .toList();
    }
}