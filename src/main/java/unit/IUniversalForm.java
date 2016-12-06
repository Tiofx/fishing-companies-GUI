package unit;

import java.util.Arrays;

public interface IUniversalForm<T> {
    //public interface IUniversalForm<T extends ISqlModel> {
    T getRecord();

    T getRawRecord();

    void setRecord(T record);

    Boolean[] canGets();

    default boolean canGetRecord() {
        return Arrays.stream(canGets()).reduce(true, (a, b) -> a && b);
    }
}
