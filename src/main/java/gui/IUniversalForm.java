package gui;

import java.util.Arrays;

public interface IUniversalForm<T> {
    T getRecord();

    void setRecord(T record);

    Boolean[] canGets();

    default boolean canGetRecord() {
        return Arrays.stream(canGets()).reduce(true, (a, b) -> a && b);
    }
}
