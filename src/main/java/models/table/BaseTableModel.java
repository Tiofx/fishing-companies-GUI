package models.table;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseTableModel extends AbstractTableModel {
    private ResultSet tableResultSet;
    private int skipFirst;

    public BaseTableModel(ResultSet tableResultSet, int skipFirst) {
        this.tableResultSet = tableResultSet;
        this.skipFirst = skipFirst;
    }

    public BaseTableModel(ResultSet tableResultSet) {
        this(tableResultSet, 1);
    }

    @Override
    public int getRowCount() {
        try {
            tableResultSet.last();
            return tableResultSet.getRow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String getColumnName(int column) {
        try {
            return tableResultSet.getMetaData().getColumnLabel(column + 1 + skipFirst);
        } catch (SQLException e) {
            return e.toString();
        }
    }

    @Override
    public int getColumnCount() {
        try {
            return tableResultSet.getMetaData().getColumnCount() - skipFirst;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            tableResultSet.absolute(rowIndex + 1);
            return tableResultSet.getObject(columnIndex + 1 + skipFirst);
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
