package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Exceptions.NoSuchElementInDatabaseException;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Image;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.ImageDestination;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories.ImageRepository;

import java.util.List;


@Service
public class ImageServiceImpl implements ImageService{

    private final ImageRepository imageRepository;
    private final ImageHostingService imageHostingService;
    private final ImagesDestinationsProvider imagesDestinationsProvider;



    public ImageServiceImpl(ImageRepository imageRepository, ImageHostingService imageHostingService, ImagesDestinationsProvider imagesDestinationsProvider) {
        this.imageRepository = imageRepository;
        this.imageHostingService = imageHostingService;

        this.imagesDestinationsProvider = imagesDestinationsProvider;
    }




    @Override
    public Image add(MultipartFile multipartFile, ImageDestination imageDestination, String description) {

        String destination = null;
        if(imageDestination == ImageDestination.GALLERY){
            destination = this.imagesDestinationsProvider.getGalleryDestination();
        }
        if(imageDestination == ImageDestination.USERS_EXAMPLES){
            destination = this.imagesDestinationsProvider.getUsersExamplesDestination();
        }

        Image image = this.imageHostingService.upload(multipartFile, destination);
        image.setDescription(description);
        image.setImageDestination(imageDestination);
        return this.imageRepository.save(image);

    }

    @Override
    @Transactional
    public void delete(long id) {

        Image image = this.imageRepository.findById(id).orElseThrow(()->new RuntimeException("Nie znaleziono zdjęcia o podanym numerze id"));
        this.imageHostingService.delete(image.getOuterServiceId());
        this.imageRepository.deleteById(image.getId());

    }

    public void deleteAll() {


    }

    @Override
    public void update(Image image, MultipartFile multipartFile, String destination) {
        this.imageHostingService.delete(image.getOuterServiceId());
        Image newImage = this.imageHostingService.upload(multipartFile, destination);
        Image toUpdate = this.imageRepository.findById(image.getId()).orElseThrow(() -> new NoSuchElementInDatabaseException("Cannot find element with such id"));
        toUpdate.setUrl(newImage.getUrl());
        toUpdate.setOuterServiceId(newImage.getOuterServiceId());
        this.imageRepository.save(toUpdate);



    }

    @Override
    public Image getById(Long id) {
        return this.imageRepository.findById(id).orElseThrow(() -> new NoSuchElementInDatabaseException("Cannot find element with such id"));
    }

    @Override
    public List<Image> getAll() {
        return this.imageRepository.findAll();
    }

    @Override
    public List<Image> getAllByImageDestination(ImageDestination imageDestination) {
        return this.imageRepository.findAllByImageDestination(imageDestination.getValue());
    }
}
