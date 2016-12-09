package unit.info;

import models.sql.FishRegion;

public class FishRegionInfo implements IInfo<FishRegion> {
    @Override
    public String shortInfo(FishRegion object) {
        return object.getPlaceName();
    }

    @Override
    public String info(FishRegion object) {
        return "id: [" + object.getId() + "] [" + object.getPlaceName() + "]";
    }

    @Override
    public String longInfo(FishRegion object) {
        return "id: [" + object.getId() + "]\n" +
                "place name: [" + object.getPlaceName() + "]\n" +
                "description: [" + object.getDescription() + "]";
    }
}
