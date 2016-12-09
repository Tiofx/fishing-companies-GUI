package unit.info;

import models.sql.Fish;

public class FishInfo implements IInfo<Fish> {
    @Override
    public String shortInfo(Fish object) {
        return object.getName();
    }

    @Override
    public String info(Fish object) {
        return "id [" + object.getId() + "] [" + object.getName() + "]";
    }

    @Override
    public String longInfo(Fish object) {
        return "id [" + object.getId() + "]\n" +
                "name: [" + object.getName() + "]\n" +
                "price: [" + object.getPrice() + "]";
    }
}
