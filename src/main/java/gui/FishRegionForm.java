package gui;

import models.sql.FishRegion;
import unit.IUniversalForm;

import javax.swing.*;
import java.awt.*;

public class FishRegionForm extends JPanel implements IUniversalForm<FishRegion> {
    private JPanel rootPanel;
    private JTextField placeNameTxt;
    private JTextField descriptionTxt;

    public FishRegionForm() {
        super();
        add(rootPanel);
    }

    @Override
    public FishRegion getRecord() {
        return new FishRegion(getPlaceName(), getDescription());
    }

    @Override
    public FishRegion getRawRecord() {
        FishRegion fishRegion = new FishRegion();

        if (canGetPlaceName())
            fishRegion.setPlaceName(getPlaceName());
        if (canGetDescription())
            fishRegion.setDescription(getDescription());

        return fishRegion;
    }

    @Override
    public void setRecord(FishRegion record) {
        placeNameTxt.setText(record.getPlaceName());
        descriptionTxt.setText(record.getDescription());
    }

    @Override
    public Boolean[] canGets() {
        return new Boolean[]{canGetPlaceName(), canGetDescription()};
    }

    public boolean canGetPlaceName() {
        return getPlaceName().length() > 0;
    }

    public boolean canGetDescription() {
        return getDescription().length() > 0;
    }

    public String getPlaceName() {
        return placeNameTxt.getText();
    }

    public String getDescription() {
        return descriptionTxt.getText();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootPanel = new JPanel();
        rootPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        placeNameTxt = new JTextField();
        rootPanel.add(placeNameTxt, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        descriptionTxt = new JTextField();
        rootPanel.add(descriptionTxt, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("place name");
        rootPanel.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("description");
        rootPanel.add(label2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}
