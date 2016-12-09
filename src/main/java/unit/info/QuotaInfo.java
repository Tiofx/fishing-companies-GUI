package unit.info;

import models.sql.Quota;

public class QuotaInfo implements IInfo<Quota> {
    @Override
    public String shortInfo(Quota object) {
        return String.valueOf(object.getYear());
    }

    @Override
    public String info(Quota object) {
        return "id: [" + object.getId() + "] [" + object.getYear() + "]";
    }

    @Override
    public String longInfo(Quota object) {
        return "id: [" + object.getId() + "]\n" +
                "fishRegionId: [" + object.getFishRegionId() + "]\n" +
                "year: [" + object.getYear() + "]";
    }
}
