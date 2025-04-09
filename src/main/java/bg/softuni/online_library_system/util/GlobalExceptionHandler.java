package bg.softuni.online_library_system.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(MaxUploadSizeExceededException ex,
                                         RedirectAttributes redirectAttributes,
                                         HttpServletRequest request) {

        redirectAttributes.addFlashAttribute("isFileSizeExceeded", true);
        return "redirect:" + request.getHeader("Referer");
    }
}