package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.SMS;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class SmsPlanetServiceTest {

    @Autowired
    private SmsPlanetService smsPlanetService;

    @Test
    void shouldSendSms(){
        String msg = "Użytkownik o numerze telefonu właśnie złożył zamówienie.";


     //  this.smsPlanetService.send(msg); !!!!! disabled in building jar!!!!!


    }

}