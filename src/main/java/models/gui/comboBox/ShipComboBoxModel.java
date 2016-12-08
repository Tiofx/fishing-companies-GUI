package models.gui.comboBox;

import controllers.AbstractController;
import models.sql.Ship;

public class ShipComboBoxModel extends AbstractSqlComboBoxModel<Ship> {
    public ShipComboBoxModel(AbstractController<Ship> objects) {
        super(objects);
    }

    @Override
    public void findBy(String field) {
        objects.fullFind(new Boolean[]{false, true},
                new Ship(field));
    }

    @Override
    public void setSelectedItem(Object anItem) {
        if (anItem != null) {
            if (anItem instanceof Ship) {
                if ((selectedObject != null
                        && !selectedObject.equals(anItem)) ||
                        selectedObject == null) {

                    selectedObject = (Ship) anItem;
                    fireContentsChanged(this, -1, -1);
                }
            } else if (anItem instanceof Integer) {
                objects.fullFind(new Boolean[]{true, false},
                        new Ship((int) anItem, null));

                selectedObject = objects.getRecord(1);

                objects.reset();
                fireContentsChanged(this, -1, -1);
            } else {
                objects.fullFind(new Boolean[]{false, true},
                        new Ship((String) anItem));

                if (objects.getRecord(1) != null) {
                    selectedObject = objects.getRecord(1);

                    objects.reset();
                    fireContentsChanged(this, -1, -1);
                }
            }
        }
    }
}
