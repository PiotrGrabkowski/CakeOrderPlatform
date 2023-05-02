package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model;

import java.util.List;

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

    public static OrderStatus parseString(String s){
       for(OrderStatus status : OrderStatus.values()){
           if(status.getValue().equals(s)){

               return status;
           }

       }
       return null;

    }


}
