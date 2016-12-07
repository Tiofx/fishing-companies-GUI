package models.gui;

import models.sql.FishRegion;

import javax.swing.*;
import java.awt.*;

@Deprecated
public class FishRegionComboBoxRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list,
                                                  Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JComponent c = (JComponent) super.getListCellRendererComponent(list,
                value, index, isSelected, cellHasFocus);

        if (value instanceof FishRegion) {
            if (!isSelected) {
                setText(value.toString());
            } else {
                list.setToolTipText(((FishRegion) value).longInfo());
                setText(((FishRegion) value).longInfoOneLine());
            }
            setToolTipText(((FishRegion) value).longInfo());
        }

        return c;
    }
}
