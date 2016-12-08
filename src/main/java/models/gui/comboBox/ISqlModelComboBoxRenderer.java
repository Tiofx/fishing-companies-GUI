package models.gui.comboBox;

import models.sql.ISqlModel;
import unit.UniversalInfo;
import unit.info.IInfo;
import unit.info.TooltipText;

import javax.swing.*;
import java.awt.*;

public class ISqlModelComboBoxRenderer extends DefaultListCellRenderer {
    protected IInfo<ISqlModel> informator = new UniversalInfo();

    @Override
    public Component getListCellRendererComponent(JList list,
                                                  Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JComponent c = (JComponent) super.getListCellRendererComponent(list,
                value, index, isSelected, cellHasFocus);

        if (value instanceof ISqlModel) {
            ISqlModel model = (ISqlModel) value;
            if (!isSelected) {
                setText(informator.shortInfo(model));
            } else {
                list.setToolTipText(TooltipText.toTooltipText(informator.longInfo(model)));
                setText(informator.info(model));
            }
        }

        return c;
    }
}
