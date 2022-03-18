package view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    private static final Regex instance = new Regex();
    public final String START_MODEL = "^start modeling$";
    public final String FINISH_MODEL = "^finish modeling$";
    public final String DEF_WIRE = "^wire (?<name>\\w+)$";
    public final String INIT_START = "^start init$";
    public final String INIT_FINISH = "finish init$";
    public final String INIT_VALUE = "^(?<name>\\w+) = (?<value>\\d)$";
    public final String UPDATE = "^update (?<name>\\w+) = (?<value>\\d) in (?<time>\\d+)$";
    public final String END = "^end$";

    public static Regex getInstance() {
        return instance;
    }

    public String getGateRegex(String gate) {
        String BASE_GATE = "\\((?<output>\\w+), (?<delay>\\d+), (?<inputs>.+)\\)$";
        return "^" + gate + BASE_GATE;
    }

    public Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }


}
