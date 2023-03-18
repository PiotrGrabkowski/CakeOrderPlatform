package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository <Order, Long> {

    String findAllNativeQuery = "SELECT id, creation_date, description, event_date, image_id, number_of_servings, order_status, phone_number, type_of_product, user_id FROM orders";
    @Query(value = findAllNativeQuery, nativeQuery = true)
    List<Order> findAll();

    String findByUserNativeQuery = "SELECT id, creation_date, description, event_date, image_id, number_of_servings, order_status, phone_number, type_of_product, user_id FROM orders WHERE user_id = :userId";
    @Query(value = findByUserNativeQuery, nativeQuery = true)
    List<Order> findAllByUser(Long userId);
}
