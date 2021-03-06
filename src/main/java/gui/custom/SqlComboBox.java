package gui.custom;

import models.gui.comboBox.AbstractSqlComboBoxModel;
import models.gui.comboBox.ISqlModelComboBoxRenderer;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboPopup;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SqlComboBox<E> extends JComboBox<E> {
    public SqlComboBox() {
        super();

        setEditable(true);
        setRenderer(new ISqlModelComboBoxRenderer());

        getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (getEditor().getItem() != null
                        && !getEditor().getItem().toString().equals("")) {
                    ((AbstractSqlComboBoxModel) getModel()).findBy(getEditor().getItem().toString());
                    if (((AbstractSqlComboBoxModel) getModel()).getSize() <= 2) {
                        hidePopup();
                    }
                } else {
                    ((AbstractSqlComboBoxModel) getModel()).getObjects().reset();
                }
                ((BasicComboPopup) getAccessibleContext().getAccessibleChild(0)).pack();
                showPopup();
            }
        });
    }

    public SqlComboBox(AbstractSqlComboBoxModel model) {
        this();
        setModel(model);
    }
}