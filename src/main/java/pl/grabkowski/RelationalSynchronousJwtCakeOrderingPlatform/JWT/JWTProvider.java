package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.JWT;

import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.LoginRequest;

public interface JWTProvider {

    JWTTransferingObject provideJWT (LoginRequest loginRequest);
}
