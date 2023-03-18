package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Controllers;

import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ControllerTest {
    @Test
    void shouldDoSth(){

        String msg = URLEncoder.encode("Zostałeś poprawnie wylogowany",StandardCharsets.UTF_8);


        System.out.println(msg);

        String msg2 = URLDecoder.decode(msg, StandardCharsets.UTF_8);
        System.out.println(msg2);
    }

}
