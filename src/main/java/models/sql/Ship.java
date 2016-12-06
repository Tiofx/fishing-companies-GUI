package models.sql;

public class Ship implements ISqlModel {
    public final static int PK_NUMBER = 1;
    private int id;
    private String name;


    public Ship() {
        this(null);
    }

    public Ship(String name) {
        this(-1, name);
    }

    public Ship(int id, String name) {
        this.id = id;
        this.name = name;
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
}
