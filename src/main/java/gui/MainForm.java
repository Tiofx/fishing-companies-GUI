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


        LoginForm loginForm = new LoginForm();
        unit.Dialog.makeOperation("authorization", loginForm, a -> true);
        connection = loginForm.getRecord();
//        connection = new Connection();
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
        deleteButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete");
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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootPanel = new JPanel();
        rootPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane = new JTabbedPane();
        rootPanel.add(tabbedPane, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(600, 100), new Dimension(600, 200), new Dimension(600, 300), 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab("Untitled", panel1);
        addButton = new JButton();
        addButton.setSelected(false);
        addButton.setText("Add");
        addButton.setMnemonic('A');
        addButton.setDisplayedMnemonicIndex(0);
        rootPanel.add(addButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        findButton = new JButton();
        findButton.setText("Find");
        findButton.setMnemonic('F');
        findButton.setDisplayedMnemonicIndex(0);
        rootPanel.add(findButton, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteButton = new JButton();
        deleteButton.setText("Delete");
        deleteButton.setMnemonic('D');
        deleteButton.setDisplayedMnemonicIndex(0);
        rootPanel.add(deleteButton, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        editButton = new JButton();
        editButton.setText("Edit");
        editButton.setMnemonic('T');
        editButton.setDisplayedMnemonicIndex(3);
        rootPanel.add(editButton, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        resetButton = new JButton();
        resetButton.setText("Reset");
        resetButton.setMnemonic('R');
        resetButton.setDisplayedMnemonicIndex(0);
        rootPanel.add(resetButton, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        exitButton = new JButton();
        exitButton.setText("Exit");
        exitButton.setMnemonic('E');
        exitButton.setDisplayedMnemonicIndex(0);
        rootPanel.add(exitButton, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}
