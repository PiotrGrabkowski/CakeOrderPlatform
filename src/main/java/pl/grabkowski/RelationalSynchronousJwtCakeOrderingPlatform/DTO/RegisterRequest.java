package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO;

public class RegisterRequest {

    private String nickname;
    private String password;

    //Must be an email
    private String username;
    private Long phoneNumber;

    public RegisterRequest() {
    }

    public RegisterRequest(String nickname, String password, String username, Long phoneNumber) {
        this.nickname = nickname;
        this.password = password;
        this.username = username;
        this.phoneNumber = phoneNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
