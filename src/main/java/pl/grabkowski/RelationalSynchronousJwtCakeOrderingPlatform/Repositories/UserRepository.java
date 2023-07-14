package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    Optional<User> findByUsername (String username);
    Optional<User> findByRole (String role);
}
