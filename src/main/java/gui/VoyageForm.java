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
import java.sql.Timestamp;

public class VoyageForm extends JPanel implements IUniversalForm<Voyage> {
    private JPanel rootPanel;
    private SqlComboBox captainNameCB;
    private SqlComboBox shipNameCB;
    private SqlComboBox fishSeasonCB;
    private SqlComboBox quotaCB;
    private DateTimePicker departureDatePicker;
    private DateTimePicker returnDatePicker;

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
                canGetQuotaId(), canGetDepartureDate(), canGetReturnDate(),
                isReturnDateGreaterThenDeparture()};
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

    public java.sql.Timestamp getDepartureDate() {
        return Timestamp.valueOf(departureDatePicker.getDateTime());
    }

    public java.sql.Timestamp getReturnDate() {
        return Timestamp.valueOf(returnDatePicker.getDateTime());
    }

    protected Captain getCaptain() {
        return (Captain) captainNameCB.getSelectedItem();
    }

    protected Ship getShip() {
        return (Ship) quotaCB.getSelectedItem();
    }

    protected FishSeason getFishSeason() {
        return (FishSeason) fishSeasonCB.getSelectedItem();
    }

    protected Quota getQuota() {
        return (Quota) quotaCB.getSelectedItem();
    }
}
