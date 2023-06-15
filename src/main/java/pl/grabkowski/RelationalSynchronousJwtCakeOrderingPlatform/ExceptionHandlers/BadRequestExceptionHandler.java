package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.ExceptionHandlers;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Exceptions.BadRequestException;

import java.util.Locale;

@RestControllerAdvice
public class BadRequestExceptionHandler {

    private final MessageSource messageSource;

    public BadRequestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleException(BadRequestException exception){
        Locale locale = (Locale)RequestContextHolder.getRequestAttributes().getAttribute("locale", RequestAttributes.SCOPE_REQUEST);
        String response =  this.messageSource.getMessage(exception.getMessage(), new Object[]{}, locale);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);


    }
}
