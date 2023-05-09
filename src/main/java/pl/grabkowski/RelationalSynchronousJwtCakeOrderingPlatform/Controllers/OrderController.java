package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.OrderFilterOptions;
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
    @PostMapping("/filtered")
    public ResponseEntity<List<OrderResponse> >getFilteredOrders(@RequestBody OrderFilterOptions orderFilterOptions){

       return ResponseEntity.ok(orderService.getFilteredOrders(orderFilterOptions).stream().map(order-> new OrderResponse(order)).collect(Collectors.toList()));

    }
    @PostMapping("/filtered/user/{id}")
    public ResponseEntity<List<OrderResponse>>getFilteredOrdersByUserId(@RequestBody OrderFilterOptions orderFilterOptions, @PathVariable(name ="id") Long id){

        return ResponseEntity.ok(orderService.getFilteredByUserId(orderFilterOptions, id).stream().map(order-> new OrderResponse(order)).collect(Collectors.toList()));

    }
    @GetMapping("/{id}")

    public ResponseEntity<OrderResponse> getOrderById(@PathVariable(name ="id") Long id){



        return ResponseEntity.ok(new OrderResponse(orderService.getOrderById(id)));
    }



    @GetMapping("/all/{id}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUsernameId(@PathVariable(name ="id")Long id){

        return ResponseEntity.ok(orderService.getOrdersByUserId(id).stream().map(order-> new OrderResponse(order)).collect(Collectors.toList()));
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
