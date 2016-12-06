package gui;

import controllers.AbstractController;
import unit.Connection;
import unit.IUniversalForm;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static unit.Connection.formFactory;

public class MainForm extends JFrame {

    private Connection connection;
    private final AbstractController[] allControllers;
//    private final FormFactory<Class> formFactory = new FormFactory<>();

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
            allControllers[i] = addTableView(i);
        }

        addListeners();
    }

    public static void main(String[] args) {
        JFrame frame = new MainForm();
        frame.setVisible(true);
        frame.pack();
    }

    private AbstractController addTableView(int i) {
        String tableName = connection.getTablesName()[i];
        return addTableView(tableName, connection.getJRS(tableName));
    }

    private AbstractController addTableView(String tableName, JdbcRowSet tableDate) {
        AbstractController result = null;
        JScrollPane scroll = new JScrollPane();
        final JTable table;

        result = Connection.controllerFactory.getInstance(tableName);
        table = result.getView();

        scroll.setViewportView(table);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        tabbedPane.addTab(tableName, scroll);

        return result;
    }

    private void addListeners() {
        addButton.setMnemonic(KeyEvent.VK_N);
        editButton.setMnemonic(KeyEvent.VK_E);
        findButton.setMnemonic(KeyEvent.VK_F);
        resetButton.setMnemonic(KeyEvent.VK_R);
        deleteButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete");
        deleteButton.getActionMap().put("delete", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteButton.getActionListeners()[0].actionPerformed(e);
            }
        });

        addButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_N && ke.isAltDown()) {
                    addButton.getActionListeners()[0].actionPerformed(null);
                }
            }
        });
        addButton.addActionListener(e -> {
            int i = tabbedPane.getSelectedIndex();
            IUniversalForm inputForm = formFactory.getInstance(allControllers[i].getClass());
            unit.Dialog.makeOperation("add", inputForm, a -> allControllers[i].insert(a));
            allControllers[i].update();
        });

        deleteButton.addActionListener(e -> {
            int i = tabbedPane.getSelectedIndex();
            while (allControllers[i].deleteSelectedInTable()) ;
            allControllers[i].update();
        });

        editButton.addActionListener(e -> {
            int i = tabbedPane.getSelectedIndex();
            IUniversalForm inputForm = formFactory.getInstance(allControllers[i].getClass());
            inputForm.setRecord(allControllers[i].getRecordSelectedInTable());
            unit.Dialog.makeOperation("edit", inputForm, a -> allControllers[i].editSelectedInTable(a));
            allControllers[i].update();
        });

        findButton.addActionListener(e -> {
            int i = tabbedPane.getSelectedIndex();
            IUniversalForm inputForm = formFactory.getInstance(allControllers[i].getClass());

            int result = JOptionPane.showConfirmDialog(this,
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
        connection.close();
        super.dispose();
    }
}
