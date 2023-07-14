package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories;

import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.Page;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.UserDto;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.User;

public interface UserRepositoryCustom {
    Page<User> findAll(UserDto userSearchParameters, Page<UserDto>requestedPageParameters);
}
