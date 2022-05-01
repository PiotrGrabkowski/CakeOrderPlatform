package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.NumberOfServings;

@Repository
public interface NumberOfServingsRepository extends JpaRepository<NumberOfServings, Long> {
}
