package controllers;

import models.gui.BaseTableModel;
import models.gui.TableModelWithRef;
import unit.Connection;

import javax.sql.rowset.JdbcRowSet;

public abstract class AbstractControllerWithRef<T> extends AbstractController<T> {
    protected JdbcRowSet reference;

    public AbstractControllerWithRef() {
    }

    public AbstractControllerWithRef(Connection connection, String tableName) {
        super(connection, tableName);
    }

    public AbstractControllerWithRef(Connection connection, String tableName, String refTableName) {
        this.reference = connection.getJRS(refTableName);
        setJrs(connection.getJRS(tableName));
    }

    public AbstractControllerWithRef(JdbcRowSet jrs) {
        super(jrs);
    }

    @Override
    protected BaseTableModel createTableModel(JdbcRowSet jrs) {
        return new TableModelWithRef(jrs, reference);
    }
}