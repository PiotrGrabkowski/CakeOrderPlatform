package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO;

import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.User;

public class UserDto {
    private Long id;
    private String username; // Must be an email
    private String role;
    private String nickname;

    public UserDto() {
    }
    public UserDto(User user){
        if(user != null) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.role = user.getRole();
            this.nickname = user.getNickname();
        }

    }

    public UserDto(Long id, String username, String role, String nickname) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.nickname = nickname;
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
}
