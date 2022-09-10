package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Image;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services.ImageService;


import java.util.List;

@RestController
@RequestMapping("/image")
@CrossOrigin
public class ImageController {


    private final ImageService imageService;

    public ImageController( ImageService imageService) {

        this.imageService = imageService;

    }

    @PostMapping("/gallery")
    public ResponseEntity<String> uploadImageToGallery (@RequestParam("image") MultipartFile multipartFile,  @RequestParam("description") String description){
        this.imageService.add(multipartFile, description);
        return ResponseEntity.status(HttpStatus.OK).body("Poprawnie dodano zdjęcie");
    }

    @DeleteMapping("/{public_id}")
    public ResponseEntity<String> deleteImage(@RequestBody Image image){
        this.imageService.delete(image);
        return ResponseEntity.status(HttpStatus.OK).body("Poprawnie usunięto zdjęcie");

    }

    @GetMapping ("/gallery/images")
    public List<Image> getAllImagesFromGallery(){

        return this.imageService.getAll();
    }
}
