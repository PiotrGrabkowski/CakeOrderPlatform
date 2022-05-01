package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "cloudinary.config")
@Configuration
public class CloudinaryConfigurationProperties {


    private String cloudName;
    private String apiKey;
    private String apiSecret;
    private String galleryFolder;
    private String examplesFolder;

    public CloudinaryConfigurationProperties() {
    }

    public CloudinaryConfigurationProperties(String cloudName, String apiKey, String apiSecret, String galleryFolder, String examplesFolder) {
        this.cloudName = cloudName;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.galleryFolder = galleryFolder;
        this.examplesFolder = examplesFolder;
    }

    public String getCloudName() {
        return cloudName;
    }

    public void setCloudName(String cloudName) {
        this.cloudName = cloudName;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public String getGalleryFolder() {
        return galleryFolder;
    }

    public void setGalleryFolder(String galleryFolder) {
        this.galleryFolder = galleryFolder;
    }

    public String getExamplesFolder() {
        return examplesFolder;
    }

    public void setExamplesFolder(String examplesFolder) {
        this.examplesFolder = examplesFolder;
    }
}
