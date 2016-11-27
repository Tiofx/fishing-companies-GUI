package gui;

import models.Fish;

import javax.swing.*;

public class FishForm extends JPanel {
    private JPanel rootPanel;
    private JTextField nameTxt;
    private JTextField priceTxt;

    public FishForm() {
        super();
        add(rootPanel);
    }

    public Fish getFish() {
        return new Fish(nameTxt.getText(),
                Integer.parseInt(priceTxt.getText()));
    }
}
