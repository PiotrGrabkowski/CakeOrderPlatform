package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.JWT;

public interface JWTAuthenticationProvider {

    void provideAuthentication (String jwt);

}
