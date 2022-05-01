package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services;

import org.springframework.stereotype.Service;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.TypeOfProduct;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories.TypeOfProductRepository;

import java.util.List;

@Service
public class TypeOfProductService {
    private final TypeOfProductRepository typeOfProductRepository;

    public TypeOfProductService(TypeOfProductRepository typeOfProductRepository) {
        this.typeOfProductRepository = typeOfProductRepository;
    }

    public List<TypeOfProduct> getAll(){

        return typeOfProductRepository.findAll();
    }
}
