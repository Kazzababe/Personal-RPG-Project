package ravioli.gravioli.rpg.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;

public class CommonUtil {
    public static boolean isInteger(String string) {
        return string.matches("^(-|\\+)?\\d+$");
    }

    public static String wrapString(String str, char colourCode, int lineLength, boolean wrapLongWords) {
        String[] split = WordUtils.wrap(str, lineLength, null, wrapLongWords).split("\\r\\n");
        String[] fixed = new String[split.length];

        fixed[0] = split[0];

        for (int i = 1; i < split.length; i++) {
            String line = split[i];
            String previous = split[i - 1];

            int code = previous.lastIndexOf(colourCode);
            if (code != -1) {
                char cCode = previous.charAt(code == previous.length() - 1 ? code : code + 1);
                if (code == previous.length() - 1) {
                    if (ChatColor.getByChar(line.charAt(0)) != null) {
                        fixed[i - 1] = previous.substring(0, previous.length() - 1);
                        line = "§" + line;
                        split[i] = line;
                    }
                } else {
                    if (line.length() < 2 || line.charAt(0) != colourCode || ChatColor.getByChar(line.charAt(1)) == null) {
                        if (ChatColor.getByChar(cCode) != null) {
                            line = "§" + cCode + line;
                        }
                    }
                }
            }
            fixed[i] = line;
            split[i] = line;
        }
        return ChatColor.translateAlternateColorCodes(colourCode, StringUtils.join(fixed, '\n'));
    }
}
