package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO;

public class PasswordChangeDTO {
    private UserDto userDTO;
    private String oldPassword;
    private String newPassword;

    public PasswordChangeDTO() {
    }

    public UserDto getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDto userDTO) {
        this.userDTO = userDTO;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
