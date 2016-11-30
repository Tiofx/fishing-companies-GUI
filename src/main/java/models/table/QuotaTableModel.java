package models.table;

import com.sun.rowset.JdbcRowSetImpl;
import controllers.FishRegionController;
import models.Connection;
import models.sql.FishRegion;

import javax.sql.rowset.JdbcRowSet;
import java.sql.SQLException;

public class QuotaTableModel extends BaseTableModel {
    protected FishRegionController fishRegion;
    protected final int replaceableNumber = 2;
    protected final int whatReplace = 1;
    private FishRegion rawRecord = new FishRegion();

    public QuotaTableModel(JdbcRowSet tableResultSet, int skipFirst) {
        super(tableResultSet, skipFirst);

        try {
//            JdbcRowSet res = new JdbcRowSetImpl(DriverManager.getConnection(tableResultSet.getUrl()));
            JdbcRowSet res = new JdbcRowSetImpl(Connection.connection);
            res.setCommand("select * from fishRegion");
            res.execute();
            // TODO: 30/11/2016 change model this
            fishRegion = new FishRegionController(res, null, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public QuotaTableModel(JdbcRowSet tableResultSet) {
        super(tableResultSet);

        try {
//            JdbcRowSet res = new JdbcRowSetImpl(DriverManager.getConnection(tableResultSet.getUrl()));
            JdbcRowSet res = new JdbcRowSetImpl(Connection.connection);
            res.setCommand("select * from fishRegion");
            res.execute();
            fishRegion = new FishRegionController(res, null, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getColumnName(int column) {
        if (column == whatReplace - 1) {
            try {
                return fishRegion.getJrs().getMetaData().getColumnName(replaceableNumber);
            } catch (SQLException e) {
                e.printStackTrace();
                return "error get name";
            }
        } else return super.getColumnName(column);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == whatReplace - 1) {
            Boolean[] mask = {true, false, false};
            rawRecord.setId((int) super.getValueAt(rowIndex, columnIndex));

            fishRegion.fullFind(mask, rawRecord);
            try {
                fishRegion.getJrs().first();
                return fishRegion.getJrs().getString(replaceableNumber);
            } catch (SQLException e) {
                e.printStackTrace();
                return "error get value";
            }
        } else
            return super.getValueAt(rowIndex, columnIndex);
    }
}
