package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO;

import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.OrderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderFilterOptions {
    private String nickname;
    private String username; // must be an email
    private String fromEventDate;
    private String upToEventDate;
    private String fromCreationDate;
    private String upToCreationDate;
    private String orderStatus;
    private String description;
    private long phoneNumber;
    private String typeOfProduct;

    public OrderFilterOptions() {
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFromEventDate() {
        return fromEventDate;
    }

    public void setFromEventDate(String fromEventDate) {
        this.fromEventDate = fromEventDate;
    }

    public String getUpToEventDate() {
        return upToEventDate;
    }

    public void setUpToEventDate(String upToEventDate) {
        this.upToEventDate = upToEventDate;
    }

    public String getFromCreationDate() {
        return fromCreationDate;
    }

    public void setFromCreationDate(String fromCreationDate) {
        this.fromCreationDate = fromCreationDate;
    }

    public String getUpToCreationDate() {
        return upToCreationDate;
    }

    public void setUpToCreationDate(String upToCreationDate) {
        this.upToCreationDate = upToCreationDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTypeOfProduct() {
        return typeOfProduct;
    }

    public void setTypeOfProduct(String typeOfProduct) {
        this.typeOfProduct = typeOfProduct;
    }
}
