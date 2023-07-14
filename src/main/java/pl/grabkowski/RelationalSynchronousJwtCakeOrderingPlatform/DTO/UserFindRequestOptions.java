package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO;

public class UserFindRequestOptions {
    private UserDto user;
    private Page<UserDto> page;

    public UserFindRequestOptions() {
    }

    public UserFindRequestOptions(UserDto user, Page<UserDto> page) {
        this.user = user;
        this.page = page;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public Page<UserDto> getPage() {
        return page;
    }

    public void setPage(Page<UserDto> page) {
        this.page = page;
    }
}
