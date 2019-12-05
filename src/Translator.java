import java.io.PrintStream;
import java.util.ArrayList;


/**
 * This class allows to apply and modify the control logic behind
 * intermediate code generation. It converts from PL subset to Three-Address Code.
 */
public class Translator {
    private static int tmpVarCount = 0, labelCount = 0;
    protected static PrintStream out, err;
    // private static ArrayList<String> variables = new ArrayList<>();

    // Comparison states
    public static final int GT = 0;
    public static final int LT = 1;
    public static final int EQ = 2;
    public static final int GE = 3;
    public static final int LE = 4;
    public static final int NEQ = 5;
    public static final String[] comps = {">", "<", "==", ">=", "<=", "!="};

    // Creates a new temporary variable
    public static String getNewTmpVar() {
        String tmp = "t" + tmpVarCount++;
        return tmp;
    }

    // Creates a new label
    public static String getNewLabel() {
        return "L" + labelCount++;
    }

    // ===================================================================
    // ===================== NON-TERMINAL GENERATORS =====================

    public static String arithmetic(String e1, String txt, String e2, boolean autoAsign) {
        String result = autoAsign ? e1 : getNewTmpVar();
        _applyOp(e1, txt, e2, result);
        return result;
    }

    public static Condition comparison(String e1, int comp, String e2) {
        Condition tag = new Condition();
        String txt = _applyCond(e1, comp, e2);
        _if(txt, tag.TrueLabel(), tag.FalseLabel());
        return tag;
    }

    public static String assignment(String ident, String expr) {
        out.println(String.format("%s = %s;", ident, expr));
        return ident;
    }

    // ===================================================================
    // ===================== DEFAULT MESSAGES ============================

    public static void _goto(String label) {
        out.println(String.format("goto %s;", label));
    }

    public static void _label(String label) {
        out.println(String.format("label %s;", label));
    }

    public static String _applyCond(String c1, int comp, String c2) {
        return String.format("%s %s %s", c1, comps[comp], c2);
    }

    public static void _applyOp(String e1, String op, String e2, String assignTo) {
        out.println(String.format("%s = %s %s %s;", assignTo, e1, op, e2));
    }

    public static void _if(String cond, String lTrue, String lFalse) {
        out.println(String.format("if (%s) goto %s;", cond, lTrue));
        out.println(String.format("goto %s;", lFalse));
    }

    public static void _print(String exp) {
        out.println(String.format("print %s;", exp));
    }

    public static void _errorTrace(String info) {
        err.println(String.format("# %s", info));
        err.println("error;");
    }
    public static void _halt() {
        out.println("halt;");
    }
}
