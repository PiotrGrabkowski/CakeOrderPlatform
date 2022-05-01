package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Exceptions;

public class AccessTimeoutException extends RuntimeException{

    public AccessTimeoutException (String msg){
        super(msg);
    }
}
