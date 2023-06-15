package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Image;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.ImageDestination;

import java.util.List;


@Repository
public interface ImageRepository extends JpaRepository<Image, Long>, ImageRepositoryCustom {
    String findByImageDestinationNativeQuery = "SELECT id, url, outer_service_id, description, image_destination FROM image WHERE image_destination = :imageDestination";
    @Query(value = findByImageDestinationNativeQuery, nativeQuery = true)
    List<Image> findAllByImageDestination(String imageDestination);
}
