package unit.info;

import models.sql.Captain;

public class CaptainInfo implements IInfo<Captain> {
    @Override
    public String shortInfo(Captain object) {
        return object.getFio();
    }

    @Override
    public String info(Captain object) {
        return "id: [" + object.getId() + "] [" + object.getFio() + "]";
    }

    @Override
    public String longInfo(Captain object) {
        return "id: [" + object.getId() + "]\n" +
                "fio: [" + object.getFio() + "]\n" +
                "experience: [" + object.getExperience() + "]";
    }
}
