package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Configuration.ClientSideConfigurationProperties;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Configuration.UriConfig;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.LoginRequest;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.PasswordChangeDTO;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.RegisterRequest;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.UserDto;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Exceptions.ExternalServiceException;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Exceptions.NoSuchElementInDatabaseException;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Exceptions.UserAlreadyExistsException;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.JWT.JWTProvider;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.JWT.JWTTransferingObject;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.User;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.UserToken;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories.UserRepository;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories.UserTokenRepository;

import javax.mail.MessagingException;
import java.util.Random;
import java.util.UUID;

@Service
public class UserAccountManager {
    private final JWTProvider jwtProvider;
    private final UserTokenRepository userTokenRepository;
    private final MailService mailService;
    private final UserRepository userRepository;
    private final ClientSideConfigurationProperties clientSideConfigurationProperties;
    private final PasswordEncoder passwordEncoder;
    private final UriConfig uriConfig;
    private final AuthenticationManager authenticationManager;



    public UserAccountManager(JWTProvider jwtProvider, UserTokenRepository userTokenRepository, MailService mailService, UserRepository userRepository, ClientSideConfigurationProperties clientSideConfigurationProperties, PasswordEncoder passwordEncoder, UriConfig uriConfig, AuthenticationManager authenticationManager) {
        this.jwtProvider = jwtProvider;
        this.userTokenRepository = userTokenRepository;
        this.mailService = mailService;
        this.userRepository = userRepository;
        this.clientSideConfigurationProperties = clientSideConfigurationProperties;
        this.passwordEncoder = passwordEncoder;
        this.uriConfig = uriConfig;
        this.authenticationManager = authenticationManager;
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



        String confirmationEndpoint = clientSideConfigurationProperties.getUrl() +clientSideConfigurationProperties.getRegistrationConfirmationEndpoint() + userToken.getValue();
        String from = "PINKSAGITARIUS@alwaysdata.net";
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
        User user = userRepository.findById(id)
                .orElseThrow(()->new NoSuchElementInDatabaseException("Nie znaleziono użytkownika o podanym identyfikatorze."));
        userRepository.delete(user);

    }

    public void changePassword(PasswordChangeDTO passwordChangeDTO) {
        this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(passwordChangeDTO.getUserDTO().getUsername(), passwordChangeDTO.getOldPassword()));
        User user = this.userRepository.findByUsername(passwordChangeDTO.getUserDTO().getUsername()).orElseThrow(()->new UsernameNotFoundException("Nie znaleziono użytkownika o podanym adresie e-mail"));
        user.setPassword(this.passwordEncoder.encode(passwordChangeDTO.getNewPassword()));
        this.userRepository.save(user);
    }

    public void restorePassword(UserDto userDto) {
        String email = userDto.getUsername();
        String token = UUID.randomUUID().toString();
        User user = this.userRepository.findByUsername(email).orElseThrow(()->new UsernameNotFoundException("Nie znaleziono użytkownika o podanym adresie e-mail"));
        UserToken userToken = user.getUserToken();
        userToken.setValue(token);
        this.userTokenRepository.save(userToken);

        String confirmationEndpoint = clientSideConfigurationProperties.getUrl() +clientSideConfigurationProperties.getRestorationConfirmationEndpoint() + token;
        String from = "PINKSAGITARIUS@alwaysdata.net";
        String subject = "Prośba o potwierdzenie zmiany hasła na platformie do zamawiania tortów PINK SAGITARIUS";
        System.out.println(confirmationEndpoint);
        String content = "Klinkij w podany link w celu potiwerdzenia zmiany hasła:  " + "<a href=\"" +confirmationEndpoint +"\">Kliknij aby potwierdzic</a>";
        try {
            mailService.sendMail(user.getUsername(), from, subject, content, true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public void confirmRestoration(String tokenValue) {
        UserToken userToken = this.userTokenRepository.findByValue(tokenValue).orElseThrow(()->new RuntimeException(""));
        User user = userToken.getUser();
        int temporaryPassword = new Random().nextInt(9999);

        String msg = "Twoje hasło tymczasowe to: " + temporaryPassword + ". Wykorzystaj je do ponownego logowania. Później możesz je zmienić w ustawieniach.";
        String from = "PINKSAGITARIUS@alwaysdata.net";
        String subject = "Nowe hasło";
        String content = msg;
        try {
            mailService.sendMail(user.getUsername(), from, subject, content, true);
        } catch (MessagingException e) {
            throw new ExternalServiceException("Problem z wysłaniem wiadomości e-mail");
        }
        user.setPassword(this.passwordEncoder.encode(Integer.toString(temporaryPassword)));
        this.userRepository.save(user);

    }
}
