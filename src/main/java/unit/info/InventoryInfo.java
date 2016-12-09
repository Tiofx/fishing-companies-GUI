package unit.info;

import models.sql.Inventory;

public class InventoryInfo implements IInfo<Inventory> {
    @Override
    public String shortInfo(Inventory object) {
        return object.getName();
    }

    @Override
    public String info(Inventory object) {
        return "id: [" + object.getId() + "] [" + object.getName() + "]";
    }

    @Override
    public String longInfo(Inventory object) {
        return "id: [" + object.getId() + "]\n" +
                "name: [" + object.getName() + "]\n" +
                "lifeTime: [" + object.getLifeTime() + "]\n" +
                "manufactureDate: [" + object.getLifeTime() + "]";
    }
}
