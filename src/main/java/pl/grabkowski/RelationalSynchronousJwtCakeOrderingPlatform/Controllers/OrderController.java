package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.OrderRequest;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.OrderResponse;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Order;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.OrderStatus;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/order")
    public ResponseEntity<String>  createOrder(@RequestBody OrderRequest orderRequest) {

        orderService.createOrder(orderRequest);
        return ResponseEntity.ok("Zamówienie zostało przyjęte do realizacji");
    }
    @PostMapping("/order/authorized")
    public ResponseEntity<String>  createAuthorizedOrder(@RequestBody OrderRequest orderRequest){
        orderService.createOrder(orderRequest);
        return ResponseEntity.ok("Zamówienie zostało przyjęte do realizacji");

    }
    @GetMapping("/all")
    public ResponseEntity<List<OrderResponse> >getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders().stream().map(order-> new OrderResponse(order)).collect(Collectors.toList()));

    }
    @GetMapping("/{id}")

    public ResponseEntity<OrderResponse> getOrderById(@PathVariable(name ="id") Long id){



        return ResponseEntity.ok(new OrderResponse(orderService.getOrderById(id)));
    }



    @GetMapping("/all/{id}")
    public ResponseEntity<List<Order>> getOrdersByUsernameId(@PathVariable(name ="id")Long id){

        return ResponseEntity.ok(orderService.getOrdersByUserId(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderById(@PathVariable(name ="id")Long id){
        this.orderService.deleteOrderById(id);
        return ResponseEntity.ok("Poprawnie usunięto zamówienie");
    }
    @DeleteMapping("/all")
    public void deleteAll(){
        this.orderService.deleteAll();

    }
    @PatchMapping("/order")
    public ResponseEntity<String> updateOrder(@RequestBody OrderResponse orderResponse){
        this.orderService.update(orderResponse);
        return ResponseEntity.ok("Poprawnie zaktualizowano zamówienie");

    }
    @PatchMapping("/order/status")
    public ResponseEntity<String> updateOrderStatus(@RequestBody OrderResponse orderResponse){
        long id = orderResponse.getId();
        OrderStatus status = orderResponse.getOrderStatus();
        this.orderService.changeOrderStatus(id, status);
        return ResponseEntity.ok("Poprawnie zaktualizowano status zamówienia");

    }
}
