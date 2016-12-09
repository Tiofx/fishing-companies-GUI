package models.sql;

public class Inventory implements ISqlModel {
    public final static int PK_NUMBER = 1;
    private int id;
    private String name;
    private int lifeTime;
    private int manufactureDate;

    public Inventory() {
        this(null, 0, 0);
    }

    public Inventory(String name, int lifeTime, int manufactureDate) {
        this(-1, name, lifeTime, manufactureDate);
    }

    public Inventory(int id, String name, int lifeTime, int manufactureDate) {
        this.id = id;
        this.name = name;
        this.lifeTime = lifeTime;
        this.manufactureDate = manufactureDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    public int getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(int manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    @Override
    public String toString() {
        return name;
    }
}
