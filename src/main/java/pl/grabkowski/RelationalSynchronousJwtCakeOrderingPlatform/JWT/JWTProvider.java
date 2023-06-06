package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.JWT;

import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.LoginRequest;

import java.util.Locale;

public interface JWTProvider {

    JWTTransferingObject provideJWT (LoginRequest loginRequest, Locale locale);
}
