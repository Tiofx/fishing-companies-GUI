package models.gui.comboBox;

import controllers.AbstractController;
import models.sql.FishSeason;

public class FishSeasonComboBoxModel extends AbstractSqlComboBoxModel<FishSeason> {
    public FishSeasonComboBoxModel(AbstractController<FishSeason> objects) {
        super(objects);
    }

    @Override
    public void findBy(String field) {
        try {
            Integer.parseInt(field);
        } catch (Throwable t) {
            return;
        }
        objects.fullFind(new Boolean[]{false, true, false, false},
                new FishSeason(Integer.parseInt(field), null, null));

    }

    @Override
    public void setSelectedItem(Object anItem) {
        if (anItem != null) {
            if (anItem instanceof FishSeason) {
                if ((selectedObject != null
                        && !selectedObject.equals(anItem)) ||
                        selectedObject == null) {

                    selectedObject = (FishSeason) anItem;
                    fireContentsChanged(this, -1, -1);
                }
            } else if (anItem instanceof Integer) {
                objects.fullFind(new Boolean[]{true, false, false, false},
                        new FishSeason((int) anItem, 0, null, null));

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
