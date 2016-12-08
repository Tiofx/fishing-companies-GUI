package controllers;

import models.sql.Voyage;
import unit.Connection;

import javax.sql.rowset.JdbcRowSet;
import java.sql.SQLException;

@Deprecated
public class VoyageController extends AbstractController<Voyage> {
    public VoyageController() {
    }

    public VoyageController(Connection connection, String tableName) {
        super(connection, tableName);
    }

    public VoyageController(JdbcRowSet jrs) {
        super(jrs);
    }

    @Override
    protected void updateRecord(Voyage newRecord) throws SQLException {
        if (newRecord.getId() != -1) {
            jrs.updateInt(1, newRecord.getId());
        }
        jrs.updateInt(2, newRecord.getCaptainId());
        jrs.updateInt(3, newRecord.getShipId());
        jrs.updateInt(4, newRecord.getFishSeasonId());
        jrs.updateInt(5, newRecord.getQuotaId());
        jrs.updateTimestamp(6, newRecord.getDepartureDate());
        jrs.updateTimestamp(7, newRecord.getReturnDate());
    }

    @Override
    protected Voyage getRecord() throws SQLException {
        return new Voyage(jrs.getInt(1), jrs.getInt(2), jrs.getInt(3), jrs.getInt(4),
                jrs.getInt(5), jrs.getTimestamp(6), jrs.getTimestamp(7));
    }

    @Override
    protected void setCommandParameter(int numParameter, int numModelField, Voyage searchFields) throws SQLException {
        switch (numModelField) {
            case 1:
                jrs.setInt(numParameter, searchFields.getId());
                break;
            case 2:
                jrs.setInt(numParameter, searchFields.getCaptainId());
                break;
            case 3:
                jrs.setInt(numParameter, searchFields.getShipId());
                break;
            case 4:
                jrs.setInt(numParameter, searchFields.getFishSeasonId());
                break;
            case 5:
                jrs.setInt(numParameter, searchFields.getQuotaId());
                break;
            case 6:
                jrs.setTimestamp(numParameter, searchFields.getDepartureDate());
                break;
            case 7:
                jrs.setTimestamp(numParameter, searchFields.getReturnDate());
                break;
            default:
                throw new SQLException();
        }
    }
}
