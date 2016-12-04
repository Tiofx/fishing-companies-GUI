package controllers;

import models.gui.BaseTableModel;
import models.sql.Captain;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.*;
import java.sql.SQLException;

public class CaptainController extends AbstractController<Captain> {

    public CaptainController() {
    }

    public CaptainController(JdbcRowSet jrs, BaseTableModel tableModel, JTable view) {
        super(jrs, tableModel, view);
    }

    @Override
    protected void updateRecord(Captain newRecord) throws SQLException {
        if (newRecord.getId() != -1) {
            jrs.updateInt(1, newRecord.getId());
        }
        jrs.updateString(2, newRecord.getFio());
        jrs.updateInt(3, newRecord.getExperience());
    }

    @Override
    protected Captain getRecord() throws SQLException {
        return new Captain(jrs.getInt(1), jrs.getString(2), jrs.getInt(3));
    }

    @Override
    protected void setCommandParameter(int numParameter, int numModelField, Captain searchFields) throws SQLException {
        switch (numModelField) {
            case 1:
                jrs.setInt(numParameter, searchFields.getId());
                break;
            case 2:
                jrs.setString(numParameter, searchFields.getFio());
                break;
            case 3:
                jrs.setInt(numParameter, searchFields.getExperience());
                break;
            default:
                throw new SQLException();
        }
    }
}
