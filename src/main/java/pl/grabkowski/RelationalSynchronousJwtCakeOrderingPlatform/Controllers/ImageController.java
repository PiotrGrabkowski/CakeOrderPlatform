package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.GalleryItem;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Image;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services.GalleryItemService;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services.ImageService;


import java.util.List;

@RestController
@RequestMapping("/image")
@CrossOrigin
public class ImageController {


    private final ImageService imageService;
    private final GalleryItemService galleryItemService;

    public ImageController( ImageService imageService, GalleryItemService galleryItemService) {

        this.imageService = imageService;
        this.galleryItemService = galleryItemService;
    }

    @PostMapping("/gallery")
    public ResponseEntity<String> uploadImageToGallery (@RequestParam("image") MultipartFile multipartFile1, @RequestParam("image-thumb") MultipartFile multipartFile2, @RequestParam("description") String description){
        this.galleryItemService.add(multipartFile1, multipartFile2, description);
        return ResponseEntity.status(HttpStatus.OK).body("Poprawnie dodano zdjęcie");
    }

    @DeleteMapping("/{public_id}")
    public ResponseEntity<String> deleteImage(@RequestBody Image image){
        this.imageService.delete(image);
        return ResponseEntity.status(HttpStatus.OK).body("Poprawnie usunięto zdjęcie");

    }

    @GetMapping ("/gallery/images")
    public List<GalleryItem> getAllImagesFromGallery(){

        return this.galleryItemService.getAll();
    }
}
