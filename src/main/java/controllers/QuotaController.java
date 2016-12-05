package controllers;

import com.sun.rowset.JdbcRowSetImpl;
import models.Connection;
import models.gui.BaseTableModel;
import models.gui.QuotaTableModel;
import models.sql.Quota;

import javax.sql.rowset.JdbcRowSet;
import java.sql.SQLException;

public class QuotaController extends AbstractController<Quota> {

    public QuotaController() {
    }

    public QuotaController(JdbcRowSet jrs) {
        super(jrs);
    }

    @Override
    protected BaseTableModel createTableModel(JdbcRowSet jrs) {
        try {
            JdbcRowSet res = new JdbcRowSetImpl(Connection.connection);
            res.setCommand("select * from fishRegion");
            res.execute();
            return new QuotaTableModel(jrs, res);
        } catch (SQLException e) {
            e.printStackTrace();
            return new BaseTableModel(jrs);
        }
    }

    @Override
    protected void updateRecord(Quota newRecord) throws SQLException {
        if (newRecord.getId() != -1) {
            jrs.updateInt(1, newRecord.getId());
        }
        jrs.updateInt(2, newRecord.getFishRegionId());
        jrs.updateShort(3, newRecord.getYear());
    }

    @Override
    protected Quota getRecord() throws SQLException {
        return new Quota(jrs.getInt(1), jrs.getInt(2), jrs.getShort(3));
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
                jrs.setShort(numParameter, searchFields.getYear());
                break;
            default:
                throw new SQLException();
        }
    }
}
