package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO;

public class OrderSortingParameterDto {
    private String displayedName;
    private OrderSortingParameters databaseParameter;

    public OrderSortingParameterDto() {
    }

    public OrderSortingParameterDto(String displayedName, OrderSortingParameters databaseParameter) {
        this.displayedName = displayedName;
        this.databaseParameter = databaseParameter;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public void setDisplayedName(String displayedName) {
        this.displayedName = displayedName;
    }

    public OrderSortingParameters getDatabaseParameter() {
        return databaseParameter;
    }

    public void setDatabaseParameter(OrderSortingParameters databaseParameter) {
        this.databaseParameter = databaseParameter;
    }
}
