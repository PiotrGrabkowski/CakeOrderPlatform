package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model;



import javax.persistence.*;


@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String outerServiceId;

    public Image() {
    }

    public Image(Long id, String url, String outerServiceId) {
        this.id = id;
        this.url = url;
        this.outerServiceId = outerServiceId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOuterServiceId() {
        return outerServiceId;
    }

    public void setOuterServiceId(String outerServiceId) {
        this.outerServiceId = outerServiceId;
    }
}
