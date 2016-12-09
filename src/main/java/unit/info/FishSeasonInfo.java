package unit.info;

import models.sql.FishSeason;

public class FishSeasonInfo implements IInfo<FishSeason> {
    @Override
    public String shortInfo(FishSeason object) {
        return object.getBegin().toString();
    }

    @Override
    public String info(FishSeason object) {
        return "id: [" + object.getId() + "] [" + object.getFishRegionId() + "]";
    }

    @Override
    public String longInfo(FishSeason object) {
        return "id: [" + object.getId() + "]\n" +
                "fishRegionId: [" + object.getFishRegionId() + "]\n" +
                "begin: [" + object.getBegin().toString() + "]\n" +
                "end: [" + object.getEnd().toString() + "]";

    }
}
