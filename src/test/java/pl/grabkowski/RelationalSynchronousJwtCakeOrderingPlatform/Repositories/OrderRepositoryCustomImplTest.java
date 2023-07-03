package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.OrderFilterOptions;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Order;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.OrderStatus;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.OrderTaste;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class OrderRepositoryCustomImplTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderRepositoryCustomImpl orderRepositoryCustom;
    private LocalDateTime now = LocalDateTime.now();
    private LocalDate eventDate = LocalDate.now();

    private static final Logger logger = LoggerFactory.getLogger(OrderRepositoryCustomImplTest.class);




    @BeforeEach
    void populateDatabase(){
        logger.info("Populating database");
        ///
        User user1 = new User();
        user1.setRole("ROLE_USER");
        user1.setUsername("ABC");
        user1.setNickname("abc");
        user1.setPhoneNumber(666777888L);
        user1.setUserEnabled(true);
        this.userRepository.save(user1);

        User user2 = new User();
        user2.setRole("ROLE_USER");
        user2.setUsername("CDE");
        user2.setNickname("cde");
        user2.setPhoneNumber(111222333L);
        user2.setUserEnabled(true);
        this.userRepository.save(user2);

        ///

        Order order = new Order();
        order.setOrderStatus(OrderStatus.NEW);
        order.setUser(user1);
        order.setCreationDate(now);
        order.setEventDate(eventDate);
        order.setDescription("silly description");
        order.setNumberOfServings("25");
        order.setTypeOfProduct("cake");
        order.setPhoneNumber(666777888L);
        Set<OrderTaste> orderTasteSet = new HashSet<>();
        OrderTaste orderTaste = new OrderTaste();
        orderTaste.setOrder(order);
        orderTaste.setName("chocolate");
        orderTasteSet.add(orderTaste);
        order.setOrderTasteSet(orderTasteSet);
        this.orderRepository.save(order);

        Order order2 = new Order();
        order2.setOrderStatus(OrderStatus.COMPLETED);
        order2.setUser(user1);
        order2.setCreationDate(now.plusDays(1));
        order2.setEventDate(eventDate.plusDays(10));
        order2.setDescription("serious description");
        order2.setNumberOfServings("35");
        order2.setTypeOfProduct("pancake");
        order2.setPhoneNumber(666777888L);
        Set<OrderTaste> orderTasteSet2 = new HashSet<>();
        OrderTaste orderTaste2 = new OrderTaste();
        orderTaste2.setOrder(order2);
        orderTaste2.setName("raspberry");
        orderTasteSet2.add(orderTaste2);
        order2.setOrderTasteSet(orderTasteSet2);
        this.orderRepository.save(order2);

        Order order3 = new Order();
        order3.setOrderStatus(OrderStatus.PROCESSED);
        order3.setUser(user1);
        order3.setCreationDate(now.plusDays(2));
        order3.setEventDate(LocalDate.of(2023, Month.JUNE,29));
        order3.setDescription("nothing to say");
        order3.setNumberOfServings("25");
        order3.setTypeOfProduct("cake");
        order3.setPhoneNumber(666777888L);
        Set<OrderTaste> orderTasteSet3 = new HashSet<>();
        OrderTaste orderTaste3 = new OrderTaste();
        orderTaste3.setOrder(order3);
        orderTaste3.setName("strawberry");
        orderTasteSet3.add(orderTaste3);
        order3.setOrderTasteSet(orderTasteSet3);
        this.orderRepository.save(order3);




    }
    @AfterEach
    void clearDatabase(){
        logger.info("Clearing database");
        this.orderRepository.deleteAll();
        this.userRepository.deleteAll();


    }


    @Test
    void shouldReturnFilteredItems(){
        OrderFilterOptions orderFilterOptions = new OrderFilterOptions();
        orderFilterOptions.setDescription("nothing");
        List<Order> list = this.orderRepository.findFiltered(orderFilterOptions,null,null).getListOfItems();
        Assertions.assertEquals(1, list.size());

    }

    @Test
    void testSth(){

        this.orderRepository.findFiltered(null,null,null).getListOfItems().forEach(order -> System.out.println(order.getDescription()));
    }

}