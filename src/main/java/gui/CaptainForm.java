package gui;

import models.sql.Captain;
import unit.IUniversalForm;

import javax.swing.*;
import java.awt.*;

public class CaptainForm extends JPanel implements IUniversalForm<Captain> {
    private JPanel rootPanel;
    private JTextField fioTxt;
    private JTextField experienceTxt;

    public CaptainForm() {
        super();
        add(rootPanel);
    }

    @Override
    public Captain getRecord() {
        return new Captain(getFio(), getExperience());
    }

    @Override
    public Captain getRawRecord() {
        Captain captain = new Captain();

        if (canGetFio())
            captain.setFio(getFio());
        if (canGetExperience())
            captain.setExperience(getExperience());

        return captain;
    }

    @Override
    public void setRecord(Captain record) {
        fioTxt.setText(record.getFio());
        experienceTxt.setText(Integer.toString(record.getExperience()));
    }

    @Override
    public Boolean[] canGets() {
        return new Boolean[]{canGetFio(), canGetExperience()};
    }

    public boolean canGetFio() {
        return getFio().split(" ").length == 3 && getFio().length() > 0;
    }

    public boolean canGetExperience() {
        try {
            getExperience();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String getFio() {
        return fioTxt.getText();
    }

    public int getExperience() {
        return Integer.parseUnsignedInt(experienceTxt.getText());
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
        final JLabel label1 = new JLabel();
        label1.setText("fio");
        rootPanel.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fioTxt = new JTextField();
        rootPanel.add(fioTxt, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("experience");
        rootPanel.add(label2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        experienceTxt = new JTextField();
        rootPanel.add(experienceTxt, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}
