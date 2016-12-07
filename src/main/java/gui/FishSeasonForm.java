package gui;

import controllers.FishRegionController;
import gui.custom.SqlComboBox;
import models.gui.FishRegionComboBoxModel;
import models.sql.FishRegion;
import models.sql.FishSeason;
import org.jdesktop.swingx.JXDatePicker;
import unit.IUniversalForm;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.*;

public class FishSeasonForm extends JPanel implements IUniversalForm<FishSeason> {
    private JPanel rootPanel;
    private JXDatePicker beginDate;
    private JXDatePicker endDate;
    private SqlComboBox placeNameCB;

    private FishRegionController fishRegion;

    public FishSeasonForm(JdbcRowSet jrs) {
        this(new FishRegionController(jrs));
    }

    public FishSeasonForm(FishRegionController fishRegion) {
        super();
        add(rootPanel);
        this.fishRegion = fishRegion;

        placeNameCB.setModel(new FishRegionComboBoxModel(fishRegion));
    }

    public FishSeasonForm() {
        super();
        add(rootPanel);
    }

    public static void main(String[] args) {
        FishSeasonForm form = new FishSeasonForm();
        new Thread(() ->
                JOptionPane.showMessageDialog(
                        null,
                        form,
                        "Form test",
                        JOptionPane.PLAIN_MESSAGE)
        ).start();
    }

    @Override
    public FishSeason getRecord() {
        return new FishSeason(getFishRegionId(), getBeginDate(), getEndDate());
    }

    @Override
    public FishSeason getRawRecord() {
        FishSeason record = new FishSeason();

        if (canGetFishRegionId())
            record.setFishRegionId(getFishRegionId());
        if (canGetBeginDate())
            record.setBegin(getBeginDate());
        if (canGetEndDate())
            record.setEnd(getEndDate());
        return null;
    }

    @Override
    public void setRecord(FishSeason record) {
        ComboBoxModel cb = new FishRegionComboBoxModel(fishRegion);
        cb.setSelectedItem(record.getFishRegionId());
        placeNameCB.setModel(cb);
        beginDate.setDate(new java.util.Date(record.getBegin().getTime()));
        endDate.setDate(new java.util.Date(record.getEnd().getTime()));

    }

    @Override
    public Boolean[] canGets() {
        return new Boolean[]{canGetFishRegionId(), canGetBeginDate(), canGetEndDate()};
    }

    public boolean canGetFishRegionId() {
        try {
            if (getFishRegionId() != -1) {
                return true;
            } else return false;
        } catch (Throwable t) {
            return false;
        }
    }

    public boolean canGetBeginDate() {
        try {
            getBeginDate();
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    public boolean canGetEndDate() {
        try {
            getEndDate();
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    protected FishRegion getFishRegion() {
        return ((FishRegion) placeNameCB.getSelectedItem());
    }

    public int getFishRegionId() {
        return getFishRegion().getId();
    }

    public java.sql.Date getBeginDate() {
        return new java.sql.Date(beginDate.getDate().getTime());
    }

    public java.sql.Date getEndDate() {
        return new java.sql.Date(endDate.getDate().getTime());
    }
}
