package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model;

public enum ImageDestination {
    GALLERY("GALLERY"), USERS_EXAMPLES("USERS_EXAMPLES");

    private String value;

    ImageDestination(String value){
        this.value = value;

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
