package models.gui.comboBox;

import controllers.AbstractController;
import models.sql.Captain;

public class CaptainComboBoxModel extends AbstractSqlComboBoxModel<Captain> {
    public CaptainComboBoxModel(AbstractController<Captain> objects) {
        super(objects);
    }

    @Override
    public void findBy(String field) {
        objects.fullFind(new Boolean[]{false, true, false},
                new Captain(field, -1));
    }

    @Override
    public void setSelectedItem(Object anItem) {
        if (anItem != null) {
            if (anItem instanceof Captain) {
                if ((selectedObject != null
                        && !selectedObject.equals(anItem)) ||
                        selectedObject == null) {

                    selectedObject = (Captain) anItem;
                    fireContentsChanged(this, -1, -1);
                }
            } else if (anItem instanceof Integer) {
                objects.fullFind(new Boolean[]{true, false, false},
                        new Captain((int) anItem, null, -1));

                selectedObject = objects.getRecord(1);

                objects.reset();
                fireContentsChanged(this, -1, -1);
            } else {
                objects.fullFind(new Boolean[]{false, true, false},
                        new Captain((String) anItem, -1));

                if (objects.getRecord(1) != null) {
                    selectedObject = objects.getRecord(1);

                    objects.reset();
                    fireContentsChanged(this, -1, -1);
                }
            }
        }
    }
}
