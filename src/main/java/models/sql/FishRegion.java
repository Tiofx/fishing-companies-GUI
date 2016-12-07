package models.sql;

public class FishRegion implements ISqlModel {
    public final static int PK_NUMBER = 1;
    private int id;
    private String placeName;
    private String description;

    public FishRegion() {
        this(null, null);
    }

    public FishRegion(String placeName, String description) {
        this(-1, placeName, description);
    }

    public FishRegion(int id, String placeName, String description) {
        this.id = id;
        this.placeName = placeName;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    @Override
//    public String toString() {
//        return placeName;
//    }
}
