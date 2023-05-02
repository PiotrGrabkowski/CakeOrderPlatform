package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.SmsSettingsDto;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services.SmsSettings;

@RestController
@RequestMapping("/settings")
@CrossOrigin
public class SmsSettingsController {

    private final SmsSettings smsSettings;

    public SmsSettingsController(SmsSettings smsSettings) {
        this.smsSettings = smsSettings;
    }


    @GetMapping()
    public ResponseEntity<SmsSettingsDto> getSettings(){
        return ResponseEntity.ok(this.smsSettings.getSettings());
    }
    @PostMapping()
    public ResponseEntity<String> setSettings(@RequestBody SmsSettingsDto smsSettingsDto){
        this.smsSettings.setSettings(smsSettingsDto);
        return ResponseEntity.ok("Poprawnie zaktualizowano ustawienia");

    }
}
