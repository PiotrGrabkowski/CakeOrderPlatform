package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO;

import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.User;

public class UserDto {
    private Long id;
    private String username; // Must be an email
    private String role;
    private String nickname;
    private String phoneNumber;
    private String userEnabled;

    public UserDto() {
    }
    public UserDto(User user){
        if(user != null) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.role = user.getRole();
            this.nickname = user.getNickname();
            if(user.getPhoneNumber()!=null){
                this.phoneNumber = user.getPhoneNumber().toString();
            }
            if(user.isUserEnabled()){
                this.userEnabled = "TRUE";
            }
            else {
                this.userEnabled = "FALSE";
            }


        }


    }

    public UserDto(Long id, String username, String role, String nickname, String phoneNumber) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }

    public UserDto(Long id, String username, String role, String nickname, String phoneNumber, boolean userEnabled) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        if(userEnabled){
            this.userEnabled = "TRUE";
        }
        else{
            this.userEnabled = "FALSE";
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String isUserEnabled() {
        return userEnabled;
    }

    public String getUserEnabled() {
        return userEnabled;
    }

    public void setUserEnabled(String userEnabled) {
        this.userEnabled = userEnabled;
    }
}
