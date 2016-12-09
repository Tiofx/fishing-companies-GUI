package controllers;

import models.sql.Inventory;
import models.sql.VoyageInventory;
import unit.Connection;
import unit.IUniversalForm;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class InventoryControllerForVoyageForm extends InventoryController {
    protected final int voyageId;
    protected VoyageInventoryController controller;

    public InventoryControllerForVoyageForm() {
        voyageId = -1;
    }

    public InventoryControllerForVoyageForm(Connection connection, String tableName, String secondName, int voyageId) {
        super(connection, tableName);
        controller = new VoyageInventoryController(connection.getJRS(secondName));
        this.voyageId = voyageId;
    }

    public InventoryControllerForVoyageForm(InventoryController controller2,
                                            VoyageInventoryController controller3, int voyageId) {
        this.controller = controller3;
        this.voyageId = voyageId;
        setJrs(controller2.getJrs());
    }

    @Override
    protected JTable createView() {
        reset();
        final AbstractController controller = this.controller;
        final AbstractController thisController = this;
        final TableModel model = tableModel;
        final JTable view = new JTable(model);
        view.setAutoCreateRowSorter(false);
        view.setShowGrid(true);
        view.setGridColor(Color.GRAY);

        view.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete");
        view.getActionMap().put("delete", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.hasFocus() && view.getSelectedRow() != -1) {
                    controller.fullFind(new Boolean[]{true, true},
                            new VoyageInventory(voyageId,
                                    ((Inventory) thisController.getRecordSelectedInTable()).getId()
                            ));
                    while (controller.delete(1)) ;
                    controller.reset();
                    reset();
                    thisController.update();
                }
            }
        });
        view.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (view.getSelectedRow() != -1) {
                        if (view.getSelectedRow() < view.getModel().getRowCount() - 1) {
                            IUniversalForm inputForm = Connection.formFactory.getInstance(thisController.getClass());
                            inputForm.setRecord(thisController.getRecordSelectedInTable());
                            unit.Dialog.makeOperation("edit", inputForm, a -> thisController.editSelectedInTable(a));
                        } else {
                            IUniversalForm inputForm = Connection.formFactory.getInstance(controller.getClass());
                            inputForm.setRecord(new VoyageInventory(voyageId, 0));
                            unit.Dialog.makeOperation("add", inputForm, a -> controller.insert(a));
                        }
                        reset();
                        thisController.update();
                    }
                }
                super.mouseClicked(e);
            }
        });

        return view;
    }

    @Override
    public boolean reset() {
        try {
            jrs.setCommand("select * from " + getTableName() + " where id IN "
                    + "(select inventoryId from voyage_inventory where voyageId = "
                    + String.valueOf(voyageId) + ")");
            jrs.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
