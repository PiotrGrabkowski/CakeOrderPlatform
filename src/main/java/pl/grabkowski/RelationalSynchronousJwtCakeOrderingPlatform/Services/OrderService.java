package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.OrderRequest;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.*;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories.*;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.SMS.SmsService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final NumberOfServingsRepository numberOfServingsRepository;
    private final TypeOfProductRepository typeOfProductRepository;
    private final TasteRepository tasteRepository;
    private final ImageService imageService;
    private final ImagesDestinationsProvider imagesDestinationsProvider;
    private final SmsService smsService;

    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        NumberOfServingsRepository numberOfServingsRepository,
                        TypeOfProductRepository typeOfProductRepository,
                        TasteRepository tasteRepository,
                        ImageService imageService,
                        ImagesDestinationsProvider imagesDestinationsProvider, SmsService smsService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.numberOfServingsRepository = numberOfServingsRepository;
        this.typeOfProductRepository = typeOfProductRepository;
        this.tasteRepository = tasteRepository;
        this.imageService = imageService;
        this.imagesDestinationsProvider = imagesDestinationsProvider;
        this.smsService = smsService;
    }

    @Transactional
    public void createOrder(OrderRequest orderRequest, MultipartFile multipartFile) {
        boolean authenticated = false;
        User user = null;
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated()&& !(authentication ==null)&& !(authentication instanceof AnonymousAuthenticationToken)){

            authenticated = true;
        }

        Order newOrder = new Order();

        if(multipartFile != null && !multipartFile.isEmpty()) {
            Image image = imageService.add(multipartFile, imagesDestinationsProvider.getUsersExamplesDestination());
            newOrder.setImage(image);
        }


        if(authenticated){
            user = this.userRepository.findByUsername(authentication
                    .getName()).orElseThrow(() ->new UsernameNotFoundException("User not found."));
            user.addOrder(newOrder);


        }
        newOrder.setPhoneNumber(orderRequest.getPhoneNumber());
        newOrder.setDescription(orderRequest.getDescription());
        newOrder.setExampleLink(orderRequest.getExampleLink());
        newOrder.setNumberOfServings(orderRequest.getNumberOfServings());
        newOrder.setSetOfTastes(orderRequest.getListOfTastes().stream().collect(Collectors.toSet()));
        newOrder.setTypeOfProduct(orderRequest.getTypeOfProduct());
        newOrder.setOrderStatus(OrderStatus.NEW);

        this.orderRepository.save(newOrder);
        if(authenticated) {
            String msg = "Użytkownik: " + user.getNickname() +
                    " o numerze telefonu: " +
                    user.getPhoneNumber() +
                    " właśnie złożył zamówienie.";
            this.smsService.send(msg);
        }
        else {

            this.smsService.send();
        }




    }

    public List<Order> getAllOrders(){
        return this.orderRepository.findAll();

    }
    public Order getOrderById(Long id){



       Order order =  this.orderRepository
                .findById(id)
               .orElseThrow(() -> new IllegalArgumentException("Cannot find order with this id"));
       SecurityContextHolder.getContext()
               .getAuthentication().getName();

       return order;

    }
    public List<Order> getOrdersByUsernameId(Long id){

        return this.orderRepository.findAllByUser(id);
    }
    public void deleteOrderById(Long id){
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
}
