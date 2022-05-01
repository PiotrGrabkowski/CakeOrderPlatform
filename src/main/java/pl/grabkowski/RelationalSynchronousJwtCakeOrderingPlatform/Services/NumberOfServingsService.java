package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.NumberOfServings;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories.NumberOfServingsRepository;

import java.util.List;

@Service
public class NumberOfServingsService implements CRUDInterface<NumberOfServings>{

    private final NumberOfServingsRepository numberOfServingsRepository;

    public NumberOfServingsService(NumberOfServingsRepository numberOfServingsRepository) {
        this.numberOfServingsRepository = numberOfServingsRepository;
    }

    @Override
    public void add(NumberOfServings numberOfServings) {
        numberOfServingsRepository.save(numberOfServings);
    }

    public List<NumberOfServings> getAll(){
        return numberOfServingsRepository.findAll();

    }
    public NumberOfServings getById (Long id){

        return numberOfServingsRepository
                .findById(id)
                .orElseThrow(()->new IllegalArgumentException("Nie znaleziono rekordu o podanym parametrze"));
    }

    @Override
    @Transactional
    public void updateById(Long id, String arg) {
        NumberOfServings numberOfServings = numberOfServingsRepository
                .findById(id)
                .orElseThrow(()->new IllegalArgumentException("Nie znaleziono rekordu o podanym parametrze"));

        numberOfServings.setValue(arg);
        numberOfServingsRepository.save(numberOfServings);




    }

    public void deleteAll(){
        numberOfServingsRepository.deleteAll();

    }
    public void deleteById(Long id){
        numberOfServingsRepository.deleteById(id);

    }
}
