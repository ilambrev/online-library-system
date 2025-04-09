package bg.softuni.online_library_system.model.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ValidImageTypeValidator implements ConstraintValidator<ValidImageType, MultipartFile> {
    private final List<String> ALLOWED_FILE_TYPES = List.of("image/jpeg", "image/png", "image/gif");

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true;
        }
        return ALLOWED_FILE_TYPES.contains(file.getContentType());
    }
}