package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Taste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;


    public Taste() {
    }

    public Taste(String name) {
        this.name = name;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
