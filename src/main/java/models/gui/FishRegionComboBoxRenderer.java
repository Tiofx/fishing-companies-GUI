package models.gui;

import models.sql.FishRegion;

import javax.swing.*;
import java.awt.*;

public class FishRegionComboBoxRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list,
                                                  Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JComponent c = (JComponent) super.getListCellRendererComponent(list,
                value, index, isSelected, cellHasFocus);

        if (value instanceof FishRegion) {
            if (!isSelected) {
                setText(((FishRegion) value).getPlaceName());
            } else {
                list.setToolTipText(value.toString());
                setText(value.toString());
            }
            setToolTipText(value.toString());
        }

        return c;
    }
}
