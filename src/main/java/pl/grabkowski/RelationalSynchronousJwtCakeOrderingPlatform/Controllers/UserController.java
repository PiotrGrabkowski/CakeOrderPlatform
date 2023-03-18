package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Configuration.ClientSideConfigurationProperties;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.LoginRequest;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.RegisterRequest;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.JWT.JWTProvider;

import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.JWT.JWTTransferingObject;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services.UserAccountManager;

import javax.mail.MessagingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/user")
@CrossOrigin(exposedHeaders = "*")
public class UserController {
    private final JWTProvider jwtProvider;
    private final UserAccountManager userAccountManager;

    private final ClientSideConfigurationProperties clientProperties;

    public UserController(JWTProvider jwtProvider, UserAccountManager userAccountManager, ClientSideConfigurationProperties clientProperties) {
        this.jwtProvider = jwtProvider;
        this.userAccountManager = userAccountManager;
        this.clientProperties = clientProperties;
    }

    @PostMapping("/login")

    public ResponseEntity<String> login (@RequestBody LoginRequest loginRequest){

        JWTTransferingObject jwtTransferingObject = userAccountManager.login(loginRequest);
        String jwt = jwtTransferingObject.getJwt();
        String role = jwtTransferingObject.getUser().getRole();


        return ResponseEntity.status(HttpStatus.OK)
                .header("Authorization", "Bearer " + jwt)
                .header("Role", role)
                .body("Zostałeś poprawnie zalogowany");


    }

    @PostMapping ("/register")
    public ResponseEntity<String> createUser (@RequestBody RegisterRequest registerRequest) throws MessagingException {

        userAccountManager.createUser(registerRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Na podany email wysłano link aktywacyjny, prosimy o aktywację konta");

    }
    @GetMapping ("/confirm/{tokenValue}")
    public ResponseEntity<Void> confirmUser(@PathVariable(value = "tokenValue") String tokenValue){
        userAccountManager.confirmUser(tokenValue);

        String msg = "Konto zostało aktywowane";
        String URIComponent = URLEncoder.encode(msg, StandardCharsets.UTF_8);
        String redirectionUri = clientProperties.getUrl()+ "/#/responseView/" + URIComponent;
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(redirectionUri)).build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById (@PathVariable(name = "id") Long id){
        userAccountManager.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Użytkownik został usunięty");
    }
}
