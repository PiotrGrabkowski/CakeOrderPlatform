package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO;

public class Sort {
    private OrderSortingParameters parameter;
    private SortingDirection sortingDirection;

    public Sort() {
    }

    public Sort(OrderSortingParameters parameter, SortingDirection sortingDirection) {
        this.parameter = parameter;
        this.sortingDirection = sortingDirection;
    }

    public OrderSortingParameters getParameter() {
        return parameter;
    }

    public void setParameter(OrderSortingParameters parameter) {
        this.parameter = parameter;
    }

    public SortingDirection getSortingDirection() {
        return sortingDirection;
    }

    public void setSortingDirection(SortingDirection sortingDirection) {
        this.sortingDirection = sortingDirection;
    }
}
