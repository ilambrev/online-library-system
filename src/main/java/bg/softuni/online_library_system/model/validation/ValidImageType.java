package bg.softuni.online_library_system.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = ValidImageTypeValidator.class)
public @interface ValidImageType {

    String message() default "Invalid file type.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}