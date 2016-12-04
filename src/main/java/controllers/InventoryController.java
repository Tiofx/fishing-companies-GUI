package controllers;

import models.sql.Inventory;
import models.gui.BaseTableModel;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.*;
import java.sql.SQLException;

public class InventoryController extends AbstractController<Inventory> {

    public InventoryController() {
    }

    public InventoryController(JdbcRowSet jrs, BaseTableModel tableModel, JTable view) {
        super(jrs, tableModel, view);
    }

    @Override
    protected void updateRecord(Inventory newRecord) throws SQLException {
        if (newRecord.getId() != -1) {
            jrs.updateInt(1, newRecord.getId());
        }
        jrs.updateString(2, newRecord.getName());
        jrs.updateInt(3, newRecord.getLifeTime());
        jrs.updateInt(4, newRecord.getManufactureDate());
    }

    @Override
    protected Inventory getRecord() throws SQLException {
        return new Inventory(jrs.getInt(1), jrs.getString(2), jrs.getInt(3), jrs.getInt(4));
    }

    @Override
    protected void setCommandParameter(int numParameter, int numModelField, Inventory searchFields) throws SQLException {
        switch (numModelField) {
            case 1:
                jrs.setInt(numParameter, searchFields.getId());
                break;
            case 2:
                jrs.setString(numParameter, searchFields.getName());
                break;
            case 3:
                jrs.setInt(numParameter, searchFields.getLifeTime());
                break;
            case 4:
                jrs.setInt(numParameter, searchFields.getManufactureDate());
                break;
            default:
                throw new SQLException();
        }
    }
}
