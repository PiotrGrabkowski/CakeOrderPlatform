package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.OrderFilterOptions;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.JsonMultipartFile;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.OrderRequest;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.OrderResponse;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Exceptions.AuthorizationException;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Exceptions.NoSuchElementInDatabaseException;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.*;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories.*;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.SMS.SmsService;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Security.SecurityUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final NumberOfServingsRepository numberOfServingsRepository;
    private final TypeOfProductRepository typeOfProductRepository;
    private final TasteRepository tasteRepository;
    private final ImageService imageService;
    private final SmsService smsService;

    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        NumberOfServingsRepository numberOfServingsRepository,
                        TypeOfProductRepository typeOfProductRepository,
                        TasteRepository tasteRepository,
                        ImageService imageService, SmsService smsService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.numberOfServingsRepository = numberOfServingsRepository;
        this.typeOfProductRepository = typeOfProductRepository;
        this.tasteRepository = tasteRepository;
        this.imageService = imageService;

        this.smsService = smsService;
    }

    @Transactional
    public Order createOrder(OrderRequest orderRequest) {

        boolean authenticated = false;
        User user = null;
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        if(SecurityUtils.checkIfUserIsSignedIn()){

            authenticated = true;
        }

        Order newOrder = new Order();

        JsonMultipartFile jsonMultipartFile = orderRequest.getJsonMultipartFile();
        if(orderRequest.getJsonMultipartFile()!=null) {

            Image image = imageService.add(jsonMultipartFile, ImageDestination.USERS_EXAMPLES, null);
            newOrder.setImage(image);
        }



        if(authenticated){
            user = this.userRepository.findByUsername(authentication
                    .getName()).orElseThrow(() ->new UsernameNotFoundException("User not found."));
            user.addOrder(newOrder);


        }


        LocalDate eventDate = LocalDate.parse(orderRequest.getEventDate());
        newOrder.setEventDate(eventDate);
        newOrder.setCreationDate(LocalDateTime.now());
        newOrder.setPhoneNumber(orderRequest.getPhoneNumber());
        newOrder.setDescription(orderRequest.getDescription());
        newOrder.setNumberOfServings(orderRequest.getNumberOfServings());
        orderRequest.getListOfTastes().stream().forEach(taste -> newOrder.addTaste(new OrderTaste(taste)));
        newOrder.setTypeOfProduct(orderRequest.getTypeOfProduct());
        newOrder.setOrderStatus(OrderStatus.NEW);

       Order savedOrder = this.orderRepository.save(newOrder);
   /*      if(authenticated) {
            String msg = "Użytkownik: " + user.getNickname() +
                    " o numerze telefonu: " +
                    user.getPhoneNumber() +
                    " właśnie złożył zamówienie.";
            this.smsService.send(msg);
        }
        else {

            this.smsService.send();
        }*/

        return savedOrder;




    }

    public List<Order> getAllOrders(){
        return this.orderRepository.findAll();

    }

    public List<Order> getFilteredOrders(OrderFilterOptions orderFilterOptions){

        return this.orderRepository.findFiltered(orderFilterOptions);

    }
    public Order getOrderById(Long id){
       Order order =  this.orderRepository
                .findById(id)
               .orElseThrow(() -> new IllegalArgumentException("Cannot find order with this id"));
/*    unblock when everything is done

   User ordersUser = order.getUser();

        if(!SecurityUtils.checkIfUserIsSignedIn()){
            throw new AuthorizationException("Access denied");

        }
       String name = SecurityUtils.getSignedInUsersName();
       User user = this.userRepository.findByUsername(name).orElseThrow(() ->new UsernameNotFoundException("User not found."));
       if (!user.getRole().equals("ROLE_ADMIN")&& ordersUser!= null && user.getId().longValue()!=ordersUser.getId().longValue()){

          throw new AuthorizationException("Access denied");
       }*/

       return order;

    }
    public List<Order> getOrdersByUserId(Long id){

        if(!SecurityUtils.checkIfUserIsSignedIn()){
            throw new AuthorizationException("Access denied");

        }
        String name = SecurityUtils.getSignedInUsersName();
        User user = this.userRepository.findByUsername(name).orElseThrow(() ->new UsernameNotFoundException("User not found."));
        if (!user.getRole().equals("ROLE_ADMIN")&& user.getId().longValue()!=id.longValue()){

            throw new AuthorizationException("Access denied");
        }

        return this.orderRepository.findAllByUser(id);
    }
    @Transactional
    public void deleteOrderById(Long id){
        Order order = this.orderRepository.findById(id).orElseThrow(()-> new NoSuchElementInDatabaseException("Nie znaleziono zamówienia o podanym identyfikatorze."));
        Image image = order.getImage();
        if(image !=null){
            this.imageService.delete(image.getId());
        }

        this.orderRepository.deleteById(id);

    }
    public void deleteAll(){
        this.orderRepository.deleteAll();

    }
    public void changeOrderStatus(Long id, OrderStatus orderStatus){
        Order order = this.orderRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Order not found"));
        order.setOrderStatus(orderStatus);
        this.orderRepository.save(order);

    }

    public void update(OrderResponse orderResponse) {
    }
}
