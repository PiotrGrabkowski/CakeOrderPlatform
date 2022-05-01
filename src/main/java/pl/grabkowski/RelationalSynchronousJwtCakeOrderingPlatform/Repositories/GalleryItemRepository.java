package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.GalleryItem;

import java.util.List;

@Repository
public interface GalleryItemRepository extends JpaRepository<GalleryItem, Long> {

    @Override
    @Query("SELECT g, i, it FROM GalleryItem g LEFT JOIN FETCH g.image i LEFT JOIN FETCH g.imageThumb it")
    List<GalleryItem> findAll();
}
