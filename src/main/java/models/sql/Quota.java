package models.sql;

import java.sql.Date;

public class Quota implements ISqlModel {
    public final static int PK_NUMBER = 1;
    private int id;
    private int fishRegionId;
    private java.sql.Date year;

    public Quota() {
        this(0, null);
    }

    public Quota(int fishRegionId, Date year) {
        this(-1, fishRegionId, year);
    }

    public Quota(int id, int fishRegionId, Date year) {
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

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }
}
