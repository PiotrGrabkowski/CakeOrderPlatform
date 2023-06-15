package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories;

import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.OrderFilterOptions;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.Sort;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Order;

import java.util.List;

public interface OrderRepositoryCustom {

    List<Order> findFiltered(OrderFilterOptions orderFilterOptions, Sort sort);
    List<Order>findFiltered(OrderFilterOptions orderFilterOptions);
    List<Order> findFilteredByUserId (OrderFilterOptions orderFilterOptions, Long id);
}
