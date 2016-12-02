package models.gui;

import gui.FishRegionForm;
import models.sql.FishRegion;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.*;
import java.sql.SQLException;

import static javax.swing.JOptionPane.showConfirmDialog;

public class FishRegionComboBoxModel extends AbstractListModel<FishRegion> implements ComboBoxModel<FishRegion> {
    protected JdbcRowSet objects;
    protected FishRegion selectedObject;

    public FishRegionComboBoxModel(JdbcRowSet objects) {
        this.objects = objects;
    }

    // TODO: 02/12/2016 refactoring!!!!!
    @Override
    public void setSelectedItem(Object anItem) {
        if (anItem != null && anItem instanceof Integer) {
            try {
                int pk = (int) anItem;
                objects.beforeFirst();
                while (objects.next()) {
                    if (objects.getInt(1) == pk) {
                        selectedObject = new FishRegion(objects.getInt(1), objects.getString(2), objects.getString(3));
                        break;
                    }
                }
                fireContentsChanged(this, -1, -1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }
        if (anItem != null && !(anItem instanceof FishRegion)) {
            // TODO: 02/12/2016 delete (this from MainForm add option)
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
            String nameOperation = "add";
            int result = showConfirmDialog(null,
                    inputPanel,
                    nameOperation + " form",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                if (inputPanel.canGetRecord()) {
                    FishRegion record = inputPanel.getRecord();

                    try {
                        objects.moveToInsertRow();

                        objects.updateString(2, record.getPlaceName());
                        objects.updateString(3, record.getDescription());

                        objects.insertRow();
                        objects.beforeFirst();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            fireContentsChanged(this, -1, -1);
            return;
        }

        if ((selectedObject != null && !selectedObject.equals(anItem)) ||
                selectedObject == null && anItem != null) {
            selectedObject = (FishRegion) anItem;
            try {
                objects.updateString(2, selectedObject.getPlaceName());
                objects.updateString(3, selectedObject.getDescription());
                objects.updateRow();
            } catch (SQLException e) {
                e.printStackTrace();
            }
//            JOptionPane.showConfirmDialog(null, "edit this?");
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
            objects.last();
            return objects.getRow();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public FishRegion getElementAt(int index) {
        if (index == -1)
            return null;
        try {
            objects.absolute(index + 1);
            FishRegion fishRegion = new FishRegion(objects.getInt(1), objects.getString(2), objects.getString(3));
            return fishRegion;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
