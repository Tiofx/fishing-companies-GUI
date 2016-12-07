package unit.info;

import models.sql.ISqlModel;

public interface IInfo<T extends ISqlModel> {
    String shortInfo(T object);

    String info(T object);

    String longInfo(T object);
}
