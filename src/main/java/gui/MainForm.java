package gui;

import models.Connection;
import models.Fish;
import models.table.BaseTableModel;

import javax.swing.*;
import java.awt.*;
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

        addTableView(connection.getFishRS());
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
        table.setShowGrid(true);
        table.setGridColor(Color.GRAY);

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
    }

    private boolean deleteFromFishTable(int rowNum) {
        try {
            ResultSet rs = connection.getFishRS();
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
            ResultSet rs = connection.getFishRS();
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

    @Override
    public void dispose() {
        super.dispose();
        connection.close();
    }
}
