package gui;

import controllers.FishController;
import models.Connection;
import models.Fish;
import models.table.BaseTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

public class MainForm extends JFrame {
    private FishController fishController;

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
    private JButton resetButton;

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

        fishController = new FishController(connection.getFishJRS(), tb, table);


        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point point = e.getPoint();
                int column = table.columnAtPoint(point);

                fishController.sort(column + 1);
            }
        });

        scroll.setViewportView(table);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        tabbedPane.addTab(tableName, scroll);

//        return fishController;
    }

    private <T> void makeOperation(String nameOperation, IUniversalForm<T> inputPanel, Function<T, Boolean> func) {
        do {
            int result = JOptionPane.showConfirmDialog(null,
                    inputPanel,
                    nameOperation + " form",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                if (inputPanel.canGetRecord()) {
                    T record = inputPanel.getRecord();

                    if (!func.apply(record)) {
                        JOptionPane.showMessageDialog(
                                this,
                                "This record wasn't " + nameOperation + "ed!!!",
                                nameOperation + " problem",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        tb.fireTableDataChanged();
                    }

                    break;
                } else {
//                    JOptionPane.showMessageDialog(
//                            inputPanel,
//                            "You have error in fields: " + inputPanel.incorrectFields(),
//                            "Incorrect data",
//                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                break;
            }
        } while (true);

    }

    private void addListeners() {
        addButton.addActionListener(e -> {
            FishForm inputPanel = new FishForm();
            makeOperation("add", inputPanel, a -> fishController.insert(a));
        });

        deleteButton.addActionListener(e -> {
            fishController.delete(table.getSelectedRow() + 1);

            tb.fireTableDataChanged();
        });

        editButton.addActionListener(e -> {
            FishForm inputPanel = new FishForm();
            inputPanel.setRecord(fishController.getRecordSelectedInTable());
            makeOperation("edit", inputPanel, a -> fishController.edit(a));
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
                fishController.find(inputPanel.canGets(), fish);
                tb.fireTableDataChanged();
            }
        });


        resetButton.addActionListener(e -> {
            fishController.reset();
        });
    }

    @Override
    public void dispose() {
        super.dispose();
        connection.close();
    }
}
