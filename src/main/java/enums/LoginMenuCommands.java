package enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands {
    SIGN_UP("\\{register\\(username<(?<username>[^<>]*)>\\)\\(password<(?<password>[^<>]*)>\\)\\(confirm<(?<confirm>[^<>]*)>\\)\\(nickname<(?<nickname>[^<>]*)>\\)\\(email<(?<email>[^<>]*)>\\)\\{question\\(text<(?<text>[^<>]*)>\\)\\(answer<(?<answer>[^<>]*)>\\)\\}\\}"),
    LOGIN("\\{login\\(username<(?<username>[^<>]*)>\\)\\(password<(?<password>[^<>]*)>\\)\\}");

    private final String pattern;

    LoginMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);
        if (matcher.matches()) return matcher;
        return null;
    }
}
