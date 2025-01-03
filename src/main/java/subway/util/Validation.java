package subway.util;

import java.util.regex.Pattern;

public final class Validation {
    private Validation() {
    }

    public static boolean isNumeric(String str) {
        return Pattern.matches("^[0-9]*$", str);
    }
}
