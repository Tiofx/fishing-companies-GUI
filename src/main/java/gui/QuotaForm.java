package gui;

import controllers.FishRegionController;
import models.gui.FishRegionComboBoxEditor;
import models.gui.FishRegionComboBoxModel;
import models.gui.FishRegionComboBoxRenderer;
import models.sql.FishRegion;
import models.sql.Quota;
import unit.IUniversalForm;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class QuotaForm extends JPanel implements IUniversalForm<Quota> {
    private JPanel rootPanel;
    private JTextField yearTxt;
    private JComboBox placeNameCB;

    private FishRegionController fishRegion;

    public QuotaForm(FishRegionController fishRegion) {
        super();
        add(rootPanel);
        this.fishRegion = fishRegion;

        ComboBoxModel cb = new FishRegionComboBoxModel(fishRegion.getJrs());
        placeNameCB.setModel(cb);
        placeNameCB.setEditor(new FishRegionComboBoxEditor());
        placeNameCB.setRenderer(new FishRegionComboBoxRenderer());

        placeNameCB.getEditor().getEditorComponent().repaint();

        QuotaForm root = this;
        placeNameCB.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                placeNameCB.hidePopup();
                if (cb instanceof FishRegionComboBoxModel) {
                    if ((String) placeNameCB.getEditor().getItem() != null) {
                        ((FishRegionComboBoxModel) cb).getObjects().fullFind(new Boolean[]{false, true, false},
                                new FishRegion((String) placeNameCB.getEditor().getItem(), null));
                    } else {
                        ((FishRegionComboBoxModel) cb).getObjects().reset();
                    }
                    root.revalidate();
                    placeNameCB.showPopup();
                }
            }
        });
    }

    public QuotaForm(JdbcRowSet jrs) {
        this(new FishRegionController(jrs));
    }

    @Override
    public Quota getRecord() {
        return new Quota(getFishRegionId(), getYear());
    }

    @Override
    public Quota getRawRecord() {
        Quota quota = new Quota();

        if (canGetFishRegionId())
            quota.setFishRegionId(getFishRegionId());
        if (canGetYear())
            quota.setYear(getYear());

        return quota;
    }

    @Override
    public void setRecord(Quota record) {
        ComboBoxModel cb = new FishRegionComboBoxModel(fishRegion.getJrs());
        cb.setSelectedItem(record.getFishRegionId());
        placeNameCB.setModel(cb);
        yearTxt.setText(Integer.toString(record.getYear()));
    }

    @Override
    public Boolean[] canGets() {
        return new Boolean[]{canGetFishRegionId(), canGetYear()};
    }

    public boolean canGetFishRegionId() {
        return true;
    }

    public boolean canGetYear() {
        try {
            int year = getYear();
            if (year > 2000 && year < 2100) {
                return true;
            } else return false;
        } catch (Throwable e) {
            return false;
        }
    }

    public int getFishRegionId() {
        return ((FishRegion) placeNameCB.getSelectedItem()).getId();
    }


    protected short getYear() {
        return Short.parseShort(yearTxt.getText());
    }

}
