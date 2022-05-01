package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services;

import org.springframework.stereotype.Service;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Configuration.CloudinaryConfigurationProperties;

@Service
public class ImagesDestinationProviderImpl implements ImagesDestinationsProvider {

    private final CloudinaryConfigurationProperties cloudConfigProps;

    public ImagesDestinationProviderImpl(CloudinaryConfigurationProperties cloudConfigProps) {
        this.cloudConfigProps = cloudConfigProps;
    }

    @Override
    public String getGalleryDestination() {
        return cloudConfigProps.getGalleryFolder();
    }

    @Override
    public String getUsersExamplesDestination() {
        return cloudConfigProps.getExamplesFolder();
    }
}
