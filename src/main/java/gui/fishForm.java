package gui;

import models.Fish;

import javax.swing.*;
import java.util.Arrays;

public class FishForm extends JPanel {
    private JPanel rootPanel;
    private JTextField nameTxt;
    private JTextField priceTxt;

    public FishForm() {
        super();
        add(rootPanel);
    }

    public void setFish(Fish fish) {
        nameTxt.setText(fish.getName());
        priceTxt.setText(Integer.toString(fish.getPrice()));
    }

    public String getName() {
        return nameTxt.getText();
    }

    public Integer getPrice() {
        return Integer.parseUnsignedInt(priceTxt.getText());
    }

    public Fish getFish() {
        return new Fish(getName(), getPrice());
    }

    public Fish getRawFish() {
        Fish rawFish = new Fish();
        if (canGetName()) {
            rawFish.setName(getName());
        }
        if (canGetPrice()) {
            rawFish.setPrice(getPrice());
        }
        return rawFish;
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

    public boolean canGetFish() {
        return Arrays.stream(canGets()).reduce(true, (a, b) -> a && b);
    }

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