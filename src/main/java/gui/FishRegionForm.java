package gui;

import models.sql.FishRegion;
import unit.IUniversalForm;

import javax.swing.*;

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
}
