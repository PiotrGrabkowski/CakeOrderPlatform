package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.ExceptionHandlers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Exceptions.NoSuchElementInDatabaseException;

@RestControllerAdvice
public class NoSuchElementInDatabaseExceptionHandler {

    @ExceptionHandler(NoSuchElementInDatabaseException.class)
    public ResponseEntity<String> handleException(NoSuchElementInDatabaseException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
