package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO;

public class TasteDto {
    private Long id;
    private String name;
    private boolean toPersist;
    private boolean toDelete;

    public TasteDto() {
    }

    public TasteDto(Long id, String name, boolean toPersist, boolean toDelete) {
        this.id = id;
        this.name = name;
        this.toPersist = toPersist;
        this.toDelete = toDelete;
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

    public boolean isToPersist() {
        return toPersist;
    }

    public void setToPersist(boolean toPersist) {
        this.toPersist = toPersist;
    }

    public boolean isToDelete() {
        return toDelete;
    }

    public void setToDelete(boolean toDelete) {
        this.toDelete = toDelete;
    }
}
