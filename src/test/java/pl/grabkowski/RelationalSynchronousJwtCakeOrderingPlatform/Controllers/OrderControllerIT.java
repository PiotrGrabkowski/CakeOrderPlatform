package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Controllers;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.util.UriComponentsBuilder;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.OrderRequest;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Order;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Sql(value = "", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class OrderControllerIT {

    @LocalServerPort
    int port;
    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void shouldCreateOrder(){
        //given
        URI uri = URI.create("http://localhost:"+ port + "/orders/order");
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setDescription("desciption");
        orderRequest.setListOfTastes(List.of("taste1", "taste2"));
        orderRequest.setNumberOfServings("numberOfServings");
        orderRequest.setPhoneNumber(000L);
        orderRequest.setTypeOfProduct("typeOfProduct");
        RequestEntity requestEntity = RequestEntity.post(uri).body(orderRequest);

        //when

        ResponseEntity<Order> responseEntity = testRestTemplate.exchange(requestEntity, Order.class);
        Order order = responseEntity.getBody();

        //then

        assertEquals("desciption", order.getDescription());

    }




}