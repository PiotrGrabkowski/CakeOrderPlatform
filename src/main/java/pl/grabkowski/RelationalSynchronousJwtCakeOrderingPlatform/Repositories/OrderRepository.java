package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository <Order, Long>, OrderRepositoryCustom {


    String findAllJPQLQuery = "SELECT DISTINCT o, t, i, u, ut, so FROM Order o LEFT JOIN FETCH o.orderTasteSet t LEFT JOIN FETCH o.image i LEFT JOIN FETCH o.user u LEFT JOIN FETCH u.userToken ut LEFT JOIN FETCH u.setOfOrders so";
    @Query(value = findAllJPQLQuery)
    List<Order> findAll();





    String findByUserJPQLQuery = "SELECT DISTINCT o, t, i, u, ut, so FROM Order o LEFT JOIN FETCH o.orderTasteSet t LEFT JOIN FETCH o.image i LEFT JOIN FETCH o.user u LEFT JOIN FETCH u.userToken ut LEFT JOIN FETCH u.setOfOrders so WHERE u.id = :userId";
    @Query(value = findByUserJPQLQuery)
    List<Order> findAllByUser(Long userId);

    String findByIdJPQLQuery = "SELECT DISTINCT o, t, i, u, ut, so FROM Order o LEFT JOIN FETCH o.orderTasteSet t LEFT JOIN FETCH o.image i LEFT JOIN FETCH o.user u LEFT JOIN FETCH u.userToken ut LEFT JOIN FETCH u.setOfOrders so WHERE o.id = :id";
    @Query(value = findByIdJPQLQuery)
    Optional<Order> findById(Long id);


}
