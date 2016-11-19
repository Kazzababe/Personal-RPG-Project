package ravioli.gravioli.rpg.util;

public class CommonUtil {
    public static boolean isInteger(String string) {
        return string.matches("^(-|\\+)?\\d+$");
    }
}
