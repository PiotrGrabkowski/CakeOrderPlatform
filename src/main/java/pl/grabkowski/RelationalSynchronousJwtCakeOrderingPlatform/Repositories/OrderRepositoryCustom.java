package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories;

import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.OrderFilterOptions;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.Page;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.Sort;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Order;

import java.util.List;

public interface OrderRepositoryCustom {

    Page<Order>findFiltered(Sort sort, Page<Order> page);
    Page<Order>findFiltered(OrderFilterOptions orderFilterOptions, Page<Order> page);
    Page<Order> findFiltered(OrderFilterOptions orderFilterOptions, Sort sort, Page<Order> page);

    Page<Order> findFilteredByUserId (OrderFilterOptions orderFilterOptions, Sort sort, Page<Order> page, Long id);
}
