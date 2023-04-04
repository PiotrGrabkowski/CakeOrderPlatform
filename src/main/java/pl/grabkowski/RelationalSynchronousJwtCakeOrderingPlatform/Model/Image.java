package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model;



import javax.persistence.*;


@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String outerServiceId;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private ImageDestination imageDestination;

    public Image(Long id, String url, String outerServiceId, String description,ImageDestination imageDestination) {
        this.id = id;
        this.url = url;
        this.outerServiceId = outerServiceId;
        this.description = description;
        this.imageDestination = imageDestination;
    }

    public Image() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageDestination getImageDestination() {
        return imageDestination;
    }

    public void setImageDestination(ImageDestination imageDestination) {
        this.imageDestination = imageDestination;
    }
}
