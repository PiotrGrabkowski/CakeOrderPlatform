package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Controllers;

import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.OrderResponse;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Order;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories.OrderRepository;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories.UserRepository;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
public class TestController {
    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final OrderRepository orderRepository;

    public TestController(UserRepository userRepository, MessageSource messageSource, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
        this.orderRepository = orderRepository;
    }

    @GetMapping("test")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getTestMessage(){
        String user =  SecurityContextHolder.getContext().getAuthentication().getName();
        return "Hello " + user;

    }

    @GetMapping ("test/role")
    public List<String> findRole (){

        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .map(ga -> ga.getAuthority()).collect(Collectors.toList());






    }
    @GetMapping("testi18n")
    public String testForI18n(){


        Locale newLocale = (Locale)RequestContextHolder.getRequestAttributes().getAttribute("locale", RequestAttributes.SCOPE_REQUEST);
        return this.messageSource.getMessage("test", new Object[]{}, newLocale);
    }
    @GetMapping("testOrders")
    public List<OrderResponse> findOrders(){

       // return this.orderRepository.findFiltered(null).stream().map(order-> new OrderResponse(order)).collect(Collectors.toList());
        return null;

    }

}
