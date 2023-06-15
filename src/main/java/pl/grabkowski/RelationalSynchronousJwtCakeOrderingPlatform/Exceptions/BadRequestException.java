package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Exceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String msg){
        super (msg);
    }
}
