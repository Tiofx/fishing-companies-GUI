package models.gui;

import models.sql.ISqlModel;
import unit.UniversalInfo;
import unit.info.IInfo;

import javax.swing.plaf.basic.BasicComboBoxEditor;

public class ISqlModelEditor extends BasicComboBoxEditor {
    protected IInfo<ISqlModel> informator = new UniversalInfo();

    @Override
    public void setItem(Object anObject) {
        super.setItem(anObject);
        if (anObject != null) {
            editor.setText(informator.shortInfo((ISqlModel) anObject));
        }
    }
}
