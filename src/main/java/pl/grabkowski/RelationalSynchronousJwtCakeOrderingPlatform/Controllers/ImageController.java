package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Image;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services.ImageService;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services.ImagesDestinationsProvider;


import java.util.List;

@RestController
@RequestMapping("/image")
@CrossOrigin
public class ImageController {


    private final ImageService imageService;
    private final ImagesDestinationsProvider imagesDestinationsProvider;

    public ImageController(ImageService imageService, ImagesDestinationsProvider imagesDestinationsProvider) {

        this.imageService = imageService;

        this.imagesDestinationsProvider = imagesDestinationsProvider;
    }

    @PostMapping("/gallery")
    public ResponseEntity<String> uploadImageToGallery (@RequestParam("image") MultipartFile multipartFile,  @RequestParam("description") String description){
        this.imageService.add(multipartFile,this.imagesDestinationsProvider.getGalleryDestination(), description);
        return ResponseEntity.status(HttpStatus.OK).body("Poprawnie dodano zdjęcie");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable(name = "id") long id){
        this.imageService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Poprawnie usunięto zdjęcie");

    }

    @GetMapping ("/gallery/images")
    public List<Image> getAllImagesFromGallery(){

        return this.imageService.getAll();
    }
}
