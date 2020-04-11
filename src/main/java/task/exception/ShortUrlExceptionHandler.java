package task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ShortUrlExceptionHandler {

    @ExceptionHandler(ShortUrlException.class)
    public ResponseEntity<Object> handleShortUrlException(ShortUrlException exception, HttpServletRequest webRequest) {
        return new ResponseEntity<>(
                exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
