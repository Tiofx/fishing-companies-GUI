package models.gui;

import controllers.FishRegionController;
import gui.FishRegionForm;
import models.sql.FishRegion;
import unit.Dialog;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.*;
import java.sql.SQLException;

public class FishRegionComboBoxModel extends AbstractListModel<FishRegion> implements ComboBoxModel<FishRegion> {
    protected FishRegionController objects;
    protected FishRegion selectedObject;

    public FishRegionComboBoxModel(JdbcRowSet objects) {
        this.objects = new FishRegionController(objects);
    }

    @Override
    public void setSelectedItem(Object anItem) {
        if (anItem != null && anItem instanceof Integer) {
            Boolean[] mask = {true, false, false};
            FishRegion find = new FishRegion();

            find.setId((int) anItem);
            objects.fullFind(mask, find);

            selectedObject = objects.getRecord(1);

            objects.reset();
            fireContentsChanged(this, -1, -1);
            return;
        }
        if (anItem != null && !(anItem instanceof FishRegion)) {

            int i = JOptionPane.showConfirmDialog(null,
                    "This record doesn't exist yet.\n Would you like to add this record?",
                    "add?",
                    JOptionPane.OK_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            if (i != JOptionPane.OK_OPTION) {
                return;
            }
            FishRegionForm inputPanel = new FishRegionForm();
            inputPanel.setRecord(new FishRegion((String) anItem, ""));

            Dialog.makeOperation("add", inputPanel, (fr -> objects.insert(fr)));
//            selectedObject =

            fireContentsChanged(this, -1, -1);
            return;
        }

        if ((selectedObject != null && !selectedObject.equals(anItem)) ||
                selectedObject == null && anItem != null) {
            selectedObject = (FishRegion) anItem;
            objects.edit(selectedObject);
            fireContentsChanged(this, -1, -1);
        }
    }

    @Override
    public Object getSelectedItem() {
        return selectedObject;
    }

    @Override
    public int getSize() {
        try {
            objects.getJrs().last();
            return objects.getJrs().getRow();
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public FishRegion getElementAt(int index) {
        if (index == -1)
            return null;
        try {
            return objects.getRecord(index + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
