package models.sql;

public class FishCatch implements ISqlModel {
    public final static int PK_NUMBER = 2;
    private int id;
    private int voyageId;
    private int fishId;
    private int weight;

    public FishCatch() {
        this(0, 0, 0);
    }

    public FishCatch(int fishId, int weight) {
        this(-1, fishId, weight);
    }

    public FishCatch(int voyageId, int fishId, int weight) {
        this(-1, voyageId, fishId, weight);
    }

    public FishCatch(int id, int voyageId, int fishId, int weight) {
        this.id = id;
        this.voyageId = voyageId;
        this.fishId = fishId;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVoyageId() {
        return voyageId;
    }

    public void setVoyageId(int voyageId) {
        this.voyageId = voyageId;
    }

    public int getFishId() {
        return fishId;
    }

    public void setFishId(int fishId) {
        this.fishId = fishId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
