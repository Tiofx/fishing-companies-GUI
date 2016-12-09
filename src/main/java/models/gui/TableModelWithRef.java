package models.gui;

import javax.sql.rowset.JdbcRowSet;
import java.sql.SQLException;

public class TableModelWithRef extends BaseTableModel {
    protected final int replaceableNumber = 2;
    protected final int whatReplace = 1;
    protected JdbcRowSet joinable;
    protected int lastSize = -1;

    public TableModelWithRef(JdbcRowSet tableResultSet, JdbcRowSet joinable) {
        super(tableResultSet);
        this.joinable = joinable;
    }

    public TableModelWithRef(JdbcRowSet tableResultSet, int skipFirst, JdbcRowSet joinable) {
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
            try {
                int id = (int) super.getValueAt(rowIndex, columnIndex);
                // TODO: 05/12/2016 ineffective
                joinable.execute();
                joinable.beforeFirst();
                while (joinable.next()) {
                    if (id == joinable.getInt(replaceableNumber - 1)) {
                        return joinable.getObject(replaceableNumber);
                    }
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else
            return super.getValueAt(rowIndex, columnIndex);
    }
}
