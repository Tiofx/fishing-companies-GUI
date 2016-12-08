package models.gui.comboBox;

import controllers.AbstractController;
import models.sql.Fish;

public class FishComboBoxModel extends AbstractSqlComboBoxModel<Fish> {
    public FishComboBoxModel(AbstractController<Fish> objects) {
        super(objects);
    }

    @Override
    public void findBy(String field) {
        objects.fullFind(new Boolean[]{false, true, false},
                new Fish(field, -1));
    }

    @Override
    public void setSelectedItem(Object anItem) {
        if (anItem != null) {
            if (anItem instanceof Fish) {
                if ((selectedObject != null
                        && !selectedObject.equals(anItem)) ||
                        selectedObject == null) {

                    selectedObject = (Fish) anItem;
                    fireContentsChanged(this, -1, -1);
                }
            } else if (anItem instanceof Integer) {
                objects.fullFind(new Boolean[]{true, false, false},
                        new Fish((int) anItem, null, 0));

                selectedObject = objects.getRecord(1);

                objects.reset();
                fireContentsChanged(this, -1, -1);
            } else {
                objects.fullFind(new Boolean[]{false, true, false},
                        new Fish((String) anItem, 0));

                if (objects.getRecord(1) != null) {
                    selectedObject = objects.getRecord(1);

                    objects.reset();
                    fireContentsChanged(this, -1, -1);
                }
            }
        }
    }
}
