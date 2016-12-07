package unit.info;

public final class TooltipText {
    private TooltipText() {
    }

    public static String toTooltipText(String string) {
        return "<html>" + string.replaceAll("\n", "<br>") + "</html>";
    }

}
