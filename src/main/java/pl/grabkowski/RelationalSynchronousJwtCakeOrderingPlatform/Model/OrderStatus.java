package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model;

public enum OrderStatus {

    NEW("NEW"), PROCESSED("PROCESSED"), COMPLETED("COMPLETED");

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
