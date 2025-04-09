package bg.softuni.online_library_system.model.validation;

import bg.softuni.online_library_system.repository.AuthorRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ExistingAuthorValidator implements ConstraintValidator<ExistingAuthor, String> {
    private final AuthorRepository authorRepository;

    @Autowired
    public ExistingAuthorValidator(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty() || !value.matches("\\d+")) {
            return false;
        }

        return this.authorRepository.findById(Long.parseLong(value)).isPresent();
    }
}