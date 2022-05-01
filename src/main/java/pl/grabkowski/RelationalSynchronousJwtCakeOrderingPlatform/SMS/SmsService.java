package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.SMS;

public interface SmsService {

    void send();
    void send (String msg);
    void send (String from, String to, String msg);

}
