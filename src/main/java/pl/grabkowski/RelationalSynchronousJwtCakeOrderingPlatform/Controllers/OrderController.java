package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Controllers;

import com.sun.mail.iap.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.OrderRequest;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Order;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.OrderStatus;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services.OrderService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/order")
    public ResponseEntity<Order>  createOrder(@RequestBody OrderRequest orderRequest, @RequestParam(name = "file", required = false)MultipartFile multipartFile) {

        return ResponseEntity.ok(orderService.createOrder(orderRequest, multipartFile));
    }
    @GetMapping("/all")
    public ResponseEntity<List<Order> >getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());

    }
    @GetMapping("/{id}")

    public ResponseEntity<Order> getOrderById(@PathVariable(name ="id") Long id){



        return ResponseEntity.ok(orderService.getOrderById(id));
    }



    @GetMapping("/all/{id}")
    public ResponseEntity<List<Order>> getOrdersByUsernameId(@PathVariable(name ="id")Long id){

        return ResponseEntity.ok(orderService.getOrdersByUsernameId(id));
    }
    @DeleteMapping("/{id}")
    public void deleteOrderById(@PathVariable(name ="id")Long id){
        this.orderService.deleteOrderById(id);
    }
    @DeleteMapping("/all")
    public void deleteAll(){
        this.orderService.deleteAll();

    }
    @PatchMapping("/{id}/{orderStatus}")
    public void changeOrderStatus(@PathVariable(name ="id")Long id, @PathVariable(name ="orderStatus")OrderStatus orderStatus){
        this.orderService.changeOrderStatus(id, orderStatus);

    }
}
