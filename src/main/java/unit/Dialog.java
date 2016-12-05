package unit;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public final class Dialog {
    private static Object object;

    public static <T> T makeOperation(Component parentComponent, String nameOperation,
                                      IUniversalForm<T> inputPanel, Function<T, Boolean> func) {

        while (!tryOperation(parentComponent, nameOperation, inputPanel, func)) ;
        return (T) object;
    }

    public static <T> T makeOperation(String nameOperation,
                                      IUniversalForm<T> inputPanel, Function<T, Boolean> func) {

        while (!tryOperation(nameOperation, inputPanel, func)) ;
        return (T) object;
    }

    public static <T> boolean tryOperation(Component parentComponent, String nameOperation,
                                           IUniversalForm<T> inputPanel, Function<T, Boolean> func) {

        int result = JOptionPane.showConfirmDialog(
                parentComponent,
                inputPanel,
                nameOperation + " form",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (inputPanel.canGetRecord()) {
                T record = inputPanel.getRecord();
                object = record;

                if (!func.apply(record)) {
                    JOptionPane.showMessageDialog(
                            parentComponent,
                            "This record wasn't " + nameOperation + "ed!!!",
                            nameOperation + " problem",
                            JOptionPane.WARNING_MESSAGE);
                }
                return true;
            } else {
                JOptionPane.showMessageDialog(
                        parentComponent,
                        "You have error in fields: ",// + inputPanel.incorrectFields(),
                        "Incorrect data",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            return true;
        }
    }

    public static <T> boolean tryOperation(String nameOperation,
                                           IUniversalForm<T> inputPanel, Function<T, Boolean> func) {

        return tryOperation(null, nameOperation, inputPanel, func);
    }

}
