package gui;

import controllers.FishRegionController;
import models.gui.FishRegionComboBoxModel;
import models.sql.Quota;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.*;

public class QuotaForm extends JPanel implements IUniversalForm<Quota> {
    private JPanel rootPanel;
    private JTextField yearTxt;
    private JComboBox placeNameCB;

    private FishRegionController fishRegion;


    public QuotaForm(FishRegionController fishRegion) {
        super();
        add(rootPanel);
        this.fishRegion = fishRegion;
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
        yearTxt.setText(Integer.toString(record.getYear().getYear()));
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
        return (int) placeNameCB.getSelectedItem();
    }

    public java.sql.Date getYear() {
        return new java.sql.Date(getYearValue());
    }

    protected int getYearValue() {
        return Integer.parseInt(yearTxt.getText());
    }

}
