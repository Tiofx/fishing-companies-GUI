package gui;

import models.sql.Captain;
import unit.IUniversalForm;

import javax.swing.*;

public class CaptainForm extends JPanel implements IUniversalForm<Captain> {
    private JPanel rootPanel;
    private JTextField fioTxt;
    private JTextField experienceTxt;

    public CaptainForm() {
        super();
        add(rootPanel);
    }

    @Override
    public Captain getRecord() {
        return new Captain(getFio(), getExperience());
    }

    @Override
    public Captain getRawRecord() {
        Captain captain = new Captain();

        if (canGetFio())
            captain.setFio(getFio());
        if (canGetExperience())
            captain.setExperience(getExperience());

        return captain;
    }

    @Override
    public void setRecord(Captain record) {
        fioTxt.setText(record.getFio());
        experienceTxt.setText(Integer.toString(record.getExperience()));
    }

    @Override
    public Boolean[] canGets() {
        return new Boolean[]{canGetFio(), canGetExperience()};
    }

    public boolean canGetFio() {
        return getFio().split(" ").length == 2 && getFio().length() > 0;
    }

    public boolean canGetExperience() {
        try {
            getExperience();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String getFio() {
        return fioTxt.getText();
    }

    public int getExperience() {
        return Integer.parseUnsignedInt(experienceTxt.getText());
    }
}
