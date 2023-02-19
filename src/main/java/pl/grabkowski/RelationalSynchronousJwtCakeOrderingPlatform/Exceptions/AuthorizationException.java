package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Exceptions;

public class AuthorizationException extends RuntimeException{
    public AuthorizationException(String msg){
        super(msg);
    }
}
