package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model;

import javax.persistence.*;

@Entity
public class OrderTaste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderTaste() {
    }

    public OrderTaste(String name) {
        this.name = name;
    }

    public OrderTaste(Long id, String name, Order order) {
        this.id = id;
        this.name = name;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
