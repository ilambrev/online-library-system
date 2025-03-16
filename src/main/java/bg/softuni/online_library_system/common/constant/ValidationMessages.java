package bg.softuni.online_library_system.common.constant;

public final class ValidationMessages {
    // User Registration Form
    public static final String NOT_EMPTY_USERNAME = "Username can not be empty.";
    public static final String USERNAME_LENGTH = "Username must be between 5 and 40 characters long.";
    public static final String UNIQUE_USERNAME = "Username not available.";
    public static final String PASSWORD_REQUIREMENTS = "Password must be 8-20 characters long and must contain one digit from 1 to 9, one lowercase letter, one uppercase letter, one special character - no space.";
    public static final String NOT_EMPTY_PASSWORD = "Password is required.";
    public static final String PASSWORDS_CONFIRMATION = "Password confirmation is required.";
    public static final String PASSWORDS_MATCH = "Passwords should match.";
    public static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.* ).{8,20}$";


    // User Login From
    public static final String BAD_CREDENTIALS = "The username or password you entered is incorrect.";
    public static final String EMPTY_USERNAME = "Please, enter username.";
    public static final String EMPTY_PASSWORD = "Please, enter password.";

    private ValidationMessages() {
    }
}