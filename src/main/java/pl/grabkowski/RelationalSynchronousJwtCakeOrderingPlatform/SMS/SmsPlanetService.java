package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.SMS;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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

    public void send(){
        this.send(
                this.smsPlanetConfigurationProperties.getMsg()
        );

    }


    @Override
    public void send(String msg)  {
        this.send(
                this.smsPlanetConfigurationProperties.getFrom(),
                this.smsPlanetConfigurationProperties.getTo(),
                msg
        );
    }

    @Override
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
        String URI = uri + "?" +
                "from=" + from +
                "&to=" + to +
                "&msg=" + msg +
                "&key="+ key +
                "&password=" + password;


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
