package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Security;

import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Configuration.UriConfig;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Exceptions.AccessTimeoutException;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.JWT.JWTAuthenticationProvider;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

@Service
public class JWTFilter extends OncePerRequestFilter {
    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
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

                try {
                    jwtAuthenticationProvider.provideAuthentication(jwt);
                } catch (AccessTimeoutException e) {
                    logger.info(e.getClass() + " was thrown in " + OncePerRequestFilter.class + " with message: " + e.getMessage());
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    String URIComponent = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
                    response.getWriter().print(URIComponent);
                    return;
                }
                catch (JwtException e) {
                    logger.info(e.getClass() + " was thrown in " + OncePerRequestFilter.class + " with message: " + e.getMessage());
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    String URIComponent = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
                    response.getWriter().print(URIComponent);
                    return;
                }


        }
        filterChain.doFilter(request, response);
    }
}
