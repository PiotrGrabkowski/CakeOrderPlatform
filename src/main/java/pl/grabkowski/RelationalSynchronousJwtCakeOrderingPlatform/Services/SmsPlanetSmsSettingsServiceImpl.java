package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Services;

import org.springframework.stereotype.Service;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.SmsSettingsDto;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.SMS.SmsPlanetConfigurationProperties;

@Service
public class SmsPlanetSmsSettingsServiceImpl implements SmsSettings{
    private final SmsPlanetConfigurationProperties smsPlanetConfigurationProperties;

    public SmsPlanetSmsSettingsServiceImpl(SmsPlanetConfigurationProperties smsPlanetConfigurationProperties) {
        this.smsPlanetConfigurationProperties = smsPlanetConfigurationProperties;
    }


    public void setSettings(SmsSettingsDto smsSettingsDto){
        this.smsPlanetConfigurationProperties.setTo(smsSettingsDto.getNumber());
        this.smsPlanetConfigurationProperties.setIsNotificationEnabled(smsSettingsDto.getNotificationEnabled());




    }
    public SmsSettingsDto getSettings (){
        SmsSettingsDto smsSettingsDto = new SmsSettingsDto();
        smsSettingsDto.setNumber(this.smsPlanetConfigurationProperties.getTo());
        smsSettingsDto.setNotificationEnabled(this.smsPlanetConfigurationProperties.getIsNotificationEnabled());
        return smsSettingsDto;

    }
}
