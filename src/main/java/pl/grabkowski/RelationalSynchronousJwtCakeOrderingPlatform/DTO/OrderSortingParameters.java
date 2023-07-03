package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO;

import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.OrderStatus;

public enum OrderSortingParameters {
    U_USERNAME("u.username"), U_NICKNAME("u.nickname"), O_DESCRIPTION("o.description"),
    O_TYPE_OF_PRODUCT("o.type_of_product"), O_ORDER_STATUS("o.order_status"),
    O_EVENT_DATE("o.event_date"), O_CREATION_DATE("o.creation_date"),
    O_PHONE_NUMBER("o.phone_number");
    private String value;
    OrderSortingParameters(String value){
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
    public static OrderSortingParameters parseString(String s){
        for(OrderSortingParameters parameter : OrderSortingParameters.values()){
            if(parameter.getValue().equals(s)){

                return parameter;
            }

        }
        return null;

    }
}
