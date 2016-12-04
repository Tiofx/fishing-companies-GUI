package gui;

import controllers.FishRegionController;
import models.gui.FishRegionComboBoxModel;
import models.sql.FishRegion;
import models.sql.Quota;
import unit.*;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showConfirmDialog;

public class QuotaForm extends JPanel implements IUniversalForm<Quota> {
    private JPanel rootPanel;
    private JTextField yearTxt;
    private JComboBox placeNameCB;
    private JButton editButton;

    private FishRegionController fishRegion;

    private class ComboRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list,
                                                      Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JComponent c = (JComponent) super.getListCellRendererComponent(list,
                    value, index, isSelected, cellHasFocus);

            if (value instanceof FishRegion) {
                if (isSelected) {
                    setText(((FishRegion) value).getPlaceName());
                } else {
                    list.setToolTipText(value.toString());
                    setText(value.toString());
                }
            }

            return c;
        }
    }


    public QuotaForm(FishRegionController fishRegion) {
        super();
        add(rootPanel);
        this.fishRegion = fishRegion;

        ComboBoxModel cb = new FishRegionComboBoxModel(fishRegion.getJrs());
        placeNameCB.setModel(cb);

        placeNameCB.setRenderer(new ComboRenderer());
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FishRegion fr = (FishRegion) placeNameCB.getSelectedItem();

                FishRegionForm inputPanel = new FishRegionForm();
                inputPanel.setRecord(fr);
                String nameOperation = "add";
                int result = showConfirmDialog(null,
                        inputPanel,
                        nameOperation + " form",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    if (inputPanel.canGetRecord()) {
                        FishRegion record = inputPanel.getRecord();

                        placeNameCB.setSelectedItem(record);
                    }
                }

            }
        });


    }

    public QuotaForm(JdbcRowSet jrs) {
        this(new FishRegionController(jrs));
    }

    @Override
    public Quota getRecord() {
        return new Quota(getFishRegionId(), getYear());
    }

    @Override
    public Quota getRawRecord() {
        Quota quota = new Quota();

        if (canGetFishRegionId())
            quota.setFishRegionId(getFishRegionId());
        if (canGetYear())
            quota.setYear(getYear());

        return quota;
    }

    @Override
    public void setRecord(Quota record) {
        ComboBoxModel cb = new FishRegionComboBoxModel(fishRegion.getJrs());
        cb.setSelectedItem(record.getFishRegionId());
        placeNameCB.setModel(cb);
        yearTxt.setText(Integer.toString(record.getYear()));
    }

    @Override
    public Boolean[] canGets() {
        return new Boolean[]{canGetFishRegionId(), canGetYear()};
    }

    public boolean canGetFishRegionId() {
        return true;
    }

    public boolean canGetYear() {
        try {
            int year = getYear();
            if (year > 2000 && year < 2100) {
                return true;
            } else return false;
        } catch (Throwable e) {
            return false;
        }
    }

    public int getFishRegionId() {
        return ((FishRegion) placeNameCB.getSelectedItem()).getId();
    }


    protected short getYear() {
        return Short.parseShort(yearTxt.getText());
    }

}
