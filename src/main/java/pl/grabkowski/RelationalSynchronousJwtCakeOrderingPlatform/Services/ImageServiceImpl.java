package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services;

import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Exceptions.NoSuchElementInDatabaseException;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Image;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories.ImageRepository;

import java.util.List;


@Service
public class ImageServiceImpl implements ImageService{

    private final ImageRepository imageRepository;
    private final ImageHostingService imageHostingService;

    public ImageServiceImpl(ImageRepository imageRepository, ImageHostingService imageHostingService) {
        this.imageRepository = imageRepository;
        this.imageHostingService = imageHostingService;
    }




    @Override
    public Image add(MultipartFile multipartFile, String destination) {

        Image image = this.imageHostingService.upload(multipartFile, destination);
        return this.imageRepository.save(image);

    }

    @Override
    public void delete(Image image) {
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


}
