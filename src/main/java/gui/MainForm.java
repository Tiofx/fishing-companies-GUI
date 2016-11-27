package gui;

import models.Connection;
import models.table.BaseTableModel;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class MainForm extends JFrame {
    private Connection connection;

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

        addTableView("fish", connection.getFishRS());
        addListeners();
    }

    public static void main(String[] args) {
        JFrame frame = new MainForm();
        frame.setVisible(true);
        frame.pack();
    }

    private void addTableView(String tableName, ResultSet tableDate) {
        JScrollPane scroll = new JScrollPane();
        JTable table = new JTable(new BaseTableModel(tableDate));
        table.setShowGrid(true);
        table.setGridColor(Color.GRAY);

        scroll.setViewportView(table);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        tabbedPane.addTab(tableName, scroll);
    }

    private void addListeners() {
    }
}
