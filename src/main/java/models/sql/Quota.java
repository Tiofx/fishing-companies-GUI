package models.sql;

public class Quota implements ISqlModel {
    public final static int PK_NUMBER = 1;
    private int id;
    private int fishRegionId;
    private short year;

    public Quota() {
        this(0, (short) 0);
    }

    public Quota(int fishRegionId, short year) {
        this(-1, fishRegionId, year);
    }

    public Quota(int id, int fishRegionId, short year) {
        this.id = id;
        this.fishRegionId = fishRegionId;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFishRegionId() {
        return fishRegionId;
    }

    public void setFishRegionId(int fishRegionId) {
        this.fishRegionId = fishRegionId;
    }

    public short getYear() {
        return year;
    }

    public void setYear(short year) {
        this.year = year;
    }
}
