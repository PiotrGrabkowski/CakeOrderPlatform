package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.JWT;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Configuration.JWTConfigurationProperties;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Exceptions.AccessTimeoutException;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services.UserDetailsServiceImpl;

@Service
public class JWTAuthenticationProviderImpl implements JWTAuthenticationProvider {
    private final JWTConfigurationProperties jwtConfigurationProperties;
    private final UserDetailsServiceImpl userDetailsService;

    public JWTAuthenticationProviderImpl(JWTConfigurationProperties jwtConfigurationProperties, UserDetailsServiceImpl userDetailsService) {
        this.jwtConfigurationProperties = jwtConfigurationProperties;
        this.userDetailsService = userDetailsService;
    }
    private Jws<Claims> parseJwt (String jwt){
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtConfigurationProperties.getSecretKey().getBytes()))
                .build()
                .parseClaimsJws(jwt);

    }


    private boolean verifyJWT(String jwt) {
        try {
            parseJwt(jwt);
        } catch (ExpiredJwtException e) {

            throw new AccessTimeoutException("Sesja wygasła, zaloguj się ponownie");
        } catch (Exception e) {

            throw new JwtException("Błąd w procesie autoryzacji, zaloguj się jeszcze raz.");
        }
        return true;

    }

    @Override
    public void provideAuthentication(String jwt) {
        if(verifyJWT(jwt)){
            Claims body = parseJwt(jwt).getBody();
            String username = body.getSubject();
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            SecurityContextHolder.getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities()));

        }

    }
}
