package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository <Order, Long> {

    static final String findAllNativeQuery = "SELECT id, user_id, phone_number, type_of_product, number_of_servings, order_status FROM orders";
    @Query(value = findAllNativeQuery, nativeQuery = true)
    List<Order> findAll();

    static final String findByUserNativeQuery = "SELECT id, user_id, phone_number, type_of_product, number_of_servings, order_status FROM orders WHERE user_id = :userId";
    @Query(value = findByUserNativeQuery, nativeQuery = true)
    List<Order> findAllByUser(Long userId);
}
