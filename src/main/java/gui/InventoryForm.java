package gui;

import models.sql.Inventory;

import javax.swing.*;

public class InventoryForm extends JPanel implements IUniversalForm<Inventory> {
    private JPanel rootPanel;
    private JTextField nameTxt;
    private JTextField lifeTimeTxt;
    private JTextField manufactureDateTxt;

    public InventoryForm() {
        super();
        add(rootPanel);
    }

    @Override
    public Inventory getRecord() {
        return new Inventory(getName(), getLifeTime(), getManufactureDate());
    }

    @Override
    public Inventory getRawRecord() {
        Inventory inventory = new Inventory();

        if (canGetName())
            inventory.setName(getName());
        if (canGetLifeTime())
            inventory.setLifeTime(getLifeTime());
        if (canGetManufactureDate())
            inventory.setManufactureDate(getManufactureDate());

        return inventory;
    }

    @Override
    public void setRecord(Inventory record) {
        nameTxt.setText(record.getName());
        lifeTimeTxt.setText(Integer.toString(record.getLifeTime()));
        manufactureDateTxt.setText(Integer.toString(record.getManufactureDate()));
    }

    @Override
    public Boolean[] canGets() {
        return new Boolean[]{canGetName(), canGetLifeTime(), canGetManufactureDate()};
    }

    public boolean canGetName() {
        return getName().length() > 0 && getName().length() < 45;
    }

    public boolean canGetLifeTime() {
        try {
            getLifeTime();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean canGetManufactureDate() {
        try {
            getManufactureDate();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String getName() {
        return nameTxt.getText();
    }

    public int getLifeTime() {
        return Integer.parseUnsignedInt(lifeTimeTxt.getText());
    }

    public int getManufactureDate() {
        return Integer.parseUnsignedInt(manufactureDateTxt.getText());
    }
}
