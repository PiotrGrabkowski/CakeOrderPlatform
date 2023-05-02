package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO;

public class LoginResponse {
    private UserDto user;
    private String msg;

    public LoginResponse(UserDto user, String msg) {
        this.user = user;
        this.msg = msg;
    }

    public LoginResponse() {
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
