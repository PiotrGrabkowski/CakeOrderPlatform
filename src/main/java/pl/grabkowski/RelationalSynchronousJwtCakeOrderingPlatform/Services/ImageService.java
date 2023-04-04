package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services;

import org.springframework.web.multipart.MultipartFile;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Image;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.ImageDestination;

import java.util.List;

public interface ImageService {
    Image add(MultipartFile multipartFile, ImageDestination imageDestination, String description);
    void delete(long id);
    void deleteAll();
    void update(Image image, MultipartFile multipartFile, String destination);
    Image getById(Long id);
    List<Image> getAll();
    List<Image> getAllByImageDestination(ImageDestination imageDestination);


}
