package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.JWT;

import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.UserDto;


public class JWTTransferingObject {

    private String jwt;
    private UserDto user;
    private String msg;

    public JWTTransferingObject(String jwt, UserDto user, String msg) {
        this.jwt = jwt;
        this.user = user;
        this.msg = msg;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
