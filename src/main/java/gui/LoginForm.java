package gui;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import unit.Connection;
import unit.IUniversalForm;

import javax.swing.*;
import java.awt.*;

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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootPanel = new JPanel();
        rootPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("Login:");
        rootPanel.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Password:");
        rootPanel.add(label2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        passwordTxt = new JPasswordField();
        rootPanel.add(passwordTxt, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        loginTxt = new JTextField();
        rootPanel.add(loginTxt, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}