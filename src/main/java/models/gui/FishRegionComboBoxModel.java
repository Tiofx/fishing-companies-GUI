package models.gui;

import controllers.AbstractController;
import gui.FishRegionForm;
import models.sql.FishRegion;
import unit.Dialog;

import javax.swing.*;

public class FishRegionComboBoxModel extends AbstractSqlComboBoxModel<FishRegion> {


    public FishRegionComboBoxModel(AbstractController<FishRegion> objects) {
        super(objects);
    }

    @Override
    public void findBy(String field) {
        objects.fullFind(new Boolean[]{false, true, false},
                new FishRegion(field, null));
    }

    @Override
    public void setSelectedItem(Object anItem) {
        if (anItem != null) {
            if (anItem instanceof FishRegion) {
                if ((selectedObject != null
                        && !selectedObject.equals(anItem)) ||
                        selectedObject == null) {

                    selectedObject = (FishRegion) anItem;
                    fireContentsChanged(this, -1, -1);
                }
            } else if (anItem instanceof Integer) {
                objects.fullFind(new Boolean[]{true, false, false},
                        new FishRegion((int) anItem, null, null));

                selectedObject = objects.getRecord(1);

                objects.reset();
                fireContentsChanged(this, -1, -1);
            } else {
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
                            new FishRegion((String) anItem, null));
                    selectedObject = objects.getRecord(1);

                    objects.reset();
                    fireContentsChanged(this, -1, -1);
                }
            }
        }
    }

}
