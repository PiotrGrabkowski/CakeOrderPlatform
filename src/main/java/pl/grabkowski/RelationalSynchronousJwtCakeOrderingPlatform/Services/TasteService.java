package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services;

import org.springframework.stereotype.Service;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Taste;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories.TasteRepository;

import java.util.List;

@Service
public class TasteService {
    private final TasteRepository tasteRepository;

    public TasteService(TasteRepository tasteRepository) {
        this.tasteRepository = tasteRepository;
    }
    public List<Taste> getAll(){

        return tasteRepository.findAll();
    }
}
