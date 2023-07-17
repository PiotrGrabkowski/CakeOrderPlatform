package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.JWT;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Configuration.JWTConfigurationProperties;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.LoginRequest;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.UserDto;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.User;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories.UserRepository;

import java.util.Date;
import java.util.Locale;

@Service
public class JWTProviderImpl implements JWTProvider{

    private final AuthenticationManager authenticationManager;
    private final JWTConfigurationProperties jwtConfigurationProperties;
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public JWTProviderImpl(AuthenticationManager authenticationManager, JWTConfigurationProperties jwtConfigurationProperties, UserRepository userRepository, MessageSource messageSource) {
        this.authenticationManager = authenticationManager;
        this.jwtConfigurationProperties = jwtConfigurationProperties;
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    @Override
    public JWTTransferingObject provideJWT(LoginRequest loginRequest, Locale locale) {
        String username = loginRequest.getUsername();
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("Nie znaleziono użytkownika o podanym adresie email"));
        UserDto userDto = new UserDto(user);

        Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, loginRequest.getPassword()));

        Long now = System.currentTimeMillis();
        Long expirationTime = now + jwtConfigurationProperties.getExpirationTime();


        String msg = this.messageSource.getMessage("user.login", new Object[]{}, locale);



        String jwt = Jwts.builder()
                .setSubject(authenticate.getName())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(expirationTime))
                .signWith(Keys.hmacShaKeyFor(jwtConfigurationProperties.getSecretKey().getBytes()))
                .compact();
        return  new JWTTransferingObject(jwt,userDto,msg);

    }
}
