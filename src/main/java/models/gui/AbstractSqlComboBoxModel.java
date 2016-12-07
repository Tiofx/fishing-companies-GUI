package models.gui;

import controllers.AbstractController;

import javax.swing.*;
import java.sql.SQLException;

public abstract class AbstractSqlComboBoxModel<T> extends AbstractListModel<T> implements ComboBoxModel<T> {
    protected AbstractController<T> objects;
    protected T selectedObject;


    public AbstractSqlComboBoxModel(AbstractController<T> objects) {
        this.objects = objects;
    }

    public abstract void findBy(String field);

    public AbstractController<T> getObjects() {
        return objects;
    }

    @Override
    public Object getSelectedItem() {
        return selectedObject;
    }

    @Override
    public int getSize() {
        try {
            objects.getJrs().last();
            return objects.getJrs().getRow();
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public T getElementAt(int index) {
        if (index == -1)
            return null;
        try {
            return objects.getRecord(index + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

