package unit;

import models.sql.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import unit.info.*;

import java.util.HashMap;
import java.util.Map;

public class UniversalInfo implements IInfo<ISqlModel> {
    protected final Map<Class, IInfo> functionSet = new HashMap<>();

    public UniversalInfo() {
        functionSet.put(FishRegion.class, new FishRegionInfo());
        functionSet.put(Fish.class, new FishInfo());
        functionSet.put(Captain.class, new CaptainInfo());
        functionSet.put(Ship.class, new ShipInfo());
        functionSet.put(FishSeason.class, new FishSeasonInfo());
        functionSet.put(Quota.class, new QuotaInfo());
    }

    @Override
    public String shortInfo(ISqlModel object) {
        if (functionSet.containsKey(object.getClass())) {
            return functionSet.get(object.getClass()).shortInfo(object);
        } else {
            throw new NotImplementedException();
        }
    }

    @Override
    public String info(ISqlModel object) {
        if (functionSet.containsKey(object.getClass())) {
            return functionSet.get(object.getClass()).info(object);
        } else {
            throw new NotImplementedException();
        }
    }

    @Override
    public String longInfo(ISqlModel object) {
        if (functionSet.containsKey(object.getClass())) {
            return functionSet.get(object.getClass()).longInfo(object);
        } else {
            throw new NotImplementedException();
        }
    }
}
