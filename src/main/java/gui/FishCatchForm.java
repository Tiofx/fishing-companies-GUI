package gui;

import controllers.FishController;
import models.gui.FishComboBoxModel;
import models.sql.Fish;
import models.sql.FishCatch;
import unit.IUniversalForm;

import javax.swing.*;

public class FishCatchForm extends JPanel implements IUniversalForm<FishCatch> {
    private JPanel rootPanel;
    private JComboBox fishNameCB;
    private JTextField weightTxt;

    private FishController fishController;

    public FishCatchForm() {
        super();
        add(rootPanel);
    }

    public FishCatchForm(FishController fishController) {
        this();
        this.fishController = fishController;

        fishNameCB.setModel(new FishComboBoxModel(fishController));
    }

    @Override
    public FishCatch getRecord() {
        return new FishCatch(getFishId(), getWeight());
    }

    @Override
    public FishCatch getRawRecord() {
        FishCatch raw = new FishCatch();

        if (canGetFishId())
            raw.setFishId(getFishId());
        if (canGetWeight())
            raw.setWeight(getWeight());

        return raw;
    }

    @Override
    public void setRecord(FishCatch record) {
        ComboBoxModel cb = new FishComboBoxModel(fishController);
        cb.setSelectedItem(record.getFishId());
        fishNameCB.setModel(cb);

        weightTxt.setText(String.valueOf(record.getWeight()));
    }

    @Override
    public Boolean[] canGets() {
        return new Boolean[]{canGetFishId(), canGetWeight()};
    }

    public boolean canGetFishId() {
        try {
            if (getFishId() != -1) {
                return true;
            } else {
                return false;
            }
        } catch (Throwable t) {
            return false;
        }
    }

    public boolean canGetWeight() {
        try {
            getWeight();
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    protected Fish getFish() {
        return (Fish) fishNameCB.getSelectedItem();
    }

    public int getFishId() {
        return getFish().getId();
    }

    public int getWeight() {
        return Integer.parseInt(weightTxt.getText());
    }
}
