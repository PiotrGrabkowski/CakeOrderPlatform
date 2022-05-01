package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Security;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Configuration.UriConfig;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.JWT.JWTAuthenticationProvider;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class JWTFilter extends OncePerRequestFilter {
    private final JWTAuthenticationProvider jwtAuthenticationProvider;

    public JWTFilter(JWTAuthenticationProvider jwtAuthenticationProvider) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        String requestUri = request.getRequestURI();
        Set<String> setOfPublicUris = UriConfig.getPublicUris();


        if(StringUtils.hasText(header) && header.startsWith("Bearer ")){
            String jwt = header.substring(7);
            if(!setOfPublicUris.contains(requestUri))

            jwtAuthenticationProvider.provideAuthentication(jwt);


        }
        filterChain.doFilter(request, response);
    }
}
