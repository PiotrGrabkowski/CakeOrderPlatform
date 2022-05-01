package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class NumberOfServings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;


    public NumberOfServings() {
    }

    public NumberOfServings(String value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
