package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model;



import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Table(name = "orders")
public class Order{



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private Long phoneNumber;
    private LocalDate eventDate;
    private LocalDateTime creationDate;
    private String typeOfProduct;
    private String numberOfServings;
    @OneToMany (mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderTaste> orderTasteSet = new HashSet<>();
    private String description;
    @OneToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;





    public Order() {
    }

    public Order(Long id,
                 User user,
                 Long phoneNumber,
                 LocalDate eventDate,
                 String typeOfProduct,
                 String numberOfServings,
                 Set<OrderTaste> orderTasteSet,
                 String description,
                 Image image,
                 OrderStatus orderStatus,
                 LocalDateTime creationDate) {
        this.id = id;
        this.user = user;
        this.phoneNumber = phoneNumber;
        this.eventDate = eventDate;
        this.typeOfProduct = typeOfProduct;
        this.numberOfServings = numberOfServings;
        this.orderTasteSet = orderTasteSet;
        this.description = description;
        this.image = image;
        this.orderStatus = orderStatus;
        this.creationDate = creationDate;
    }

    public void addTaste(OrderTaste orderTaste){
        this.orderTasteSet.add(orderTaste);
        orderTaste.setOrder(this);

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public Set<OrderTaste> getOrderTasteSet() {
        return orderTasteSet;
    }

    public void setOrderTasteSet(Set<OrderTaste> orderTasteSet) {
        this.orderTasteSet = orderTasteSet;
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

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", phoneNumber=" + phoneNumber +
                ", eventDate=" + eventDate +
                ", creationDate=" + creationDate +
                ", typeOfProduct='" + typeOfProduct + '\'' +
                ", numberOfServings='" + numberOfServings + '\'' +
                ", orderTasteSet=" + orderTasteSet +
                ", description='" + description + '\'' +
                ", image=" + image +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
