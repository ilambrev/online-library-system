package bg.softuni.online_library_system.common.constant;

public final class ValidationConstants {
    public static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult.";

    // User Registration Form
    public static final String NOT_EMPTY_USERNAME = "Username can not be empty.";
    public static final String USERNAME_LENGTH = "Username must be between 6 and 40 characters long.";
    public static final String UNIQUE_USERNAME = "Username not available.";
    public static final String PASSWORD_REQUIREMENTS = "Password must be 8-20 characters long and must contain one digit from 1 to 9, one lowercase letter, one uppercase letter, one special character - no space.";
    public static final String NOT_EMPTY_PASSWORD = "Password is required.";
    public static final String PASSWORDS_CONFIRMATION = "Password confirmation is required.";
    public static final String PASSWORDS_MATCH = "Passwords should match.";
    public static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.* ).{8,20}$";
    public static final String GENDER_NOT_NULL = "Gender must be provided.";
    public static final String FIRST_NAME_LENGTH = "First name must be between 2 and 20 characters long.";
    public static final String FIRST_NAME_REQUIRED = "First name can not be empty.";
    public static final String LAST_NAME_LENGTH = "Last name must be between 2 and 20 characters long.";
    public static final String LAST_NAME_REQUIRED = "Last name can not be empty.";
    public static final String PHONE_NUMBER_PATTERN = "^[+]*\\d{8,}";
    public static final String PHONE_NUMBER_WRONG_FORMAT = "Phone number must be minimum 8 digits long, can start with "+" sign and must contain only digits.";
    public static final String PHONE_NUMBER_REQUIRED = "Phone number can not be empty.";
    public static final String ADDRESS_REQUIRED = "Address can not be empty.";
    public static final String ADDRESS_LENGTH = "Address must be between 3 and 60 characters long.";
    public static final String EMAIL_REQUIRED = "Address can not be empty.";
    public static final String EMAIL_FORMAT = "Invalid e-mail format.";
    public static final String EMAIL_LENGTH = "E-mail must be between 6 and 40 characters long.";

    // User Login From
    public static final String BAD_CREDENTIALS = "The username or password you entered is incorrect.";

    // User Change Password Form
    public static final String NEW_PASSWORD_REQUIREMENTS = "New password must be 8-20 characters long and must contain one digit from 1 to 9, one lowercase letter, one uppercase letter, one special character - no space.";
    public static final String NOT_EMPTY_NEW_PASSWORD = "New password is required.";
    public static final String NEW_PASSWORD_CONFIRMATION = "New password confirmation is required.";
    public static final String NEW_PASSWORDS_MATCH = "New password and confirmed new password should match.";

    // Add Book form
    public static final String YEAR_IN_THE_FUTURE = "Year must not be in the future.";
    public static final String YEAR_VALUE = "Year must be over 1000.";
    public static final String YEAR_NOT_NULL = "Year must be provided.";
    public static final String DESCRIPTION_REQUIRED = "Description is required.";
    public static final String DESCRIPTION_SIZE = "Description must be between 5 and 1024 symbols.";
    public static final String TITLE_REQUIRED = "Book title is required.";
    public static final String TITLE_SIZE = "Book title must be between 2 and 100 symbols.";
    public static final String PAGES_POSITIVE = "Pages number must be positive.";
    public static final String PAGES_NOT_NULL = "Pages number must be provided.";
    public static final String PUBLISHER_REQUIRED = "Publisher name is required.";
    public static final String PUBLISHER_NAME_SIZE = "Publisher name must be between 2 and 100 symbols.";
    public static final String ISB_PATTERN = "^(?:ISBN(?:-1[03])?:?\\ )?(?=[0-9X]{10}$|(?=(?:[0-9]+[-\\ ]){3})[-\\ 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[-\\ ]){4})[-\\ 0-9]{17}$)(?:97[89][-\\ ]?)?[0-9]{1,5}[-\\ ]?[0-9]+[-\\ ]?[0-9]+[-\\ ]?[0-9X]$";
    public static final String ISBN_VALID_TYPE = "ISBN must be 10 or 13 digit valid number.";
    public static final String ISBN_NOT_NULL = "ISBN must be provided.";
    public static final String GENRE_NOT_NULL = "Genre must be provided.";
    public static final String AUTHOR_REQUIRED = "Author name must be provided";
    public static final String AUTHOR_NAME_LENGTH = "Author name must be between 5 and 41 symbols.";
    public static final String NON_EXISTING_AUTHOR = "The author's name was not found. You must first enter the author in the database.";

    // Add Author form
    public static final String PRESENTATION_REQUIRED = "Presentation is required.";
    public static final String PRESENTATION_SIZE = "Presentation must be between 5 and 1024 symbols.";

    public static final String INVALID_FILE_SIZE = "File size must be less than 2MB.";
    public static final String INVALID_FILE_TYPE = "Invalid file type. Only JPEG, PNG, and GIF are allowed.";

    private ValidationConstants() {
    }
}