package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories;

import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.Page;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Image;

public interface ImageRepositoryCustom {
    Page<Image> findAllByImageDestination(String imageDestination, Page<Image> page);
}
