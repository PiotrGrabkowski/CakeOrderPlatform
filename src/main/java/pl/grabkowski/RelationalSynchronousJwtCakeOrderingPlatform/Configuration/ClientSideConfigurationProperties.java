package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "client.application")
@Configuration
public class ClientSideConfigurationProperties {
    private String url;
    private String registrationConfirmationEndpoint;
    private String restorationConfirmationEndpoint;

    public ClientSideConfigurationProperties(String url, String registrationConfirmationEndpoint, String restorationConfirmationEndpoint) {
        this.url = url;
        this.registrationConfirmationEndpoint = registrationConfirmationEndpoint;
        this.restorationConfirmationEndpoint = restorationConfirmationEndpoint;
    }

    public ClientSideConfigurationProperties() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRegistrationConfirmationEndpoint() {
        return registrationConfirmationEndpoint;
    }

    public void setRegistrationConfirmationEndpoint(String registrationConfirmationEndpoint) {
        this.registrationConfirmationEndpoint = registrationConfirmationEndpoint;
    }

    public String getRestorationConfirmationEndpoint() {
        return restorationConfirmationEndpoint;
    }

    public void setRestorationConfirmationEndpoint(String restorationConfirmationEndpoint) {
        this.restorationConfirmationEndpoint = restorationConfirmationEndpoint;
    }
}
