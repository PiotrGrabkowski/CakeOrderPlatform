package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Controllers;

import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.*;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Order;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.OrderStatus;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services.OrderService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@CrossOrigin
public class OrderController {

    private final OrderService orderService;
    private final MessageSource messageSource;

    public OrderController(OrderService orderService, MessageSource messageSource) {
        this.orderService = orderService;
        this.messageSource = messageSource;
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
    public ResponseEntity<Page<OrderResponse>>getFilteredOrders(@RequestBody OrderFindRequestOptions orderFindRequestOptions){
        Page<OrderResponse> page = this.convertPageOfOrdersToPageOfOrderResponses(this.orderService.getFilteredOrders(orderFindRequestOptions));

       return ResponseEntity.ok(page);

    }
    @PostMapping("/filtered/user/{id}")
    public ResponseEntity<Page<OrderResponse>>getFilteredOrdersByUserId(@RequestBody OrderFindRequestOptions orderFindRequestOptions, @PathVariable(name ="id") Long id){

        Page<OrderResponse> page = this.convertPageOfOrdersToPageOfOrderResponses(this.orderService.getFilteredByUserId(orderFindRequestOptions, id));

        return ResponseEntity.ok(page);

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
    @GetMapping("/order_sorting_parameters")
    public ResponseEntity<List<OrderSortingParameterDto>> getSortingParameters(){
        Locale locale = (Locale) RequestContextHolder.getRequestAttributes().getAttribute("locale", RequestAttributes.SCOPE_REQUEST);
        List<OrderSortingParameterDto> list = new ArrayList<>();
        Arrays.asList(OrderSortingParameters.values()).forEach(value ->{
            String displayedName =  this.messageSource.getMessage("order.sorting."+ value, new Object[]{}, locale);
            OrderSortingParameterDto dto = new OrderSortingParameterDto();
            dto.setDisplayedName(displayedName);
            dto.setDatabaseParameter(value);
            list.add(dto);
        });

        return ResponseEntity.ok(list);


    }



    private Page<OrderResponse> convertPageOfOrdersToPageOfOrderResponses(Page<Order>page){
        List<OrderResponse> list = page.getListOfItems().stream().map(order-> new OrderResponse(order)).collect(Collectors.toList());
        Page<OrderResponse> returnedPage = new Page<>();
        returnedPage.setCurrentPage(page.getCurrentPage());
        returnedPage.setNumberOfPages(page.getNumberOfPages());
        returnedPage.setItemsPerPage(page.getItemsPerPage());
        returnedPage.setOffset(page.getOffset());
        returnedPage.setTotalNumberOfItems(page.getTotalNumberOfItems());
        returnedPage.setListOfItems(list);
        return returnedPage;

    }
}
