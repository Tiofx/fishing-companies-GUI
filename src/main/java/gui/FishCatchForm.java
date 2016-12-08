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
    // TODO: 08/12/2016 change on voyageController
    private JTextField voyageIdTxt;

    private FishController fishController;
//    private VoyageController voyageController;

    public FishCatchForm() {
        super();
        add(rootPanel);
    }

    public FishCatchForm(FishController fishController) {
        this();
        this.fishController = fishController;

        fishNameCB.setModel(new FishComboBoxModel(fishController));
//        voyageIdCB.setModel(new VoyageComboBoxModel(voyageController));
    }

    @Override
    public FishCatch getRecord() {
        return new FishCatch(getVoyageId(), getFishId(), getWeight());
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
        // TODO: 08/12/2016 change on voyageIdCB
        voyageIdTxt.setText(String.valueOf(record.getVoyageId()));
//        ComboBoxModel voyage = new VoyageComboBoxModel(voyageController);
//        voyage.setSelectedItem(record.getVoyageId());
//        voyageIdCB.setModel(new VoyageComboBoxModel(voyageController));

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

    public boolean canGetVoyageId() {
        try {
            getVoyageId();
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    public int getVoyageId() {
//        return getVoyage().getId();
        return Integer.parseInt(voyageIdTxt.getText());
    }

//    protected Voyage getVoyage() {
//        return (Voyage) voyageIdCB.getSelectedItem();
//    }

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
