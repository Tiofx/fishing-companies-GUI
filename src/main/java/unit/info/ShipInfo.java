package unit.info;

import models.sql.Ship;

public class ShipInfo implements IInfo<Ship> {
    @Override
    public String shortInfo(Ship object) {
        return object.getName();
    }

    @Override
    public String info(Ship object) {
        return "id: [" + object.getId() + "] [" + object.getName() + "]";
    }

    @Override
    public String longInfo(Ship object) {
        return "id: [" + object.getId() + "]\n" +
                "name: [" + object.getName() + "]";
    }
}
