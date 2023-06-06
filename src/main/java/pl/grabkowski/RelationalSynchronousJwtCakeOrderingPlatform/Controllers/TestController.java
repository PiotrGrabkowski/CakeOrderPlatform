package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Controllers;

import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories.UserRepository;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
public class TestController {
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public TestController(UserRepository userRepository, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
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
    public String testForI18n(@RequestHeader(name = "Accept-Language", required = false) Locale locale){

        return this.messageSource.getMessage("test", new Object[]{}, locale);
    }

}
