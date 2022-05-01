package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.SMS;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class SmsPlanetService implements SmsService{

    private final RestTemplate restTemplate;
    private final SmsPlanetConfigurationProperties smsPlanetConfigurationProperties;


    public SmsPlanetService(RestTemplate restTemplate, SmsPlanetConfigurationProperties smsPlanetConfigurationProperties) {
        this.restTemplate = restTemplate;
        this.smsPlanetConfigurationProperties = smsPlanetConfigurationProperties;
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void send(){
        this.send(
                this.smsPlanetConfigurationProperties.getMsg()
        );

    }


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void send(String msg)  {

        this.send(
                this.smsPlanetConfigurationProperties.getFrom(),
                this.smsPlanetConfigurationProperties.getTo(),
                msg
        );


    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void send(String from, String to, String msg) {
        this.createSendCall(
                this.smsPlanetConfigurationProperties.getUri(),
                from,
                to,
                msg,
                this.smsPlanetConfigurationProperties.getApiKey(),
                this.smsPlanetConfigurationProperties.getApiPassword()
        );

    }

    private void createSendCall(String uri,
                                String from,
                                String to,
                                String msg,
                                String key,
                                String password){

      String URI = UriComponentsBuilder.fromUriString(uri)
              .queryParam("key", key)
              .queryParam("password", password)
              .queryParam("from", from)
              .queryParam("msg", msg)
              .queryParam("to", to)
              .encode()
              .toUriString();
        String response = this.restTemplate.getForObject(URI, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map = null;
        try {
             map = objectMapper.readValue(response, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(map.get("errorMsg") != null){
            throw new SmsException("Problem with sending SMS");

        }




    }
}
