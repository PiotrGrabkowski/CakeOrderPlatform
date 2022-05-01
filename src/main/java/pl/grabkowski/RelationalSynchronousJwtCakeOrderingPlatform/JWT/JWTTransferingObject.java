package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.JWT;

import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.User;

public class JWTTransferingObject {

    private String jwt;
    private User user;

    public JWTTransferingObject(String jwt, User user) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
