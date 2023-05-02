package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services;

import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.SmsSettingsDto;

public interface SmsSettings {
    void setSettings(SmsSettingsDto smsSettingsDto);
    SmsSettingsDto getSettings ();
}
