package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services;

import org.springframework.web.multipart.MultipartFile;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Image;

public interface ImageHostingService {

    Image upload (MultipartFile multipartFile, String destination);
    void delete (String outerServiceId);
}
