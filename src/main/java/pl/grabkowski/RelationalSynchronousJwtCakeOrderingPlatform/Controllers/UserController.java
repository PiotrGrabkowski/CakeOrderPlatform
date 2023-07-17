package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Configuration.ClientSideConfigurationProperties;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.*;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.JWT.JWTProvider;

import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.JWT.JWTTransferingObject;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.User;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services.UserAccountManager;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

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
    @PostMapping("/all")
    public ResponseEntity<Page<UserDto>> getAllUsers(@RequestBody UserFindRequestOptions userFindRequestOptions){
        return ResponseEntity.ok(this.userAccountManager.getAllUsers(userFindRequestOptions));
    }

    @PostMapping("/login")

    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest loginRequest,
                                                @RequestHeader(name = "Accept-Language") Locale locale){

        JWTTransferingObject jwtTransferingObject = userAccountManager.login(loginRequest, locale);
        String jwt = jwtTransferingObject.getJwt();
        LoginResponse loginResponse = new LoginResponse(jwtTransferingObject.getUser(),
                jwtTransferingObject.getMsg());




        return ResponseEntity.status(HttpStatus.OK)
                .header("Authorization", "Bearer " + jwt)
                .body(loginResponse);


    }
    @GetMapping("/presence/{username}")
    public ResponseEntity<Boolean> checkIfUserAlreadyExists(@PathVariable(name = "username") String username){
        boolean isPresent = this.userAccountManager.checkIfUserAlreadyExists(username);
        return ResponseEntity.ok(isPresent);
    }

    @PostMapping ("/register")
    public ResponseEntity<String> createUser (@RequestBody RegisterRequest registerRequest) throws MessagingException {

        userAccountManager.createUser(registerRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Na podany email wysłano link aktywacyjny, prosimy o aktywację konta");

    }
    @GetMapping ("/registrationConfirmation/{tokenValue}")
    public ResponseEntity<String> confirmUser(@PathVariable(value = "tokenValue") String tokenValue){
        userAccountManager.confirmUser(tokenValue);

       // String msg = "Konto zostało aktywowane";
       // String URIComponent = URLEncoder.encode(msg, StandardCharsets.UTF_8);
        // String redirectionUri = clientProperties.getUrl()+ "/#/responseView/" + URIComponent;
        return ResponseEntity.status(HttpStatus.OK).body("Konto zostało aktywowane");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById (@PathVariable(name = "id") Long id){
        userAccountManager.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Użytkownik został usunięty");
    }

    @PostMapping("/passwordChange")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO){

        this.userAccountManager.changePassword(passwordChangeDTO);
        return ResponseEntity.ok("Hasło zostało zmienione");
    }
    @PostMapping("/passwordRestoration")
    public ResponseEntity<String> restorePassword(@RequestBody UserDto userDto){

        this.userAccountManager.restorePassword(userDto);
        return ResponseEntity.ok("Na podany e-mail wysłano link resetujący hasło. Prosimy o postępowanie zgodnie ze wskazówkami.");
    }
    @GetMapping("/restorationConfirmation/{tokenValue}")
    public ResponseEntity<String> confirmRestoration(@PathVariable(value = "tokenValue") String tokenValue){
        this.userAccountManager.confirmRestoration(tokenValue);
        return ResponseEntity.ok("Nowe hasło zostało wysłane na Twój adres e-mail");

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id")Long id){
        User user = this.userAccountManager.getById(id);
        UserDto userDto = new UserDto(user);
        return ResponseEntity.ok(userDto);
    }
    @PatchMapping()
    public ResponseEntity<String> updateUser(@RequestBody UserDto userDto){

        this.userAccountManager.update(userDto);
        return ResponseEntity.ok("Poprawnie zaktualizowano dane użytkownika");
    }
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateAccountStatus(@PathVariable(name = "id")Long id,
                                                      @RequestParam(name = "active")boolean active){

        this.userAccountManager.updateUserStatus(id, active);
        return ResponseEntity.ok("Poprawnie zaktualizowano status użytkownika");
    }
}
