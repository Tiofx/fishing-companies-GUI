package controllers;

import models.sql.FishCatch;
import unit.Connection;
import unit.IUniversalForm;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FishCatchControllerForVoyageForm extends FishCatchController {
    protected final int voyageId;

    public FishCatchControllerForVoyageForm() {
        voyageId = -1;
    }

    public FishCatchControllerForVoyageForm(Connection connection, String tableName, int voyageId) {
        super(connection, tableName);
        this.voyageId = voyageId;
    }

    public FishCatchControllerForVoyageForm(FishCatchController controller, int voyageId) {
        this.voyageId = voyageId;
        setJrs(controller.getJrs());
    }

    @Override
    protected JTable createView() {
        reset();
        final AbstractController thisController = this;
        final TableModel model = tableModel;
        final JTable view = new JTable(model);
        view.setAutoCreateRowSorter(false);
        view.setShowGrid(true);
        view.setGridColor(Color.GRAY);

        view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.getKeyCode() == KeyEvent.VK_DELETE && view.hasFocus() && view.getSelectedRow() != -1) {
                    while (thisController.deleteSelectedInTable()) ;
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
                        IUniversalForm inputForm = Connection.formFactory.getInstance(thisController.getClass());
                        if (view.getSelectedRow() < view.getModel().getRowCount() - 1) {
                            inputForm.setRecord(thisController.getRecordSelectedInTable());
                            unit.Dialog.makeOperation("edit", inputForm, a -> thisController.editSelectedInTable(a));
                        } else {
                            inputForm.setRecord(new FishCatch(0, voyageId, 0, 0));
                            unit.Dialog.makeOperation("add", inputForm, a -> thisController.insert(a));
                        }
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
        return fullFind(new Boolean[]{false, true, false, false}, new FishCatch(voyageId, -1, -1));
    }
}
