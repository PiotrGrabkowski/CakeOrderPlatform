package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO;

public class SmsSettingsDto {
    private boolean notificationEnabled;
    private String number;

    public SmsSettingsDto() {
    }

    public boolean getNotificationEnabled() {
        return notificationEnabled;
    }

    public void setNotificationEnabled(boolean notificationEnabled) {
        this.notificationEnabled = notificationEnabled;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
