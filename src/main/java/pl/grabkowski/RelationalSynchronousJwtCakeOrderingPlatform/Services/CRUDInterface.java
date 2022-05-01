package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services;

import java.util.List;

public interface CRUDInterface<T> {

    void add (T t);
    List<T> getAll();
    T getById(Long id);
    void updateById(Long id, String  arg);
    void deleteAll();
    void deleteById(Long id);



}
