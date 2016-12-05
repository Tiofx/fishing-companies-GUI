package models.gui;

import javax.sql.rowset.JdbcRowSet;
import java.sql.SQLException;

public class QuotaTableModel extends BaseTableModel {
    protected JdbcRowSet joinable;
    protected final int replaceableNumber = 2;
    protected final int whatReplace = 1;

    public QuotaTableModel(JdbcRowSet tableResultSet, JdbcRowSet joinable) {
        super(tableResultSet);
        this.joinable = joinable;
    }

    public QuotaTableModel(JdbcRowSet tableResultSet, int skipFirst, JdbcRowSet joinable) {
        super(tableResultSet, skipFirst);
        this.joinable = joinable;
    }

    @Override
    public String getColumnName(int column) {
        if (column == whatReplace - 1) {
            try {
                return joinable.getMetaData().getColumnName(replaceableNumber);
            } catch (SQLException e) {
                e.printStackTrace();
                return "error get name";
            }
        } else return super.getColumnName(column);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == whatReplace - 1) {
            int id = (int) super.getValueAt(rowIndex, columnIndex);
            try {
                joinable.beforeFirst();
                while (joinable.next()) {
                    if (id == joinable.getInt(replaceableNumber - 1)) {
                        return joinable.getObject(replaceableNumber);
                    }
                }
                return "incorrect";
            } catch (SQLException e) {
                e.printStackTrace();
                return "error get value";
            }
        } else
            return super.getValueAt(rowIndex, columnIndex);
    }
}
