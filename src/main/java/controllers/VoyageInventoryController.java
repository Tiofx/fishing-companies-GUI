package controllers;

import models.sql.VoyageInventory;
import unit.Connection;

import javax.sql.rowset.JdbcRowSet;
import java.sql.SQLException;

public class VoyageInventoryController extends AbstractController<VoyageInventory> {
    public VoyageInventoryController() {
    }

    public VoyageInventoryController(Connection connection, String tableName) {
        super(connection, tableName);
    }

    public VoyageInventoryController(JdbcRowSet jrs) {
        super(jrs);
    }

    @Override
    protected void updateRecord(VoyageInventory newRecord) throws SQLException {
        jrs.updateInt(1, newRecord.getVoyageId());
        jrs.updateInt(2, newRecord.getInventoryId());
    }

    @Override
    protected VoyageInventory getRecord() throws SQLException {
        return new VoyageInventory(jrs.getInt(1), jrs.getInt(2));
    }

    @Override
    protected int getSkipNumber() {
        return 0;
    }

    @Override
    protected void setCommandParameter(int numParameter, int numModelField, VoyageInventory searchFields) throws SQLException {
        switch (numModelField) {
            case 1:
                jrs.setInt(numParameter, searchFields.getVoyageId());
                break;
            case 2:
                jrs.setInt(numParameter, searchFields.getInventoryId());
                break;
            default:
                throw new SQLException();
        }
    }
}
