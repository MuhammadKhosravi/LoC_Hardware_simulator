package view;

import model.HelpType;
import model.Instructions.InitInstruction;
import model.Instructions.ModelingInstruction;
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
    public static final String MODEL_FINISH = "finish modeling";

    public static final String INIT_START = "start init";
    public static final String INIT_FINISH = "finish init";

    public static final String SIM_START = "start simulate";
    public static final String SIM_FINISH = "finish simulate";

    public static final String END = "end";


    private static final Pair<String, InitInstruction> INIT_VALUE =
            new Pair<>("^(?<name>\\w+) = (?<value>\\d)$", InitInstruction.INIT_VALUE);

    private static final Pair<String, ModelingInstruction> MODELING_AND =
            new Pair<>("and\\((?<output>\\w+), (?<delay>\\d+), (?<inputs>.+)\\)$", ModelingInstruction.AND);

    private static final Pair<String, ModelingInstruction> MODELING_OR =
            new Pair<>("or\\((?<output>\\w+), (?<delay>\\d+), (?<inputs>.+)\\)$", ModelingInstruction.OR);

    private static final Pair<String, ModelingInstruction> MODELING_NAND =
            new Pair<>("nand\\((?<output>\\w+), (?<delay>\\d+), (?<inputs>.+)\\)$", ModelingInstruction.NAND);

    private static final Pair<String, ModelingInstruction> MODELING_NOR =
            new Pair<>("nor\\((?<output>\\w+), (?<delay>\\d+), (?<inputs>.+)\\)$", ModelingInstruction.NOR);

    private static final Pair<String, ModelingInstruction> MODELING_XOR =
            new Pair<>("xor\\((?<output>\\w+), (?<delay>\\d+), (?<inputs>.+)\\)$", ModelingInstruction.XOR);

    private static final Pair<String, ModelingInstruction> MODELING_UPDATE =
            new Pair<>("^update (?<name>\\w+) = (?<value>\\d) in (?<time>\\d+)$", ModelingInstruction.XOR);

    private static final Pair<String, ModelingInstruction> MODELING_DEFINE_WIRE =
            new Pair<>("^wire (?<name>\\\\w+)$", ModelingInstruction.XOR);


    private static final Statics instance = new Statics();
    private static final Scanner SCANNER = new Scanner(System.in);

    public static List<Pair<String, InitInstruction>> INIT_INSTRUCTIONS = new ArrayList<>((Arrays.asList(
            INIT_VALUE
    )));

    public static List<Pair<String, ModelingInstruction>> MODELING_INSTRUCTIONS = new ArrayList<>((Arrays.asList(
            MODELING_AND,
            MODELING_OR,
            MODELING_NAND,
            MODELING_NOR,
            MODELING_XOR,
            MODELING_UPDATE,
            MODELING_DEFINE_WIRE
    )));

    public static Statics getInstance() {
        return instance;
    }

    public static void help(HelpType type) {
        if (!IS_GUID_NEEDED) return;
        switch (type) {
            case SEGMENT_VIEW -> System.out.println("""
                    Available commands :
                    1)start init : start defining inputs
                    2)start modeling : start defining system logics
                    3)start simulate : start simulating your system
                    4)end : terminate program
                    """);
            case INIT_VIEW -> System.out.println("""
                    Available commands :
                    1) [name] = [ 0 or 1 as value ]
                    2) finish init
                    """);
            case MODEL_VIEW -> System.out.println("""
                    Available instruction :
                    1) [gate_name]([output_wire], [delay_time], [inputs]\\)
                    """);
        }
    }

    public static Scanner getScanner() {
        return SCANNER;
    }

    public static Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

}
