package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO;

import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Image;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Order;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.OrderStatus;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderResponse {
    private Long id;
    private UserDto user;
    private Long phoneNumber;
    private LocalDate eventDate;
    private String typeOfProduct;
    private String numberOfServings;
    private Set<String> setOfTastes;
    private String description;
    private Image image;
    private OrderStatus orderStatus;
    private LocalDateTime creationDate;

    public OrderResponse() {
    }
    public OrderResponse(Order order) {
        if(order != null) {
            this.id = order.getId();
            this.phoneNumber = order.getPhoneNumber();
            this.creationDate = order.getCreationDate();
            this.eventDate = order.getEventDate();
            this.typeOfProduct = order.getTypeOfProduct();
            this.numberOfServings = order.getNumberOfServings();
            this.setOfTastes = order.getOrderTasteSet().stream().map(taste -> taste.getName()).collect(Collectors.toSet());
            this.description = order.getDescription();
            this.image = order.getImage();
            this.orderStatus = order.getOrderStatus();
            this.user = new UserDto(order.getUser());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getTypeOfProduct() {
        return typeOfProduct;
    }

    public void setTypeOfProduct(String typeOfProduct) {
        this.typeOfProduct = typeOfProduct;
    }

    public String getNumberOfServings() {
        return numberOfServings;
    }

    public void setNumberOfServings(String numberOfServings) {
        this.numberOfServings = numberOfServings;
    }

    public Set<String> getSetOfTastes() {
        return setOfTastes;
    }

    public void setSetOfTastes(Set<String> setOfTastes) {
        this.setOfTastes = setOfTastes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
