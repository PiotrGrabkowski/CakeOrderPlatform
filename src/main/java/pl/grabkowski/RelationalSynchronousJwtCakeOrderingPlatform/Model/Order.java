package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model;



import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.HashSet;
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

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    private Long phoneNumber;
    private String typeOfProduct;
    private String numberOfServings;
    @ElementCollection
    private Set<String> setOfTastes = new HashSet<>();
    private String description;
    private String exampleLink;
    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;

    private OrderStatus orderStatus;





    public Order() {
    }

    public Order(Long id, User user, Long phoneNumber, String typeOfProduct, String numberOfServings, Set<String> setOfTastes, String description, String exampleLink, Image image, OrderStatus orderStatus) {
        this.id = id;
        this.user = user;
        this.phoneNumber = phoneNumber;
        this.typeOfProduct = typeOfProduct;
        this.numberOfServings = numberOfServings;
        this.setOfTastes = setOfTastes;
        this.description = description;
        this.exampleLink = exampleLink;
        this.image = image;
        this.orderStatus = orderStatus;
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

    public String getExampleLink() {
        return exampleLink;
    }

    public void setExampleLink(String exampleLink) {
        this.exampleLink = exampleLink;
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
}
