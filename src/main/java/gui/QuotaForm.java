package gui;

import controllers.FishRegionController;
import models.gui.FishRegionComboBoxModel;
import models.sql.FishRegion;
import models.sql.Quota;

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

            if (value instanceof FishRegion && !isSelected) {
                setText(((FishRegion) value).getPlaceName());
            }

            if (value instanceof FishRegion && isSelected) {
//                list.setToolTipText(value.toString());
//                setText(((FishRegion) value).getPlaceName());

                setText(value.toString());
            } else if (!(value instanceof FishRegion) && isSelected) {
                list.setToolTipText("none :(");
            }
            return c;
        }
    }


    public QuotaForm(FishRegionController fishRegion) {
        super();
        add(rootPanel);
        this.fishRegion = fishRegion;

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
        this(new FishRegionController(jrs, null, null));
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
        yearTxt.setText(Integer.toString(record.getYear().getYear() + 1900));
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
            int year = getYearValue();
            if (year > 2000 && year < 2100) {
                getYear();
                return true;
            } else return false;
        } catch (Throwable e) {
            return false;
        }
    }

    public int getFishRegionId() {
        return ((FishRegion) placeNameCB.getSelectedItem()).getId();
    }

    public java.sql.Date getYear() {
        return new java.sql.Date(getYearValue() - 1900, 0, 0);
    }

    protected int getYearValue() {
        return Integer.parseInt(yearTxt.getText());
    }

}
