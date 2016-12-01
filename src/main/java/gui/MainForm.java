package gui;

import controllers.*;
import models.Connection;
import models.gui.BaseTableModel;
import models.gui.QuotaTableModel;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.function.Function;

public class MainForm extends JFrame {

    private Connection connection;
    private final AbstractController[] allControllers;

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

        allControllers = new AbstractController[connection.getTablesNumber()];

        for (int i = 0; i < connection.getTablesNumber(); i++) {
            allControllers[i] = addTableView(i, connection.getTablesName()[i], connection);
        }

        addListeners();
    }

    public static void main(String[] args) {
        JFrame frame = new MainForm();
        frame.setVisible(true);
        frame.pack();
    }

    private AbstractController addTableView(int i, String tableName, Connection connection) {
        return addTableView(i, tableName, connection.getJRS(tableName));
    }

    private AbstractController addTableView(int i, JdbcRowSet tableDate) {
        try {
            return addTableView(i, tableDate.getMetaData().getTableName(1), tableDate);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private AbstractController addTableView(int i, String tableName, JdbcRowSet tableDate) {
        AbstractController result = null;
        JScrollPane scroll = new JScrollPane();
        BaseTableModel tb = new BaseTableModel(tableDate);
        final JTable table;

        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                table = new JTable(tb);
                break;
            case 5:
                tb = new QuotaTableModel(tableDate);
                table = new JTable(tb);
                break;
            default:
                table = null;
        }

        switch (i) {
            case 0:
                result = new FishController(tableDate, tb, table);
                break;
            case 1:
                result = new ShipController(tableDate, tb, table);
                break;
            case 2:
                result = new CaptainController(tableDate, tb, table);
                break;
            case 3:
                result = new InventoryController(tableDate, tb, table);
                break;
            case 4:
                result = new FishRegionController(tableDate, tb, table);
                break;
            case 5:
                result = new QuotaController(tableDate, tb, table);
                break;
        }


        table.setAutoCreateRowSorter(false);
        table.setShowGrid(true);
        table.setGridColor(Color.GRAY);
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

        return result;
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
            case 2:
                return new CaptainForm();
            case 3:
                return new InventoryForm();
            case 4:
                return new FishRegionForm();
            case 5:
                return new QuotaForm(connection.getJRS("fishRegion"));
            default:
                return null;
        }
    }

    private void addListeners() {
        addButton.addActionListener(e -> {
            int i = tabbedPane.getSelectedIndex();
            IUniversalForm inputForm = getInputPanel(i);
            makeOperation("add", inputForm, a -> allControllers[i].insert(a));
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

        exitButton.addActionListener(e -> dispose());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(1);
            }
        });
    }

    @Override
    public void dispose() {
        super.dispose();
        connection.close();
    }
}
