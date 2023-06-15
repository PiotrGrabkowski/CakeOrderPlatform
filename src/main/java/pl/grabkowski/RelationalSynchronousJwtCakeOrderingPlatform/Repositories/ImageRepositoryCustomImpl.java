package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories;

import org.springframework.context.MessageSource;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.Page;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Exceptions.BadRequestException;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Image;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.List;

public class ImageRepositoryCustomImpl implements ImageRepositoryCustom {

    private final EntityManager entityManager;



    private String findAllImagesNativeQuery = "SELECT id, url, outer_service_id, description, image_destination FROM image";
    private String findAllImagesJPQLQuery = "SELECT i FROM Image i";


    public ImageRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;

    }


    @Override
    public Page<Image> findAllByImageDestination(String imageDestination, Page<Image> page) {
        String countStatement = "SELECT COUNT(*) FROM image WHERE image_destination = '" + imageDestination + "'";
        long numberOfItems = ((BigInteger)this.entityManager.createNativeQuery(countStatement).getSingleResult()).longValue();
        if(page.getCurrentPage()>numberOfItems){
            throw new BadRequestException("error.bad_request.page_too_large");
        }
        if(page.getCurrentPage()<1){
            throw new BadRequestException("error.bad_request.page_lower_than_one");
        }
        Page<Image> returnedPage = new Page<>(page.getCurrentPage(),page.getItemsPerPage(),numberOfItems);


        String whereClause = " WHERE image_destination = '" + imageDestination + "' LIMIT " + returnedPage.getItemsPerPage() + " OFFSET " + returnedPage.getOffset();
        List<Image> list = this.entityManager.createNativeQuery(this.findAllImagesNativeQuery+ whereClause, Image.class).getResultList();
        returnedPage.setListOfItems(list);

        return returnedPage;
    }
}
