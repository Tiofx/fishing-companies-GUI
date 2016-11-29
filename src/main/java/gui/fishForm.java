package gui;

import models.sql.Fish;

import javax.swing.*;

public class FishForm extends JPanel implements IUniversalForm<Fish> {
    private JPanel rootPanel;
    private JTextField nameTxt;
    private JTextField priceTxt;

    public FishForm() {
        super();
        add(rootPanel);
    }

    @Override
    public Fish getRecord() {
        return new Fish(getName(), getPrice());
    }

    @Override
    public Fish getRawRecord() {
        Fish rawFish = new Fish();
        if (canGetName()) {
            rawFish.setName(getName());
        }
        if (canGetPrice()) {
            rawFish.setPrice(getPrice());
        }
        return rawFish;
    }


    @Override
    public void setRecord(Fish record) {
        nameTxt.setText(record.getName());
        priceTxt.setText(Integer.toString(record.getPrice()));
    }

    public String getName() {
        return nameTxt.getText();
    }

    public Integer getPrice() {
        return Integer.parseUnsignedInt(priceTxt.getText());
    }

    public boolean canGetName() {
        return getName().length() != 0;
    }

    public boolean canGetPrice() {
        try {
            Integer.parseUnsignedInt(priceTxt.getText());
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }


    @Override
    public Boolean[] canGets() {
        return new Boolean[]{canGetName(), canGetPrice()};
    }

    public String incorrectFields() {
        String s = "";

        if (!canGetName())
            s += " name";
        if (!canGetPrice())
            s += " price";

        return s;
    }
}