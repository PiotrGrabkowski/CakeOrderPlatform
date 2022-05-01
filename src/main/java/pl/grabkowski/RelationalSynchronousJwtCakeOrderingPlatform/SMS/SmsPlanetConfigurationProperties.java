package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.SMS;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "smsplanet.config")
public class SmsPlanetConfigurationProperties {

    private String apiKey;
    private String apiPassword;
    private String from;
    private String to;
    private String msg;
    private String uri;

    public SmsPlanetConfigurationProperties(String apiKey,
                                            String apiPassword,
                                            String from,
                                            String to,
                                            String msg,
                                            String uri) {
        this.apiKey = apiKey;
        this.apiPassword = apiPassword;
        this.from = from;
        this.to = to;
        this.msg = msg;
        this.uri = uri;
    }

    public SmsPlanetConfigurationProperties() {
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiPassword() {
        return apiPassword;
    }

    public void setApiPassword(String apiPassword) {
        this.apiPassword = apiPassword;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUri() {
        return uri;
    }


    public void setUri(String uri) {
        this.uri = uri;
    }
}
