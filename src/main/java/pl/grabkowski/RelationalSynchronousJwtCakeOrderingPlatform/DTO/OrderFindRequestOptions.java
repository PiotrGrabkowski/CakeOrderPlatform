package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO;

import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Order;

public class OrderFindRequestOptions {
    private OrderFilterOptions orderFilterOptions;
    private Sort sort;
    private Page<Order>page;

    public OrderFindRequestOptions() {
    }

    public OrderFindRequestOptions(OrderFilterOptions orderFilterOptions, Sort sort, Page<Order> page) {
        this.orderFilterOptions = orderFilterOptions;
        this.sort = sort;
        this.page = page;
    }

    public OrderFilterOptions getOrderFilterOptions() {
        return orderFilterOptions;
    }

    public void setOrderFilterOptions(OrderFilterOptions orderFilterOptions) {
        this.orderFilterOptions = orderFilterOptions;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public Page<Order> getPage() {
        return page;
    }

    public void setPage(Page<Order> page) {
        this.page = page;
    }
}
