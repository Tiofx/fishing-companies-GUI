package models.sql;

public class Captain implements ISqlModel {
    public final static int PK_NUMBER = 1;
    private int id;
    private String fio;
    private int experience;

    public Captain() {
        this(null, 0);
    }

    public Captain(String fio, int experience) {
        this(-1, fio, experience);
    }

    public Captain(int id, String fio, int experience) {
        this.id = id;
        this.fio = fio;
        this.experience = experience;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public String toString() {
        return fio;
    }
}
