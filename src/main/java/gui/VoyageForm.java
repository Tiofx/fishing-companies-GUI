package gui;

import com.github.lgooddatepicker.datetimepicker.DateTimePicker;
import controllers.CaptainController;
import controllers.FishSeasonController;
import controllers.QuotaController;
import controllers.ShipController;
import gui.custom.SqlComboBox;
import models.gui.comboBox.CaptainComboBoxModel;
import models.gui.comboBox.FishSeasonComboBoxModel;
import models.gui.comboBox.QuotaComboBoxModel;
import models.gui.comboBox.ShipComboBoxModel;
import models.sql.*;
import unit.IUniversalForm;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;

public class VoyageForm extends JPanel implements IUniversalForm<Voyage> {
    private JPanel rootPanel;
    private SqlComboBox captainNameCB;
    private SqlComboBox shipNameCB;
    private SqlComboBox fishSeasonCB;
    private SqlComboBox quotaCB;
    private DateTimePicker departureDatePicker;
    private DateTimePicker returnDatePicker;
    protected JScrollPane fishCatchSP;
    protected JScrollPane inventorySP;
    protected JLabel l1;
    protected JLabel l2;

    private CaptainController captainController;
    private ShipController shipController;
    private FishSeasonController fishSeasonController;
    private QuotaController quotaController;

    public VoyageForm() {
        super();
        add(rootPanel);
    }

    public VoyageForm(CaptainController captainController, ShipController shipController,
                      FishSeasonController fishSeasonController, QuotaController quotaController) {
        this();
        this.captainController = captainController;
        this.shipController = shipController;
        this.fishSeasonController = fishSeasonController;
        this.quotaController = quotaController;

        captainNameCB.setModel(new CaptainComboBoxModel(captainController));
        shipNameCB.setModel(new ShipComboBoxModel(shipController));
        fishSeasonCB.setModel(new FishSeasonComboBoxModel(fishSeasonController));
        quotaCB.setModel(new QuotaComboBoxModel(quotaController));
    }

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(
                null,
                new VoyageForm(),
                "Form test",
                JOptionPane.PLAIN_MESSAGE);
    }

    @Override
    public Voyage getRecord() {
        return new Voyage(getCaptainId(), getShipId(), getFishSeasonId(),
                getQuotaId(), getDepartureDate(), getReturnDate());
    }

    @Override
    public Voyage getRawRecord() {
        Voyage record = new Voyage();

        if (canGetCaptainId())
            record.setCaptainId(getCaptainId());
        if (canGetShipId())
            record.setShipId(getShipId());
        if (canGetFishSeasonId())
            record.setFishSeasonId(getFishSeasonId());
        if (canGetQuotaId())
            record.setQuotaId(getQuotaId());
        if (canGetDepartureDate())
            record.setDepartureDate(getDepartureDate());
        if (canGetReturnDate())
            record.setReturnDate(getReturnDate());

        return record;
    }

    @Override
    public void setRecord(Voyage record) {
        ComboBoxModel models[] = {
                new CaptainComboBoxModel(captainController),
                new ShipComboBoxModel(shipController),
                new FishSeasonComboBoxModel(fishSeasonController),
                new QuotaComboBoxModel(quotaController)
        };

        models[0].setSelectedItem(record.getCaptainId());
        models[1].setSelectedItem(record.getShipId());
        models[2].setSelectedItem(record.getFishSeasonId());
        models[3].setSelectedItem(record.getQuotaId());

        captainNameCB.setModel(models[0]);
        shipNameCB.setModel(models[1]);
        fishSeasonCB.setModel(models[2]);
        quotaCB.setModel(models[3]);

        departureDatePicker.setDateTime(record.getDepartureDate().toLocalDateTime());
        returnDatePicker.setDateTime(record.getReturnDate().toLocalDateTime());
    }

    @Override
    public Boolean[] canGets() {
        return new Boolean[]{canGetCaptainId(), canGetShipId(), canGetFishSeasonId(),
                canGetQuotaId(), canGetDepartureDate(), canGetReturnDate()};
//                isReturnDateGreaterThenDeparture()};
    }

    public boolean canGetCaptainId() {
        try {
            if (getCaptainId() != -1) {
                return true;
            } else return false;
        } catch (Throwable t) {
            return false;
        }
    }

    public boolean canGetShipId() {
        try {
            if (getShipId() != -1) {
                return true;
            } else return false;
        } catch (Throwable t) {
            return false;
        }
    }

    public boolean canGetFishSeasonId() {
        try {
            if (getFishSeasonId() != -1) {
                return true;
            } else return false;
        } catch (Throwable t) {
            return false;
        }
    }

    public boolean canGetQuotaId() {
        try {
            if (getQuotaId() != -1) {
                return true;
            } else return false;
        } catch (Throwable t) {
            return false;
        }
    }

    public boolean canGetDepartureDate() {
        try {
            getDepartureDate();
            return true;
        } catch (Throwable t) {
            return false;
        }

    }

    public boolean canGetReturnDate() {
        try {
            getReturnDate();
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    public boolean isReturnDateGreaterThenDeparture() {
        return getReturnDate().compareTo(getDepartureDate()) > 0;
    }

    public int getCaptainId() {
        return getCaptain().getId();
    }

    public int getShipId() {
        return getShip().getId();
    }

    public int getFishSeasonId() {
        return getFishSeason().getId();
    }

    public int getQuotaId() {
        return getQuota().getId();
    }

    public Timestamp getDepartureDate() {
        return Timestamp.valueOf(departureDatePicker.getDateTime());
    }

    public Timestamp getReturnDate() {
        return Timestamp.valueOf(returnDatePicker.getDateTime());
    }

    protected Captain getCaptain() {
        return (Captain) captainNameCB.getSelectedItem();
    }

    protected Ship getShip() {
        return (Ship) shipNameCB.getSelectedItem();
    }

    protected FishSeason getFishSeason() {
        return (FishSeason) fishSeasonCB.getSelectedItem();
    }

    protected Quota getQuota() {
        return (Quota) quotaCB.getSelectedItem();
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
        rootPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(8, 2, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("return date:");
        rootPanel.add(label1, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        departureDatePicker = new DateTimePicker();
        rootPanel.add(departureDatePicker, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        returnDatePicker = new DateTimePicker();
        rootPanel.add(returnDatePicker, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("departure date:");
        rootPanel.add(label2, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("quota:");
        rootPanel.add(label3, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("fish season:");
        rootPanel.add(label4, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("ship:");
        rootPanel.add(label5, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("captain:");
        rootPanel.add(label6, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        captainNameCB = new SqlComboBox();
        rootPanel.add(captainNameCB, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        shipNameCB = new SqlComboBox();
        rootPanel.add(shipNameCB, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fishSeasonCB = new SqlComboBox();
        rootPanel.add(fishSeasonCB, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        quotaCB = new SqlComboBox();
        rootPanel.add(quotaCB, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        l1 = new JLabel();
        l1.setText("fish catch:");
        rootPanel.add(l1, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        l2 = new JLabel();
        l2.setText("inventory:");
        rootPanel.add(l2, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fishCatchSP = new JScrollPane();
        rootPanel.add(fishCatchSP, new com.intellij.uiDesigner.core.GridConstraints(6, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 200), new Dimension(-1, 200), new Dimension(-1, 200), 0, false));
        inventorySP = new JScrollPane();
        rootPanel.add(inventorySP, new com.intellij.uiDesigner.core.GridConstraints(7, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 200), new Dimension(-1, 200), new Dimension(-1, 200), 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}
