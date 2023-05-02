package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Exceptions.ExternalServiceException;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Image;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryImageHostingService implements ImageHostingService{

    private final Cloudinary cloudinary;

    public CloudinaryImageHostingService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }



    @Override
    public Image upload(MultipartFile multipartFile, String destination) {
        Map<String, String> options = new HashMap<>();
        options.put("folder", destination);
        Map<String, String> map = null;
        try {
            map = cloudinary.uploader().upload(multipartFile.getBytes(), options);
        } catch (IOException e) {
            throw new ExternalServiceException("Wystąpił problem z dodaniem zdjęcia do zewnętrznego serwisu. Spróbuj jeszcze raz.");
        }
        if (map == null){

            throw new ExternalServiceException("Wystąpił problem z dodaniem zdjęcia do zewnętrznego serwisu. Spróbuj jeszcze raz.");
        }

        Image image = new Image();
        image.setUrl(map.get("url"));
        image.setOuterServiceId(map.get("public_id"));
        return image;
    }

    @Override
    public void delete(String outerServiceId) {

        Map response = null;
        try {
            response = cloudinary.uploader().destroy(outerServiceId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new ExternalServiceException(e.getMessage());
        }
        if(response.get("result").equals("not found")) {
            throw new ExternalServiceException("Nie znaleziono zdjęcia o podanym public_id");

        }

    }
}
