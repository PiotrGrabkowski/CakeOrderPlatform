package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.LoginRequest;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.RegisterRequest;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.JWT.JWTProvider;

import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.JWT.JWTTransferingObject;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services.UserAccountManager;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/user")
@CrossOrigin(exposedHeaders = "*")
public class UserController {
    private final JWTProvider jwtProvider;
    private final UserAccountManager userAccountManager;

    public UserController(JWTProvider jwtProvider, UserAccountManager userAccountManager) {
        this.jwtProvider = jwtProvider;
        this.userAccountManager = userAccountManager;
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
    @PatchMapping ("/confirm/{tokenValue}")
    public ResponseEntity<String> confirmUser(@PathVariable(value = "tokenValue") String tokenValue){
        userAccountManager.confirmUser(tokenValue);
        return ResponseEntity.status(HttpStatus.OK).body("Konto zostało aktywowane");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById (@PathVariable Long id){
        userAccountManager.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Użytkownik został usunięty");
    }
}
