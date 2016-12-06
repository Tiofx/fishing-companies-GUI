package controllers;

import models.sql.Ship;

import javax.sql.rowset.JdbcRowSet;
import java.sql.SQLException;

public class ShipController extends AbstractController<Ship> {

    public ShipController() {
    }

    public ShipController(unit.Connection connection, String tableName) {
        super(connection, tableName);
    }

    public ShipController(JdbcRowSet jrs) {
        super(jrs);
    }

    @Override
    protected void updateRecord(Ship newRecord) throws SQLException {
        if (newRecord.getId() != -1) {
            jrs.updateInt(1, newRecord.getId());
        }
        jrs.updateString(2, newRecord.getName());
    }

    @Override
    protected Ship getRecord() throws SQLException {
        return new Ship(jrs.getInt(1), jrs.getString(2));
    }

    @Override
    protected void setCommandParameter(int numParameter, int numModelField, Ship searchFields) throws SQLException {
        switch (numModelField) {
            case 1:
                jrs.setInt(numParameter, searchFields.getId());
                break;
            case 2:
                jrs.setString(numParameter, searchFields.getName());
                break;
            default:
                throw new SQLException();
        }
    }
}
