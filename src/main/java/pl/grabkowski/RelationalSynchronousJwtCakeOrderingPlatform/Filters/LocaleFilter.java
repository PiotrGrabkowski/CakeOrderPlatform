package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Filters;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@Service
public class LocaleFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       try{
           String stringLocale = request.getHeader("Accept-Language");
           if(stringLocale!= null){
               Locale locale = new Locale(stringLocale);
               RequestContextHolder.getRequestAttributes().setAttribute("locale", locale, RequestAttributes.SCOPE_REQUEST);

           }
           filterChain.doFilter(request, response);
       }
       finally {
           RequestContextHolder.resetRequestAttributes();
       }


    }
}
