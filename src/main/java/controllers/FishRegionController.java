package controllers;

import models.sql.FishRegion;
import models.gui.BaseTableModel;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.*;
import java.sql.SQLException;

public class FishRegionController extends AbstractController<FishRegion> {
    public FishRegionController(JdbcRowSet jrs, BaseTableModel tableModel, JTable view) {
        super(jrs, tableModel, view);
    }

    @Override
    protected void updateRecord(FishRegion newRecord) throws SQLException {
        if (newRecord.getId() != -1) {
            jrs.updateInt(1, newRecord.getId());
        }
        jrs.updateString(2, newRecord.getPlaceName());
        jrs.updateString(3, newRecord.getDescription());
    }

    @Override
    protected FishRegion getRecord() throws SQLException {
        return new FishRegion(jrs.getInt(1), jrs.getString(2), jrs.getString(3));
    }

    @Override
    protected int getSkipNumber() {
        return FishRegion.PK_NUMBER;
    }

    @Override
    protected void setCommandParameter(int numParameter, int numModelField, FishRegion searchFields) throws SQLException {
        switch (numModelField) {
            case 1:
                jrs.setInt(numParameter, searchFields.getId());
                break;
            case 2:
                jrs.setString(numParameter, searchFields.getPlaceName());
                break;
            case 3:
                jrs.setString(numParameter, searchFields.getDescription());
                break;
            default:
                throw new SQLException();
        }

    }
}
