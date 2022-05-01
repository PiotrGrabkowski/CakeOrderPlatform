package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.ExceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Exceptions.AccessTimeoutException;

@RestControllerAdvice
public class AccessTimeoutExceptionHandler {

    @ExceptionHandler(AccessTimeoutException.class)
    public ResponseEntity<String> handleException (AccessTimeoutException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("accessExpired","true").body(e.getMessage());

    }
}
