import java.io.PrintStream;

/**
 * This class allows to apply and modify the control logic behind
 * intermediate code generation. It allows to convert from PL subset to Three-Address Code.
 */
public class Translator {
    private static final String _indent = "    ";
    private static int labelCount = 0;
    public static PrintStream out, err, logging = System.out;
    protected static boolean checkRanges = true, debug = false;


    // Comparison states
    public static final String[] comp_operators = {"<", "=="};
    public static final int LT = 0;
    public static final int EQ = 1;

    public static String getNewLabel() {
        return "L" + labelCount++;
    }

    // ===================================================================
    // ===================== NON-TERMINAL GENERATORS =====================

    public static String arithmetic(String e1, String op, String e2, boolean toSelf) {
        // Ternary operator evaluates both before checking the condition - thus we may produce unused temporal vars
        String tmp = toSelf ? e1 : Variables.declareTemp("int");
        _applyOp(tmp, e1, op, e2);
        return tmp;
    }

    public static Condition comparison(String e1, int op, String e2, boolean perm) {
        Condition tag = new Condition();
        String cond = String.format("%s %s %s", e1, comp_operators[op], e2);

        if(perm) {
            _if_else(cond, tag.FalseLabel(), tag.TrueLabel());
        } else {
            _if_else(cond, tag.TrueLabel(), tag.FalseLabel());
        }
        return tag;
    }

    public static String assignment(String ident, String expr) {
        _applyAssign(ident, expr);
        return ident;
    }

    // ===================================================================
    // ===================== DEFAULT MESSAGES ============================

    public static void _updateArray(String id, String[] list) {
        String eTo;
        for (int i = 0; i < list.length; i++) {
            eTo = String.format("%s[%s]", id, i);
            Translator._applyAssign(eTo, list[i]);
        }
    }

    public static void _checkRange(String dimSize, String idx) {
        if (checkRanges) {
            String l0 = getNewLabel(), l1 = getNewLabel();
            _comment("Comprobacion de rangos");

            _if(String.format("%s < 0", idx), l0);
            _if(String.format("%s < %s", dimSize, idx), l0);
            _if(String.format("%s == %s", dimSize, idx), l0);
            _else(l1);

            _label(l0);
            _errorTrace("");
            _label(l1);
        }
    }

    public static void _goto(String label) {
        out.println(String.format("%sgoto %s;", _indent, label));
    }
    public static void _label(String label) {
        out.println(String.format("%s:", label));
    }

    public static void _applyAssign(String a, String b) {
        out.println(String.format("%s%s = %s;", _indent, a, b));
    }

    public static void _applyOp(String assignTo, String e1, String op, String e2) {
        out.println(String.format("%s%s = %s %s %s;", _indent, assignTo, e1, op, e2));
    }

    public static void _if_else(String cond, String lTrue, String lFalse) {
        out.println(String.format("%sif (%s) goto %s;", _indent, cond, lTrue));
        out.println(String.format("%sgoto %s;", _indent, lFalse));
    }

    public static void _if(String cond, String lTrue) {
        out.println(String.format("%sif (%s) goto %s;", _indent, cond, lTrue));
    }

    public static void _else(String lFalse) {
        out.println(String.format("%sgoto %s;", _indent, lFalse));
    }

    public static void _print(String exp) {
        out.println(String.format("%sprint %s;", _indent, exp));
    }

    public static void _errorTrace(String info) {
        if (info != "") { _comment(info); }
        _error();
        _halt();
    }

    public static void _comment(String info) {
        out.println("# " + info);
        if (debug) { logging.println("# " + info); }
    }

    public static void _error() {
        err.println(_indent + "error;");
        if (debug) { logging.println(_indent + "error;"); }
    }

    public static void _halt() {
        out.println(_indent + "halt;");
        if (debug) { logging.println(_indent + "halt;"); }
    }
}
