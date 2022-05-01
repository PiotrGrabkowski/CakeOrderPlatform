package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Exceptions;

public class NoSuchElementInDatabaseException extends RuntimeException{

    public NoSuchElementInDatabaseException(String s) {
        super(s);
    }
}
