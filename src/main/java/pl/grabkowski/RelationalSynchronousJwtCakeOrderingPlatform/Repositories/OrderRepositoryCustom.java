package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories;

import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.OrderFilterOptions;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Order;

import java.util.List;

public interface OrderRepositoryCustom {

    List<Order> findFiltered(OrderFilterOptions orderFilterOptions);
}
