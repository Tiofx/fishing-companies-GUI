package gui;

import controllers.InventoryController;
import gui.custom.SqlComboBox;
import models.gui.comboBox.InventoryComboBoxModel;
import models.sql.Inventory;
import models.sql.VoyageInventory;
import unit.IUniversalForm;

import javax.swing.*;

public class VoyageInventoryForm extends JPanel implements IUniversalForm<VoyageInventory> {
    private JPanel rootPanel;
    private JTextField voyageIdTxt;
    private SqlComboBox inventoryNameCB;

    private InventoryController controller;

    public VoyageInventoryForm() {
        super();
        add(rootPanel);
    }

    public VoyageInventoryForm(InventoryController controller) {
        this();
        this.controller = controller;

        inventoryNameCB.setModel(new InventoryComboBoxModel(controller));
    }

    @Override
    public VoyageInventory getRecord() {
        return new VoyageInventory(getVoyageId(), getInventoryId());
    }

    @Override
    public VoyageInventory getRawRecord() {
        VoyageInventory vi = new VoyageInventory();

        if (canGetVoyageId())
            vi.setVoyageId(getVoyageId());
        if (canGetInventoryId())
            vi.setInventoryId(getInventoryId());

        return vi;
    }

    @Override
    public void setRecord(VoyageInventory record) {
        voyageIdTxt.setText(String.valueOf(record.getVoyageId()));

        ComboBoxModel cb = new InventoryComboBoxModel(controller);
        cb.setSelectedItem(record.getInventoryId());
        inventoryNameCB.setModel(cb);
    }

    @Override
    public Boolean[] canGets() {
        return new Boolean[]{canGetVoyageId(), canGetInventoryId()};
    }

    public boolean canGetVoyageId() {
        try {
            if (getVoyageId() != -1) {
                return true;
            } else return false;
        } catch (Throwable t) {
            return false;
        }
    }

    public boolean canGetInventoryId() {
        try {
            if (getInventoryId() != -1) {
                return true;
            } else return false;
        } catch (Throwable t) {
            return false;
        }
    }

    public int getVoyageId() {
        return Integer.parseInt(voyageIdTxt.getText());
    }

    public int getInventoryId() {
        return getInventory().getId();
    }

    protected Inventory getInventory() {
        return (Inventory) inventoryNameCB.getSelectedItem();
    }
}
