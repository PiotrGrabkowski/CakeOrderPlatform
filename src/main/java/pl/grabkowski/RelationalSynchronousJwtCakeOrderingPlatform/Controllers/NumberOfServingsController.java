package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Controllers;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.NumberOfServings;

import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services.NumberOfServingsService;

import java.util.List;

@RestController
@RequestMapping("/numberOfServings")
public class NumberOfServingsController {
    private final NumberOfServingsService numberOfServingsService;

    public NumberOfServingsController(NumberOfServingsService numberOfServingsService) {
        this.numberOfServingsService = numberOfServingsService;
    }

    @PostMapping("/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void add(@PathVariable String name) {
        NumberOfServings numberOfServings = new NumberOfServings();
        numberOfServings.setValue(name);
        numberOfServingsService.add(numberOfServings);

    }

    @GetMapping("/all")
    public List<NumberOfServings> getAll(){
        return numberOfServingsService.getAll();

    }
    @GetMapping("/{id}")
    public NumberOfServings getById (@PathVariable Long id) {

        return numberOfServingsService.getById(id);

    }

    @PatchMapping("/{id}/{arg}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateById(@PathVariable(name ="id") Long id, @PathVariable(name = "arg") String arg) {


        numberOfServingsService.updateById(id, arg);



    }

    @DeleteMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteAll(){
        numberOfServingsService.deleteAll();

    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteById(Long id){
        numberOfServingsService.deleteById(id);

    }

}
