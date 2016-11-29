package gui;

import models.sql.Ship;

import javax.swing.*;

public class ShipForm extends JPanel implements IUniversalForm<Ship> {
    private JPanel rootPanel;
    private JTextField nameTxt;

    public ShipForm() {
        super();
        add(rootPanel);
    }

    @Override
    public Ship getRecord() {
        return new Ship(getName());
    }

    @Override
    public void setRecord(Ship record) {
        nameTxt.setText(record.getName());
    }

    @Override
    public Boolean[] canGets() {
        return new Boolean[]{canGetName()};
    }

    public String getName() {
        return nameTxt.getText();
    }

    public boolean canGetName() {
        return getName().length() != 0;
    }
}
