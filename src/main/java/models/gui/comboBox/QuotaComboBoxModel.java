package models.gui.comboBox;

import controllers.AbstractController;
import models.sql.Quota;

public class QuotaComboBoxModel extends AbstractSqlComboBoxModel<Quota> {
    public QuotaComboBoxModel(AbstractController<Quota> objects) {
        super(objects);
    }

    @Override
    public void findBy(String field) {
        try {
            Short.parseShort(field);
        } catch (Throwable t) {
            return;
        }
        objects.fullFind(new Boolean[]{false, false, true},
                new Quota(-1, -1, Short.parseShort(field)));
    }

    @Override
    public void setSelectedItem(Object anItem) {
        if (anItem != null) {
            if (anItem instanceof Quota) {
                if ((selectedObject != null
                        && !selectedObject.equals(anItem)) ||
                        selectedObject == null) {

                    selectedObject = (Quota) anItem;
                    fireContentsChanged(this, -1, -1);
                }
            } else if (anItem instanceof Integer) {
                objects.fullFind(new Boolean[]{true, false, false},
                        new Quota((int) anItem, -1, (short) -1));

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
