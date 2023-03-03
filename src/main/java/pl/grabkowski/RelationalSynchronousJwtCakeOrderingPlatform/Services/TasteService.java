package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services;

import org.springframework.stereotype.Service;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.TasteDto;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Taste;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories.TasteRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TasteService {
    private final TasteRepository tasteRepository;

    public TasteService(TasteRepository tasteRepository) {
        this.tasteRepository = tasteRepository;
    }
    public List<TasteDto> getAll(){

        return tasteRepository.findAll().stream().map(taste -> {
            TasteDto tasteDto = new TasteDto();
            tasteDto.setId(taste.getId());
            tasteDto.setName(taste.getName());
            return  tasteDto;
        }).collect(Collectors.toList());
    }
    public void updateTastes (List<TasteDto> tasteDtoList){
        tasteDtoList.stream().forEach(
                tasteDto -> {
                    if(tasteDto.isToPersist()){
                        Taste taste = new Taste();
                        taste.setName(tasteDto.getName());
                        tasteRepository.save(taste);
                    }
                    if(tasteDto.isToDelete()){

                        tasteRepository.deleteById(tasteDto.getId());
                    }

                }

        );

    }
}
