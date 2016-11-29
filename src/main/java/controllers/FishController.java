package controllers;

import models.sql.Fish;
import models.table.BaseTableModel;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.*;
import java.sql.SQLException;

public class FishController extends AbstractController<Fish> {

    public FishController(JdbcRowSet jrs, BaseTableModel tableModel, JTable view) {
        super(jrs, tableModel, view);
    }

    @Override
    protected void updateRecord(Fish newRecord) throws SQLException {
        if (newRecord.getId() != -1) {
            jrs.updateInt(1, newRecord.getId());
        }
        jrs.updateString(2, newRecord.getName());
        jrs.updateInt(3, newRecord.getPrice());
    }

    @Override
    protected Fish getRecord() throws SQLException {
        return new Fish(jrs.getInt(1), jrs.getString(2), jrs.getInt(3));
    }

    @Override
    protected int getSkipNumber() {
        return Fish.PK_NUMBER;
    }

    @Override
    protected void setCommandParameter(int numParameter, int numModelField, Fish searchFields) throws SQLException {
        switch (numModelField) {
            case 1:
                jrs.setInt(numParameter, searchFields.getId());
                break;
            case 2:
                jrs.setString(numParameter, searchFields.getName());
                break;
            case 3:
                jrs.setInt(numParameter, searchFields.getPrice());
                break;
        }
    }
}
