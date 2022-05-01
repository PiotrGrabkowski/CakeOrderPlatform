package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.User;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Configuration
public class CreatingAdminClass {
    private final AdminConfigurationProperties adminConfigurationProperties;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public CreatingAdminClass(AdminConfigurationProperties adminConfigurationProperties, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.adminConfigurationProperties = adminConfigurationProperties;

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void createAdmin(){

        Optional<User> userOptional = userRepository.findByRole("ROLE_ADMIN");

        if (userOptional.isEmpty()){

            this.createNewAdmin();
        }
        else if (userOptional.isPresent()){

            User user = userOptional.get();
            String expectedUsername = this.adminConfigurationProperties.getUsername();
            String expectedPassword = this.adminConfigurationProperties.getPassword();
            if(!expectedUsername.equals(user.getUsername()) || !passwordEncoder.matches(expectedPassword,user.getPassword())){
                userRepository.delete(user);
                this.createNewAdmin();
            }
        }




    }

    private void createNewAdmin (){
        User user = new User();
        user.setUsername(adminConfigurationProperties.getUsername());
        user.setPassword(passwordEncoder.encode(adminConfigurationProperties.getPassword()));
        user.setRole("ROLE_ADMIN");
        user.setUserEnabled(true);
        userRepository.save(user);



    }
}
