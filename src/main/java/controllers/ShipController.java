package controllers;

import models.sql.Ship;
import models.table.BaseTableModel;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.*;
import java.sql.SQLException;

public class ShipController extends AbstractController<Ship> {
    public ShipController(JdbcRowSet jrs, BaseTableModel tableModel, JTable view) {
        super(jrs, tableModel, view);
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
    protected int getSkipNumber() {
        return Ship.PK_NUMBER;
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
