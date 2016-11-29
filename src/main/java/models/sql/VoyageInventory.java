package models.sql;

public class VoyageInventory implements ISqlModel {
    public final static int PK_NUMBER = 2;
    private int voyageId;
    private int inventoryId;

    public VoyageInventory() {
        this(0, 0);
    }

    public VoyageInventory(int voyageId, int inventoryId) {
        this.voyageId = voyageId;
        this.inventoryId = inventoryId;
    }

    public int getVoyageId() {
        return voyageId;
    }

    public void setVoyageId(int voyageId) {
        this.voyageId = voyageId;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }
}
