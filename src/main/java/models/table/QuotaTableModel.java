package models.table;

import com.sun.rowset.JdbcRowSetImpl;
import controllers.FishRegionController;
import models.sql.FishRegion;

import javax.sql.rowset.JdbcRowSet;
import java.sql.DriverManager;
import java.sql.SQLException;

public class QuotaTableModel extends BaseTableModel {
    protected FishRegionController fishRegion;
    protected final int replaceableNumber = 2;
    private FishRegion rawRecord = new FishRegion();

    public QuotaTableModel(JdbcRowSet tableResultSet, int skipFirst) {
        super(tableResultSet, skipFirst);

        try {
            JdbcRowSet res = new JdbcRowSetImpl(DriverManager.getConnection(tableResultSet.getUrl()));
            res.setCommand("select * from fishRegion");
            res.execute();
            fishRegion = new FishRegionController(res, null, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public QuotaTableModel(JdbcRowSet tableResultSet) {
        super(tableResultSet);

        try {
            JdbcRowSet res = new JdbcRowSetImpl(DriverManager.getConnection(tableResultSet.getUrl()));
            res.setCommand("select * from fishRegion");
            res.execute();
            fishRegion = new FishRegionController(res, null, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0 + skipFirst) {
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
        if (columnIndex == 0 + skipFirst) {
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
