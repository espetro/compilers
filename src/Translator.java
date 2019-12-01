import java.io.PrintStream;
import java.util.Arrays;


/**
 * This class allows to apply and modify the control logic behind
 * intermediate code generation. It allows to convert from PL subset to Three-Address Code.
 */
public class Translator {
    private static int tmpVarCount, labelCount;
    private static int elseCount, forCount, whileCount, doWhileCount;
    protected static PrintStream out, err;
    protected static String tab;

    // Comparison states
    public static final int GT = 0;
    public static final int LT = 1;
    public static final int EQ = 2;
    public static final int GE = 3;
    public static final int LE = 4;
    public static final int NEQ = 5;
    public static final String[] comps = {">", "<", "==", ">=", "<=", "!="};

    // Logical constants
    public static final int TRUE = 10;
    public static final int FALSE = 11;

    /**
     *
     * @param stream
     * @param err_stream
     * @param isTabbed if true, it uses tabs to make code blocks, along with 'L0:' label style
     */
    public Translator(PrintStream stream, PrintStream err_stream, boolean isTabbed) {
        out = stream;
        err = err_stream;
        tmpVarCount = labelCount = 0;
        elseCount = forCount = whileCount = doWhileCount = 0;
        tab = isTabbed ? "\t" : "";
    }

    // Creates a new temporary variable
    public static String getNewTmpVar() {
        return "t" + tmpVarCount++;
    }

    // Creates a new label
    public static String getNewLabel() {
        return "L" + labelCount++;
    }

    public static String getNewFor() { return String.valueOf(forCount++); }

    public static String getNewElse() { return String.valueOf(elseCount++); }

    public static String getNewWhile() { return String.valueOf(whileCount++); }

    public static String getNewDoWhile() { return String.valueOf(doWhileCount++); }

    // ===================================================================
    // ===================== NON-TERMINAL GENERATORS =====================

    public static String arithmetic(String e1, String op, String e2) {
        String tmp = getNewTmpVar();
        _applyOp(e1, op, e2, tmp);
        return tmp;
    }

    /**
     * Logs an IF statement. Comparison statements are only associated with conditional statements (IFs)
     * @param e1
     * @param comp
     * @param e2 Can be null if 'comp' is equal to TRUE or FALSE token
     * @return
     */
    public static Condition comparison(String e1, int comp, String e2) {
        Condition tag = new Condition();

        boolean checkAbsolute = comp == Translator.TRUE || comp == Translator.FALSE;
        String txt = checkAbsolute ? e1 : _applyCond(e1, comp, e2);

        _if(txt, tag.TrueLabel(), tag.FalseLabel());
        return tag;
    }

    public static String assignment(String ident, String expr) {
        out.println(String.format("%s = %s;", ident, expr));
        return ident;
    }

    public static String logical() {
        return "";
    }


    // ===================================================================
    // ===================== DEFAULT MESSAGES ============================

    public static void _goto(String label) {
        out.println(String.format("goto %s;", label));
    }

    public static void _label(String label) {
        out.println(String.format("label %s;", label));
    }

    /**
     * Applies a conditional statement
     * @param c1
     * @param comp
     * @param c2
     */
    public static String _applyCond(String c1, int comp, String c2) {
        return String.format("%s %s %s", c1, comps[comp], c2);
    }

    /**
     * Applies an arithmetic statement
     * @param e1
     * @param op
     * @param e2
     * @param assignTo
     */
    public static void _applyOp(String e1, String op, String e2, String assignTo) {
        out.println(String.format("%s = %s %s %s;", assignTo, e1, op, e2));
    }

    public static void _if(String cond, String lTrue, String lFalse) {
        out.println(String.format("if (%s) goto %s;", cond, lTrue));
        out.println(String.format("goto %s;", lFalse));
    }

    public static void _print(String txt) {
        out.println(String.format("print %s;", txt));
    }

    public static void _errorDefault() {
        err.print("Error;");
    }

    public static void _errorInstr(String tokens) {
        err.println(String.format("Error: code generation failed with arguments: %s", tokens));
    }

    public static void _halt() {
        out.println("halt;");
    }
}
