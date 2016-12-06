package controllers;

import models.sql.FishCatch;

import javax.sql.rowset.JdbcRowSet;
import java.sql.SQLException;

public class FishCatchController extends AbstractController<FishCatch> {

    public FishCatchController() {
    }

    public FishCatchController(unit.Connection connection, String tableName) {
        super(connection, tableName);
    }

    public FishCatchController(JdbcRowSet jrs) {
        super(jrs);
    }

    @Override
    protected void updateRecord(FishCatch newRecord) throws SQLException {
        if (newRecord.getId() != -1) {
            jrs.updateInt(1, newRecord.getId());
        }
        jrs.updateInt(2, newRecord.getVoyageId());
        jrs.updateInt(3, newRecord.getFishId());
        jrs.updateInt(4, newRecord.getWeight());
    }

    @Override
    protected FishCatch getRecord() throws SQLException {
        return new FishCatch(jrs.getInt(1), jrs.getInt(2), jrs.getInt(3), jrs.getInt(4));
    }

    @Override
    protected void setCommandParameter(int numParameter, int numModelField, FishCatch searchFields) throws SQLException {
        switch (numModelField) {
            case 1:
                jrs.setInt(numParameter, searchFields.getId());
                break;
            case 2:
                jrs.setInt(numParameter, searchFields.getVoyageId());
                break;
            case 3:
                jrs.setInt(numParameter, searchFields.getFishId());
                break;
            case 4:
                jrs.setInt(numParameter, searchFields.getWeight());
                break;
            default:
                throw new SQLException();
        }

    }
}
