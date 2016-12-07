package controllers;

import models.sql.FishSeason;
import unit.Connection;

import javax.sql.rowset.JdbcRowSet;
import java.sql.SQLException;

public class FishSeasonController extends AbstractControllerWithRef<FishSeason> {

    public FishSeasonController() {
    }

    public FishSeasonController(Connection connection, String tableName) {
        this(connection, tableName, "fishRegion");
    }

    public FishSeasonController(Connection connection, String tableName, String refTableName) {
        super(connection, tableName, refTableName);
    }

    public FishSeasonController(JdbcRowSet jrs) {
        super(jrs);
    }

    @Override
    protected void updateRecord(FishSeason newRecord) throws SQLException {
        if (newRecord.getId() != -1) {
            jrs.updateInt(1, newRecord.getId());
        }
        jrs.updateInt(2, newRecord.getFishRegionId());
        jrs.updateDate(3, newRecord.getBegin());
        jrs.updateDate(4, newRecord.getEnd());
    }

    @Override
    protected FishSeason getRecord() throws SQLException {
        return new FishSeason(jrs.getInt(1), jrs.getInt(2), jrs.getDate(3), jrs.getDate(4));
    }

    @Override
    protected void setCommandParameter(int numParameter, int numModelField, FishSeason searchFields) throws SQLException {
        switch (numModelField) {
            case 1:
                jrs.setInt(numParameter, searchFields.getId());
                break;
            case 2:
                jrs.setInt(numParameter, searchFields.getFishRegionId());
                break;
            case 3:
                jrs.setDate(numParameter, searchFields.getBegin());
                break;
            case 4:
                jrs.setDate(numParameter, searchFields.getEnd());
                break;
            default:
                throw new SQLException();
        }
    }
}
