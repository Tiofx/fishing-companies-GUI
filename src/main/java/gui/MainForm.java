package gui;

import models.Connection;
import models.Fish;
import models.table.BaseTableModel;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainForm extends JFrame {
    private Connection connection;
    private BaseTableModel tb;
    private JTable table;

    private JPanel rootPanel;
    private JTabbedPane tabbedPane;
    private JButton addButton;
    private JButton deleteButton;
    private JButton editButton;
    private JButton findButton;

    private JButton exitButton;

    public MainForm() throws HeadlessException {
        super("fishing companies");
        setContentPane(rootPanel);
        this.setBounds(rootPanel.getBounds());
        this.setMinimumSize(rootPanel.getMinimumSize());
        this.setLocation(494, 153);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        this.setVisible(true);

        connection = new Connection();
        connection.startConnection();

        addTableView(connection.getFishJRS());
        addListeners();
    }

    public static void main(String[] args) {
        JFrame frame = new MainForm();
        frame.setVisible(true);
        frame.pack();
    }

    private void addTableView(ResultSet tableDate) {
        try {
            addTableView(tableDate.getMetaData().getTableName(1), tableDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addTableView(String tableName, ResultSet tableDate) {
        JScrollPane scroll = new JScrollPane();
        tb = new BaseTableModel(tableDate);
        table = new JTable(tb);
        table.setAutoCreateRowSorter(false);
        table.setShowGrid(true);
        table.setGridColor(Color.GRAY);


        table.getTableHeader().addMouseListener(new MouseAdapter() {

            private final static int UNSORTED = 0;
            private final static int DESCENDING = -1;
            private final static int ASCENDING = 1;
            private int[] sortedInfo = {UNSORTED, UNSORTED};

            @Override
            public void mouseReleased(MouseEvent e) {
                Point point = e.getPoint();
                int column = table.columnAtPoint(point);

                JdbcRowSet jrs = connection.getFishJRS();
                try {
                    switch (column + 1) {
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
                    tb.fireTableDataChanged();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        scroll.setViewportView(table);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        tabbedPane.addTab(tableName, scroll);
    }

    private void addListeners() {
        addButton.addActionListener(e -> {
                    FishForm inputPanel = new FishForm();
                    do {
                        int result = JOptionPane.showConfirmDialog(null,
                                inputPanel,
                                "Input form",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.PLAIN_MESSAGE);

                        if (result == JOptionPane.OK_OPTION) {
                            if (inputPanel.canGetFish()) {
                                Fish fish = inputPanel.getFish();
                                if (!insertIntoFishTable(fish)) {
                                    JOptionPane.showMessageDialog(
                                            this,
                                            "This record wasn't inserted!",
                                            "Insert problem",
                                            JOptionPane.WARNING_MESSAGE);
                                } else {
                                    tb.fireTableDataChanged();
                                }
                                break;
                            } else {
                                JOptionPane.showMessageDialog(
                                        inputPanel,
                                        "You have error in fields: " + inputPanel.incorrectFields(),
                                        "Incorrect data",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            break;
                        }
                    } while (true);
                }
        );

        deleteButton.addActionListener(e -> {
            deleteFromFishTable(table.getSelectedRow() + 1);

            tb.fireTableDataChanged();
        });

        editButton.addActionListener(e -> {
            FishForm inputPanel = new FishForm();
            do {
                inputPanel.setFish(getFish(table.getSelectedRow() + 1));
                int result = JOptionPane.showConfirmDialog(null,
                        inputPanel,
                        "Edit form",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    if (inputPanel.canGetFish()) {
                        Fish fish = inputPanel.getFish();

                        if (!editFishTable(table.getSelectedRow() + 1, fish)) {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "This record wasn't edited!",
                                    "Edit problem",
                                    JOptionPane.WARNING_MESSAGE);
                        } else {
                            tb.fireTableDataChanged();
                        }
                        break;
                    } else {
                        JOptionPane.showMessageDialog(
                                inputPanel,
                                "You have error in fields: " + inputPanel.incorrectFields(),
                                "Incorrect data",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    break;
                }
            } while (true);

        });

        findButton.addActionListener(e -> {
            FishForm inputPanel = new FishForm();
            int result = JOptionPane.showConfirmDialog(null,
                    inputPanel,
                    "Find form",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                Fish fish = inputPanel.getRawFish();
                findInFishTable(inputPanel.canGets(), fish);
                tb.fireTableDataChanged();
            }
        });
    }


    private void findInFishTable(Boolean[] f, Fish searchFields) {
        JdbcRowSet jrs = connection.getFishJRS();
        try {
            int n = jrs.getMetaData().getColumnCount();
            String preparedStatement = formPreparedStatement(f, n);
            jrs.setCommand(preparedStatement);

            int firstSkip = 1;
            int numPar = 1;
            for (int i = firstSkip; i < n; i++) {
                if (f[i - firstSkip]) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String formPreparedStatement(Boolean[] f, int columnCount) {
        String stt = "select * from fish where ";
        String condition = "";
        int firstSkip = 1;
        for (int i = firstSkip; i < columnCount; i++) {
            try {
                if (f[i - firstSkip])
                    condition += connection.getFishJRS().getMetaData().getColumnName(i + 1) + " = ?  ";
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        condition = condition.trim();
        condition = condition.replaceAll("  ", " AND ");
        stt += condition;
        return stt;
    }

    private boolean deleteFromFishTable(int rowNum) {
        try {
            ResultSet rs = connection.getFishJRS();
            rs.absolute(rowNum);
            rs.deleteRow();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean insertIntoFishTable(Fish fish) {
        try {
            ResultSet rs = connection.getFishJRS();
            rs.moveToInsertRow();
            if (fish.getId() != -1) {
                rs.updateInt(1, fish.getId());
            }
            rs.updateString(2, fish.getName());
            rs.updateInt(3, fish.getPrice());
            rs.insertRow();
            rs.beforeFirst();
            return true;
        } catch (SQLException e1) {
            e1.printStackTrace();
            return false;
        }
    }

    private boolean editFishTable(int selectedRow, Fish changedFish) {
        try {
            ResultSet rs = connection.getFishJRS();
            rs.absolute(selectedRow);
            rs.updateString(2, changedFish.getName());
            rs.updateInt(3, changedFish.getPrice());
            rs.updateRow();
            return true;
        } catch (SQLException e1) {
            e1.printStackTrace();
            return false;
        }
    }

    private Fish getFish(int rowNum) {
        Fish fish = null;
        try {
            ResultSet rs = connection.getFishJRS();
            rs.absolute(rowNum);
            fish = new Fish(rs.getInt(1), rs.getString(2), rs.getInt(3));
            return fish;
        } catch (SQLException e1) {
            e1.printStackTrace();
            return fish;
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        connection.close();
    }
}
