package models.sql;

import java.sql.Date;

public class FishSeason implements ISqlModel {
    public final static int PK_NUMBER = 1;
    private int id;
    private int fishRegionId;
    private java.sql.Date begin;
    private java.sql.Date end;

    public FishSeason() {
        this(0, null, null);
    }

    public FishSeason(int fishRegionId, Date begin, Date end) {
        this(-1, fishRegionId, begin, end);
    }

    public FishSeason(int id, int fishRegionId, Date begin, Date end) {
        this.id = id;
        this.fishRegionId = fishRegionId;
        this.begin = begin;
        this.end = end;
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

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return String.valueOf(fishRegionId);
    }
}
