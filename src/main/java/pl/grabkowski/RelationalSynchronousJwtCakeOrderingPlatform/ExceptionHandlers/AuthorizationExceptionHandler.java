package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.ExceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Exceptions.AuthorizationException;

@RestControllerAdvice
public class AuthorizationExceptionHandler {
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<String> handle(AuthorizationException e){

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Nie masz dostępu do danego zasobu");
    }
}
