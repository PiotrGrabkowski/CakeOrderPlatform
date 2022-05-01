package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.User;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories.UserRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TestController {
    private final UserRepository userRepository;

    public TestController(UserRepository userRepository) {
        this.userRepository = userRepository;
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

}
