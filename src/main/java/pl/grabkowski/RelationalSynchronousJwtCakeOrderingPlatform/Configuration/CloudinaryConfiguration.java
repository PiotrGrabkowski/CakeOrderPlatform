package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Configuration;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfiguration {

    private final CloudinaryConfigurationProperties cloudinaryConfigurationProperties;

    public CloudinaryConfiguration(CloudinaryConfigurationProperties cloudinaryConfigurationProperties) {
        this.cloudinaryConfigurationProperties = cloudinaryConfigurationProperties;
    }

    @Bean
    public Cloudinary cloudinary(){
        Map config = new HashMap();
        config.put("cloud_name", cloudinaryConfigurationProperties.getCloudName());
        config.put("api_key", cloudinaryConfigurationProperties.getApiKey());
        config.put("api_secret", cloudinaryConfigurationProperties.getApiSecret());
        Cloudinary cloudinary = new Cloudinary(config);
        return cloudinary;

    }
}
