package unit;

public final class PreparedConditions {
    public static final String equal = "= ?";
    public static final String startWith = "LIKE CONCAT(?, '%')";

    private PreparedConditions() {
    }
}
