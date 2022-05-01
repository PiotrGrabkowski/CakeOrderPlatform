package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Configuration.ClientSideConfigurationProperties;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.LoginRequest;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.RegisterRequest;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Exceptions.NoSuchElementInDatabaseException;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Exceptions.UserAlreadyExistsException;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.JWT.JWTProvider;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.JWT.JWTTransferingObject;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.User;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.UserToken;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories.UserRepository;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories.UserTokenRepository;

import javax.mail.MessagingException;
import java.util.UUID;

@Service
public class UserAccountManager {
    private final JWTProvider jwtProvider;
    private final UserTokenRepository userTokenRepository;
    private final MailService mailService;
    private final UserRepository userRepository;
    private final ClientSideConfigurationProperties clientSideConfigurationProperties;
    private final PasswordEncoder passwordEncoder;



    public UserAccountManager(JWTProvider jwtProvider, UserTokenRepository userTokenRepository, MailService mailService, UserRepository userRepository, ClientSideConfigurationProperties clientSideConfigurationProperties, PasswordEncoder passwordEncoder) {
        this.jwtProvider = jwtProvider;
        this.userTokenRepository = userTokenRepository;
        this.mailService = mailService;
        this.userRepository = userRepository;
        this.clientSideConfigurationProperties = clientSideConfigurationProperties;
        this.passwordEncoder = passwordEncoder;
    }

    public JWTTransferingObject login (LoginRequest loginRequest){

        return jwtProvider.provideJWT(loginRequest);
    }

    @Transactional
    public void createUser (RegisterRequest registerRequest) throws MessagingException {

        String username = registerRequest.getUsername();
        if(userRepository.findByUsername(username).isPresent()){
            throw new UserAlreadyExistsException("Użytkownik o podanym adresie email już istnieje");

        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(this.passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole("ROLE_USER");
        user.setNickname(registerRequest.getNickname());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setUserEnabled(false);
        UserToken userToken = new UserToken();
        userToken.setValue(UUID.randomUUID().toString());
        userToken.setUser(user);
        user.setUserToken(userToken);
        userRepository.save(user);


        String confirmationEndpoint = clientSideConfigurationProperties.getUrl()+ "#/" +clientSideConfigurationProperties.getConfirmationEndpoint() + userToken.getValue();
        String from = "PINK_SAGITARIUS";
        String subject = "Prośba o potwierdzenie rejestracji na platformie do zamawiania tortów PINK SAGITARIUS";
        String content = "Klinkij w podany link w celu potiwerdzenia rejestracji:  " + "<a href=\"" +confirmationEndpoint +"\">Kliknij aby potwierdzic</a>";
        mailService.sendMail(user.getUsername(), from, subject, content, true);



    }
    public void confirmUser(String tokenValue){
       UserToken userToken = userTokenRepository.findByValue(tokenValue).orElseThrow(()-> new NoSuchElementInDatabaseException("Nieprawidłowy token użytkownika"));
       User user = userToken.getUser();
       user.setUserEnabled(true);
       userRepository.save(user);
    }

    public void deleteUserById (Long id){
        userRepository.findById(id)
                .orElseThrow(()->new UsernameNotFoundException("Nie znaleziono użytkownika o podanym identyfikatorze."));
        userRepository.deleteById(id);

    }

}
