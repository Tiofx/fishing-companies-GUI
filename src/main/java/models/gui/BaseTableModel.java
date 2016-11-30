package models.gui;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.table.AbstractTableModel;
import java.sql.SQLException;

public class BaseTableModel extends AbstractTableModel {
    protected JdbcRowSet tableResultSet;
    protected int skipFirst;

    public BaseTableModel(JdbcRowSet tableResultSet, int skipFirst) {
        this.tableResultSet = tableResultSet;
        this.skipFirst = skipFirst;
    }

    public BaseTableModel(JdbcRowSet tableResultSet) {
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
