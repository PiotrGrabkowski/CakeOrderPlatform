package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.GalleryItem;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Image;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories.GalleryItemRepository;

import java.util.List;

@Service
public class GalleryItemService {

    private final GalleryItemRepository galleryItemRepository;
    private final ImagesDestinationsProvider imagesDestinationsProvider;
    private final ImageService imageService;


    public GalleryItemService(GalleryItemRepository galleryItemRepository, ImagesDestinationsProvider imagesDestinationsProvider, ImageService imageService) {
        this.galleryItemRepository = galleryItemRepository;
        this.imagesDestinationsProvider = imagesDestinationsProvider;
        this.imageService = imageService;
    }





    @Transactional
    public void add(MultipartFile file1, MultipartFile file2, String description) {
        final String destination = this.imagesDestinationsProvider.getGalleryDestination();
       Image image1 = this.imageService.add(file1, destination);
       Image image2 = this.imageService.add(file2, destination);
       GalleryItem galleryItem = new GalleryItem();
       galleryItem.setImage(image1);
       galleryItem.setImageThumb(image2);
       galleryItem.setDescription(description);
       this.galleryItemRepository.save(galleryItem);



    }


    public List<GalleryItem> getAll() {
        return this.galleryItemRepository.findAll();
    }


    public GalleryItem getById(Long id) {
        return this.galleryItemRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Nie znaleziono rekordu o podanym parametrze"));
    }


    public void updateById(Long id, String arg) {

    }


    public void deleteAll() {


    }


    public void deleteById(Long id) {

    }
}
