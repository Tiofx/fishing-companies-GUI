package models.sql;

public class Fish implements ISqlModel {
    public final static int PK_NUMBER = 1;
    private int id;
    private String name;
    private int price;

    public Fish() {
        this(-1, null, 0);
    }

    public Fish(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Fish(String name, int price) {
        this(-1, name, price);
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name;
    }
}
