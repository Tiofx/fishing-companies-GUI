package controllers;

import models.table.BaseTableModel;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.*;
import java.sql.SQLException;

public abstract class AbstractController<T> {
    protected final String baseStatement;

    protected JdbcRowSet jrs;
    protected BaseTableModel tableModel;
    protected JTable view;

    protected final static int UNSORTED = 0;
    protected final static int DESCENDING = -1;
    protected final static int ASCENDING = 1;
    protected int[] sortedInfo;


    public AbstractController(JdbcRowSet jrs, BaseTableModel tableModel, JTable view) {
        this.jrs = jrs;
        this.tableModel = tableModel;
        this.view = view;

        baseStatement = "SELECT * FROM " + getTableName();
        sortedInfo = new int[tableModel.getColumnCount()];
        resetSortedInfo();
    }

    protected abstract void updateRecord(T newRecord) throws SQLException;

    protected abstract T getRecord() throws SQLException;

    protected abstract int getSkipNumber();

    protected abstract void setCommandParameter(int numParameter, int numModelField, T searchFields) throws SQLException;

    protected String getTableName() {
        try {
            return jrs.getMetaData().getTableName(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public final T getRecordSelectedInTable() {
        return getRecord(view.getSelectedRow() + 1);
    }

    public final T getRecord(int rowNum) {
        try {
            jrs.absolute(rowNum);
            return getRecord();
        } catch (SQLException e1) {
            e1.printStackTrace();
            return null;
        }
    }
    
    public final boolean insert(T record) {
        try {
            jrs.moveToInsertRow();

            updateRecord(record);

            jrs.insertRow();
            jrs.beforeFirst();
            return true;
        } catch (SQLException e1) {
            e1.printStackTrace();
            return false;
        }
    }

    public final boolean editSelectedInTable(T newRecord) {
        return edit(view.getSelectedRow() + 1, newRecord);
    }

    public final boolean edit(T newRecord) {
        try {
            updateRecord(newRecord);
            jrs.updateRow();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean edit(int oldRecord, T newRecord) {
        try {
            jrs.absolute(oldRecord);
            updateRecord(newRecord);
            jrs.updateRow();
            return true;
        } catch (SQLException e1) {
            e1.printStackTrace();
            return false;
        }
    }

    public final boolean delete(int rowNum) {
        try {
            jrs.absolute(rowNum);
            jrs.deleteRow();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean find(Boolean[] mask, T searchFields) {
        try {
            int n = jrs.getMetaData().getColumnCount();
            String preparedStatement = formPreparedStatement(mask, n);
            jrs.setCommand(preparedStatement);

            int numPar = 1;
            for (int i = getSkipNumber(); i < n; i++) {
                if (mask[i - getSkipNumber()]) {
                    setCommandParameter(numPar, i + 1, searchFields);
                    numPar++;
                }
            }

            jrs.execute();
            jrs.next();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean sort(int column) {
        return sort(column, sortedInfo[column - 1] != ASCENDING ? ASCENDING : DESCENDING);
    }

    public final boolean sort(int column, int sortOrder) {
        try {
            String statement = baseStatement + " ORDER BY ";
            statement += jrs.getMetaData().getColumnName(column + getSkipNumber())
                    + " " + (sortOrder == ASCENDING ? "ASC" : "DESC");

            jrs.setCommand(statement);
            jrs.execute();
            jrs.next();

            tableModel.fireTableDataChanged();
            sortedInfo[column - 1] = sortOrder;
            return true;
        } catch (SQLException e1) {
            e1.printStackTrace();
            return false;
        }
    }

    public final boolean reset() {
        try {
            jrs.setCommand(baseStatement);
            jrs.execute();
            jrs.next();

            tableModel.fireTableDataChanged();
            resetSortedInfo();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected String formPreparedStatement(Boolean[] f, int columnCount) {
        String stt = baseStatement + " WHERE ";
        String condition = "";

        for (int i = getSkipNumber(); i < columnCount; i++) {
            try {
                if (f[i - getSkipNumber()])
                    condition += jrs.getMetaData().getColumnName(i + 1) + " = ?  ";
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        condition = condition.trim();
        condition = condition.replaceAll("  ", " AND ");
        stt += condition;
        return stt;
    }

    protected void resetSortedInfo() {
        for (int i = 0; i < sortedInfo.length; i++) {
            sortedInfo[i] = UNSORTED;
        }
    }
}
