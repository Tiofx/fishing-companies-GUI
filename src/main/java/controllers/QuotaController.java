package controllers;

import models.sql.Quota;
import unit.Connection;

import javax.sql.rowset.JdbcRowSet;
import java.sql.SQLException;

public class QuotaController extends AbstractController<Quota> {

    public QuotaController() {
    }

    public QuotaController(Connection connection, String tableName) {
        this(connection, tableName, "fishRegion");
    }

    public QuotaController(Connection connection, String tableName, String refTableName) {
        super(connection, tableName);
    }

    public QuotaController(JdbcRowSet jrs) {
        super(jrs);
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
