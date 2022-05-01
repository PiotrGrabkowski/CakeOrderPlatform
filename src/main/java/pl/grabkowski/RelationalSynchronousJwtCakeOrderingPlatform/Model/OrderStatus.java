package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model;

public enum OrderStatus {

    NEW("Nowe"), PROCESSED("W trakcie realizacji"), COMPLETED("Zrealizowane");

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    OrderStatus (String value){

        this.value = value;
    }


}
