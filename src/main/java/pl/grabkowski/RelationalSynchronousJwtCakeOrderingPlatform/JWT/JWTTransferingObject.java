package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.JWT;

import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.UserDto;


public class JWTTransferingObject {

    private String jwt;
    private UserDto user;

    public JWTTransferingObject(String jwt, UserDto user) {
        this.jwt = jwt;
        this.user = user;
    }

    public JWTTransferingObject() {
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
