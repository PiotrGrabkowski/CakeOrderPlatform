package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.TasteDto;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services.TasteService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tastes")
@CrossOrigin
public class TasteController {
    private final TasteService tasteService;

    public TasteController(TasteService tasteService) {
        this.tasteService = tasteService;
    }

    @GetMapping
    public ResponseEntity<List<TasteDto>> getAll(){
       return ResponseEntity.ok(this.tasteService.getAll());

    }

    @PostMapping
    public ResponseEntity<String> updateTastes(@RequestBody List<TasteDto> tasteDtoList){
        this.tasteService.updateTastes(tasteDtoList);
        return ResponseEntity.ok("Poprawnie zaktualizowano listę smaków.");

    }
}
