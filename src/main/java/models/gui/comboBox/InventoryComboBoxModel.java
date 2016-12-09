package models.gui.comboBox;

import controllers.AbstractController;
import models.sql.Inventory;

public class InventoryComboBoxModel extends AbstractSqlComboBoxModel<Inventory> {
    public InventoryComboBoxModel(AbstractController<Inventory> objects) {
        super(objects);
    }

    @Override
    public void findBy(String field) {
        objects.fullFind(new Boolean[]{false, true, false, false},
                new Inventory(field, 0, 0));
    }

    @Override
    public void setSelectedItem(Object anItem) {
        if (anItem != null) {
            if (anItem instanceof Inventory) {
                if ((selectedObject != null
                        && !selectedObject.equals(anItem)) ||
                        selectedObject == null) {

                    selectedObject = (Inventory) anItem;
                    fireContentsChanged(this, -1, -1);
                }
            } else if (anItem instanceof Integer) {
                objects.fullFind(new Boolean[]{true, false, false, false},
                        new Inventory((int) anItem, null, 0, 0));

                selectedObject = objects.getRecord(1);

                objects.reset();
                fireContentsChanged(this, -1, -1);
            } else {
                findBy((String) anItem);

                if (objects.getRecord(1) != null) {
                    selectedObject = objects.getRecord(1);

                    objects.reset();
                    fireContentsChanged(this, -1, -1);
                }
            }
        }
    }
}
