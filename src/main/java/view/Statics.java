package view;

import model.HelpType;
import model.InitInstruction;
import model.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Statics {
    public static final boolean IS_GUID_NEEDED = false;


    public static final String MODEL_START = "start modeling";
    public static final String INIT_START = "start init";
    public static final String SIM_START = "start simulate";
    public static final String END = "end";

    public static final String SIM_FINISH = "^finish simulate$";
    public static final String MODEL_FINISH = "^finish modeling$";


    public static final Pair<String , InitInstruction> INIT_VALUE =
            new Pair<>("^(?<name>\\w+) = (?<value>\\d)$", InitInstruction.INIT_VALUE);
    public static final Pair<String , InitInstruction> INIT_FINISH =
            new Pair<>("^finish init$", InitInstruction.INIT_FINISH);

    public static final String DEF_WIRE = "^wire (?<name>\\\\w+)$";


    public static final String UPDATE = "^update (?<name>\\w+) = (?<value>\\d) in (?<time>\\d+)$";

    public static List<Pair<String , InitInstruction>> INIT_INSTRUCTIONS = new ArrayList<>((Arrays.asList(
            INIT_FINISH,
            INIT_VALUE
    )));

    private static final Statics instance = new Statics();
    private static final Scanner SCANNER = new Scanner(System.in);

    public static Statics getInstance() {
        return instance;
    }

    public static void help(HelpType type) {
        if (!IS_GUID_NEEDED) return;
        switch (type) {
            case SEGMENT_VIEW:
                System.out.println("""
                        Available commands :
                        1)start init : start defining inputs
                        2)start modeling : start defining system logics
                        3)start simulate : start simulating your system
                        4)end : terminate program
                        """);
                break;
            case INIT_VIEW:
                System.out.println("""
                        Available commands :
                        1) (name) = ( 0 or 1 as value)
                        2) finish init
                        """);
                break;
            case MODEL_VIEW:
        }
    }

    public static Scanner getScanner() {
        return SCANNER;
    }

    public String getGateRegex(String gate) {
        String BASE_GATE = "\\((?<output>\\w+), (?<delay>\\d+), (?<inputs>.+)\\)$";
        return "^" + gate + BASE_GATE;
    }

    public static List<Pair<String, InitInstruction>> getInitInstructions(){
        return INIT_INSTRUCTIONS;
    }

    public Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

}
