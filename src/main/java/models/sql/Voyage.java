package models.sql;

import java.sql.Timestamp;

public class Voyage implements ISqlModel {
    public final static int PK_NUMBER = 1;
    private int id;
    private int captainId;
    private int shipId;
    private int fishSeasonId;
    private int quotaId;
    private java.sql.Timestamp departureDate;
    private java.sql.Timestamp returnDate;

    public Voyage() {
        this(0, 0, 0, 0, null, null);
    }

    public Voyage(int captainId, int shipId, int fishSeasonId, int quotaId,
                  Timestamp departureDate, Timestamp returnDate) {
        this(-1, captainId, shipId, fishSeasonId, quotaId, departureDate, returnDate);
    }

    public Voyage(int id, int captainId, int shipId, int fishSeasonId, int quotaId,
                  Timestamp departureDate, Timestamp returnDate) {
        this.id = id;
        this.captainId = captainId;
        this.shipId = shipId;
        this.fishSeasonId = fishSeasonId;
        this.quotaId = quotaId;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCaptainId() {
        return captainId;
    }

    public void setCaptainId(int captainId) {
        this.captainId = captainId;
    }

    public int getShipId() {
        return shipId;
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
    }

    public int getFishSeasonId() {
        return fishSeasonId;
    }

    public void setFishSeasonId(int fishSeasonId) {
        this.fishSeasonId = fishSeasonId;
    }

    public int getQuotaId() {
        return quotaId;
    }

    public void setQuotaId(int quotaId) {
        this.quotaId = quotaId;
    }

    public Timestamp getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Timestamp departureDate) {
        this.departureDate = departureDate;
    }

    public Timestamp getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }
}
