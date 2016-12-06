package gui;

import controllers.FishRegionController;
import models.gui.FishRegionComboBoxModel;
import models.gui.FishRegionComboBoxRenderer;
import models.sql.FishRegion;
import models.sql.FishSeason;
import org.jdesktop.swingx.JXDatePicker;
import unit.IUniversalForm;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboPopup;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FishSeasonForm extends JPanel implements IUniversalForm<FishSeason> {
    private JPanel rootPanel;
    private JComboBox placeNameCB;
    private JXDatePicker beginDate;
    private JXDatePicker endDate;

    private FishRegionController fishRegion;

    public FishSeasonForm(FishRegionController fishRegion) {
        super();
        add(rootPanel);
        this.fishRegion = fishRegion;

        ComboBoxModel cb = new FishRegionComboBoxModel(fishRegion.getJrs());
        placeNameCB.setModel(cb);
        placeNameCB.setRenderer(new FishRegionComboBoxRenderer());

        placeNameCB.getEditor().getEditorComponent().repaint();
        placeNameCB.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
//                placeNameCB.hidePopup();
                if (cb instanceof FishRegionComboBoxModel) {
                    if ((String) placeNameCB.getEditor().getItem() != null) {
                        ((FishRegionComboBoxModel) cb).getObjects().fullFind(new Boolean[]{false, true, false},
                                new FishRegion((String) placeNameCB.getEditor().getItem(), null));
                    } else {
                        ((FishRegionComboBoxModel) cb).getObjects().reset();
                    }
                    ((BasicComboPopup) placeNameCB.getAccessibleContext().getAccessibleChild(0)).pack();
                    placeNameCB.showPopup();
                }
            }
        });
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
        ComboBoxModel cb = new FishRegionComboBoxModel(fishRegion.getJrs());
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
        return placeNameCB.getSelectedIndex() != -1;
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

    public int getFishRegionId() {
        return ((FishRegion) placeNameCB.getSelectedItem()).getId();
    }

    public java.sql.Date getBeginDate() {
        return new java.sql.Date(beginDate.getDate().getTime());
    }

    public java.sql.Date getEndDate() {
        return new java.sql.Date(endDate.getDate().getTime());
    }
}
