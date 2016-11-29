package gui;

import controllers.AbstractController;
import controllers.FishController;
import controllers.ShipController;
import models.Connection;
import models.table.BaseTableModel;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.function.Function;

public class MainForm extends JFrame {
//    private FishController fishController;

    private Connection connection;
    //    private AbstractController[] allControllers = new AbstractController[2];
    private AbstractController[] allControllers = new AbstractController[2];


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
        tabbedPane.remove(0);
        pack();
        this.setVisible(true);

        connection = new Connection();
        connection.startConnection();

        addTableView(0, connection.getFishJRS());
        addTableView(1, connection.getShipJRS());
        addListeners();
    }

    public static void main(String[] args) {
        JFrame frame = new MainForm();
        frame.setVisible(true);
        frame.pack();
    }

    private void addTableView(int i, JdbcRowSet tableDate) {
        try {
            addTableView(i, tableDate.getMetaData().getTableName(1), tableDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addTableView(int i, String tableName, JdbcRowSet tableDate) {
        JScrollPane scroll = new JScrollPane();
        BaseTableModel tb = new BaseTableModel(tableDate);
        JTable table = new JTable(tb);
        table.setAutoCreateRowSorter(false);
        table.setShowGrid(true);
        table.setGridColor(Color.GRAY);

        switch (i) {
            case 0:
                allControllers[i] = new FishController(tableDate, tb, table);
                break;
            case 1:
                allControllers[i] = new ShipController(tableDate, tb, table);
                break;
        }

        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point point = e.getPoint();
                int column = table.columnAtPoint(point);

                allControllers[i].sort(column + 1);
            }
        });

        scroll.setViewportView(table);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        tabbedPane.addTab(tableName, scroll);

//        return fishController;
    }

    //    private void makeOperation(String nameOperation, IUniversalForm<? extends ISqlModel> inputPanel, Function<? extends ISqlModel, Boolean> func) {
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
//                        tb.fireTableDataChanged();
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

    private IUniversalForm getInputPanel(int i) {
        switch (i) {
            case 0:
                return new FishForm();
            case 1:
                return new ShipForm();
            default:
                return null;
        }
    }

    private void addListeners() {
        addButton.addActionListener(e -> {
            int i = tabbedPane.getSelectedIndex();
            IUniversalForm inputForm = getInputPanel(i);
            makeOperation("add", inputForm, a -> allControllers[i].insert(a));
//            switch (i) {
//                case 0:
//                    makeOperation("add", new FishForm(), a -> ((FishController) allControllers[i]).insert(a));
//                    break;
//                case 1:
//                    makeOperation("add", new ShipForm(), a -> ((ShipController) allControllers[i]).insert(a));
//                    break;
//            }
            allControllers[i].update();
        });

        deleteButton.addActionListener(e -> {
            int i = tabbedPane.getSelectedIndex();
            allControllers[i].deleteSelectedInTable();
            allControllers[i].update();
        });

        editButton.addActionListener(e -> {
            int i = tabbedPane.getSelectedIndex();
            IUniversalForm inputForm = getInputPanel(i);
            inputForm.setRecord(allControllers[i].getRecordSelectedInTable());
            makeOperation("edit", inputForm, a -> allControllers[i].editSelectedInTable(a));
//            switch (i) {
//                case 0:
//                    FishForm inputPanel = new FishForm();
//                    inputPanel.setRecord(((FishController) allControllers[i]).getRecordSelectedInTable());
//                    makeOperation("edit", new FishForm(), a -> ((FishController) allControllers[i]).editSelectedInTable(a));
//                    break;
//                case 1:
//                    ShipForm inputPanel2 = new ShipForm();
//                    inputPanel2.setRecord(((ShipController) allControllers[i]).getRecordSelectedInTable());
//                    makeOperation("edit", new ShipForm(), a -> ((ShipController) allControllers[i]).editSelectedInTable(a));
//                    break;
//            }
            allControllers[i].update();
        });

        findButton.addActionListener(e -> {
            int i = tabbedPane.getSelectedIndex();
            IUniversalForm inputForm = getInputPanel(i);

            int result = JOptionPane.showConfirmDialog(null,
                    inputForm,
                    "Find form",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                Object record = inputForm.getRawRecord();
                allControllers[i].find(inputForm.canGets(), record);
                allControllers[i].update();
            }
        });


        resetButton.addActionListener(e -> {
            int i = tabbedPane.getSelectedIndex();
            allControllers[i].reset();
        });
    }

    @Override
    public void dispose() {
        super.dispose();
        connection.close();
    }
}
