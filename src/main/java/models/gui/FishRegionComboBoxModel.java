package models.gui;

import models.sql.FishRegion;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.sql.SQLException;

public class FishRegionComboBoxModel implements ComboBoxModel<FishRegion> {
    protected JdbcRowSet objects;
    protected FishRegion selectedObject;

    public FishRegionComboBoxModel(JdbcRowSet objects) {
        this.objects = objects;
    }

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
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }
        if ((selectedObject != null && !selectedObject.equals(anItem)) ||
                selectedObject == null && anItem != null) {
            selectedObject = (FishRegion) anItem;
        }
    }

    @Override
    public Object getSelectedItem() {
        return selectedObject.getId();
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
        try {
            objects.absolute(index + 1);
            FishRegion fishRegion = new FishRegion(objects.getInt(1), objects.getString(2), objects.getString(3));
            return fishRegion;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void addListDataListener(ListDataListener l) {

    }

    @Override
    public void removeListDataListener(ListDataListener l) {

    }
}
