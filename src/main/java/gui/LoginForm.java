package gui;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import unit.Connection;
import unit.IUniversalForm;

import javax.swing.*;

public class LoginForm extends JPanel implements IUniversalForm<Connection> {
    private JPanel rootPanel;
    private JPasswordField passwordTxt;
    private JTextField loginTxt;

    public LoginForm() {
        super();
        add(rootPanel);
    }

    @Override
    public Connection getRecord() {
        return new Connection(getLogin(), getPassword());
    }

    @Override
    public Connection getRawRecord() {
        throw new NotImplementedException();
    }

    @Override
    public void setRecord(Connection record) {
        throw new NotImplementedException();
    }

    @Override
    public Boolean[] canGets() {
        return new Boolean[]{canGetLogin(), canGetPassword(), isCanConnect()};
    }

    public boolean canGetLogin() {
        return getLogin() != null;
    }

    public boolean canGetPassword() {
        return getPassword() != null;
    }

    public boolean isCanConnect() {
        Connection connection = new Connection(getLogin(), getPassword());
        connection.startConnection();
        if (connection.getConnection() != null) {
            connection.close();
            return true;
        } else return false;
    }

    public String getLogin() {
        return loginTxt.getText();
    }

    public String getPassword() {
        return passwordTxt.getText();
    }
}
