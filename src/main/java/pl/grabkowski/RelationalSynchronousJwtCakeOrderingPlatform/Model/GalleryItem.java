package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model;

import javax.persistence.*;

@Entity
public class GalleryItem {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn (name ="image_thumb_id")
    private Image imageThumb;
    private String description;

    public GalleryItem() {
    }

    public GalleryItem(Long id, Image image, Image imageThumb, String description) {
        this.id = id;
        this.image = image;
        this.imageThumb = imageThumb;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImageThumb() {
        return imageThumb;
    }

    public void setImageThumb(Image imageThumb) {
        this.imageThumb = imageThumb;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
