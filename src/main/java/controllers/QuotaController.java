package controllers;

import models.gui.BaseTableModel;
import models.sql.Quota;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.*;
import java.sql.SQLException;

public class QuotaController extends AbstractController<Quota> {
    public QuotaController(JdbcRowSet jrs, BaseTableModel tableModel, JTable view) {
        super(jrs, tableModel, view);
    }

    @Override
    protected void updateRecord(Quota newRecord) throws SQLException {
        if (newRecord.getId() != -1) {
            jrs.updateInt(1, newRecord.getId());
        }
        jrs.updateInt(2, newRecord.getFishRegionId());
        // TODO: 02/12/2016 refactoring
        jrs.updateShort(3, (short) (newRecord.getYear().getYear() + 1900));
    }

    @Override
    protected Quota getRecord() throws SQLException {
        return new Quota(jrs.getInt(1), jrs.getInt(2), jrs.getDate(3));
    }

    @Override
    protected int getSkipNumber() {
        return Quota.PK_NUMBER;
    }

    @Override
    protected void setCommandParameter(int numParameter, int numModelField, Quota searchFields) throws SQLException {
        switch (numModelField) {
            case 1:
                jrs.setInt(numParameter, searchFields.getId());
                break;
            case 2:
                jrs.setInt(numParameter, searchFields.getFishRegionId());
                break;
            case 3:
                jrs.setShort(numParameter, (short) (searchFields.getYear().getYear() + 1900));
                break;
            default:
                throw new SQLException();
        }
    }
}
