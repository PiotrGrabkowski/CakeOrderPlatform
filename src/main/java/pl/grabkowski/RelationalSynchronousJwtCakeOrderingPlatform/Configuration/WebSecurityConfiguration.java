package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Filters.LocaleFilter;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Security.JWTFilter;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JWTFilter jwtFilter;
    private final LocaleFilter localeFilter;

    public WebSecurityConfiguration(UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder, JWTFilter jwtFilter, LocaleFilter localeFilter) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtFilter = jwtFilter;
        this.localeFilter = localeFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .and()
                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(localeFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/user/*").permitAll()
                .antMatchers("/orders/order").permitAll()
                .antMatchers("/image/gallery/images").permitAll()
                .anyRequest().permitAll(); //to change when everything is done


    }





    

}
