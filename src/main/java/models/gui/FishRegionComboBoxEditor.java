package models.gui;

import models.sql.FishRegion;

import javax.swing.plaf.basic.BasicComboBoxEditor;

public class FishRegionComboBoxEditor extends BasicComboBoxEditor {
    @Override
    public void setItem(Object anObject) {
        if (anObject instanceof FishRegion) {
            super.setItem(((FishRegion) anObject).getPlaceName());
        } else super.setItem(anObject);
    }
}
