package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "client.application")
@Configuration
public class ClientSideConfigurationProperties {
    private String url;
    private String confirmationEndpoint;

    public ClientSideConfigurationProperties(String url, String confirmationEndpoint) {
        this.url = url;
        this.confirmationEndpoint = confirmationEndpoint;
    }

    public ClientSideConfigurationProperties() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getConfirmationEndpoint() {
        return confirmationEndpoint;
    }

    public void setConfirmationEndpoint(String confirmationEndpoint) {
        this.confirmationEndpoint = confirmationEndpoint;
    }
}
