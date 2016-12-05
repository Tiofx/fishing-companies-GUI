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
        if (anItem != null) {
            if (anItem instanceof FishRegion) {
                if ((selectedObject != null
                        && !selectedObject.equals(anItem)) ||
                        selectedObject == null) {

                    selectedObject = (FishRegion) anItem;
                    objects.edit(selectedObject);
                    fireContentsChanged(this, -1, -1);
                }
            } else if (anItem instanceof Integer) {
//                Boolean[] mask = {true, false, false};
//                FishRegion find = new FishRegion();
//
//                find.setId((int) anItem);
//                objects.fullFind(mask, find);
                objects.fullFind(new Boolean[]{true, false, false},
                        new FishRegion((int) anItem, null, null));

                selectedObject = objects.getRecord(1);

                objects.reset();
                fireContentsChanged(this, -1, -1);
            } else {
//                Boolean[] mask = {false, true, false};
//                FishRegion find = new FishRegion();
//
//                find.setPlaceName(((String) anItem));
//                objects.fullFind(mask, find);
                objects.fullFind(new Boolean[]{false, true, false},
                        new FishRegion((String) anItem, null));

                if (objects.getRecord(1) != null) {
                    selectedObject = objects.getRecord(1);

                    objects.reset();
                    fireContentsChanged(this, -1, -1);
                } else {
                    int i = JOptionPane.showConfirmDialog(null,
                            "This record doesn't exist yet.\n " +
                                    "Would you like to add this record?",
                            "add?",
                            JOptionPane.OK_OPTION,
                            JOptionPane.PLAIN_MESSAGE);

                    if (i != JOptionPane.OK_OPTION) {
                        return;
                    }
                    FishRegionForm inputPanel = new FishRegionForm();
                    inputPanel.setRecord(new FishRegion((String) anItem, ""));
                    Dialog.makeOperation("add", inputPanel, (fr -> objects.insert(fr)));

                    objects.fullFind(new Boolean[]{false, true, false},
                            new FishRegion((String) anItem, ""));
                    selectedObject = objects.getRecord(1);

                    objects.reset();
                    fireContentsChanged(this, -1, -1);
                }
            }
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
