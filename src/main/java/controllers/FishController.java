package controllers;

import models.Fish;
import models.table.BaseTableModel;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.*;
import java.sql.SQLException;

public class FishController {
    private JdbcRowSet jrs;
    private BaseTableModel tableModel;
    private JTable view;

    public FishController(JdbcRowSet jrs, BaseTableModel tableModel, JTable view) {
        this.jrs = jrs;
        this.tableModel = tableModel;
        this.view = view;
    }

    protected void setFish(Fish newFish) throws SQLException {
        if (newFish.getId() != -1) {
            jrs.updateInt(1, newFish.getId());
        }
        jrs.updateString(2, newFish.getName());
        jrs.updateInt(3, newFish.getPrice());
    }

    public boolean insert(Fish fish) {
        try {
            jrs.moveToInsertRow();

            setFish(fish);

            jrs.insertRow();
            jrs.beforeFirst();

            return true;
        } catch (SQLException e1) {
            e1.printStackTrace();
            return false;
        }
    }

    public boolean edit(int oldFish, Fish newFish) {
        try {
            jrs.absolute(oldFish);

            setFish(newFish);

            jrs.updateRow();
            return true;
        } catch (SQLException e1) {
            e1.printStackTrace();
            return false;
        }
    }

    public boolean delete(int rowNum) {
        try {
            jrs.absolute(rowNum);
            jrs.deleteRow();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Fish getFish(int rowNum) {
        Fish fish = null;
        try {
            jrs.absolute(rowNum);
            fish = new Fish(jrs.getInt(1), jrs.getString(2), jrs.getInt(3));
            return fish;
        } catch (SQLException e1) {
            e1.printStackTrace();
            return fish;
        }
    }

    public boolean find(Boolean[] mask, Fish searchFields) {
        try {
            int n = jrs.getMetaData().getColumnCount();
            String preparedStatement = formPreparedStatement(mask, n);
            jrs.setCommand(preparedStatement);

            int firstSkip = 1;
            int numPar = 1;
            for (int i = firstSkip; i < n; i++) {
                if (mask[i - firstSkip]) {
                    switch (i + 1) {
                        case 1:
                            jrs.setInt(numPar, searchFields.getId());
                            break;
                        case 2:
                            jrs.setString(numPar, searchFields.getName());
                            break;
                        case 3:
                            jrs.setInt(numPar, searchFields.getPrice());
                            break;
                    }
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

    protected String formPreparedStatement(Boolean[] f, int columnCount) {
        String stt = "SELECT * FROM fish WHERE ";
        String condition = "";

        int firstSkip = 1;

        for (int i = firstSkip; i < columnCount; i++) {
            try {
                if (f[i - firstSkip])
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

    private final static int UNSORTED = 0;
    private final static int DESCENDING = -1;
    private final static int ASCENDING = 1;
    private int[] sortedInfo = {UNSORTED, UNSORTED};

    public boolean sort(int column) {
        try {
            switch (column) {
                case 1:
                    if (sortedInfo[column] != ASCENDING) {
                        jrs.setCommand("select * from fish order by name ASC");
                        sortedInfo[column] = ASCENDING;
                    } else {
                        jrs.setCommand("select * from fish order by name DESC");
                        sortedInfo[column] = DESCENDING;
                    }
                    break;
                case 2:
                    if (sortedInfo[column] != ASCENDING) {
                        jrs.setCommand("select * from fish order by price ASC");
                        sortedInfo[column] = ASCENDING;
                    } else {
                        jrs.setCommand("select * from fish order by price DESC");
                        sortedInfo[column] = DESCENDING;
                    }
                    break;
            }
            jrs.execute();
            jrs.next();
            tableModel.fireTableDataChanged();
            return true;
        } catch (SQLException e1) {
            e1.printStackTrace();
            return false;
        }
    }
}
