package models.table;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseTableModel extends AbstractTableModel {
    private ResultSet dateFromFish;
    private int skipFirst;

    public BaseTableModel(ResultSet dateFromFish, int skipFirst) {
        this.dateFromFish = dateFromFish;
        this.skipFirst = skipFirst;
    }

    public BaseTableModel(ResultSet dateFromFish) {
        this(dateFromFish, 1);
    }

    @Override
    public int getRowCount() {
        try {
            dateFromFish.last();
            return dateFromFish.getRow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String getColumnName(int column) {
        try {
            System.out.println(dateFromFish.getMetaData().getColumnLabel(column + 1 + skipFirst));
            return dateFromFish.getMetaData().getColumnLabel(column + 1 + skipFirst);
        } catch (SQLException e) {
            return e.toString();
        }
    }

    @Override
    public int getColumnCount() {
        try {
            return dateFromFish.getMetaData().getColumnCount() - skipFirst;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            dateFromFish.absolute(rowIndex + 1);
            return dateFromFish.getObject(columnIndex + 1 + skipFirst);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
