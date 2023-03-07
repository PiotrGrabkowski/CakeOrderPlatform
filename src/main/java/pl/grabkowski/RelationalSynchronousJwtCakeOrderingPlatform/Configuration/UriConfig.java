package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "server.application")
public class UriConfig {


    private String baseURL;

    public UriConfig() {
    }

    public UriConfig(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public static Set<String> getPublicUris(){
        Set<String> set = new HashSet<>();
        set.add("/image/gallery/images");
        return set;

    }
}
