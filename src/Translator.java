import java.io.PrintStream;

/**
 * This class allows to apply and modify the control logic behind
 * intermediate code generation. It allows to convert from PL subset to Three-Address Code.
 */
public class Translator {
    private static int tmpVarCount = 0, labelCount = 0;
    protected static PrintStream out, err;
    public static PrintStream logging = System.out;

    // Comparison states
    public static final String comp_operators[] = {"<", "=="};
    public static final int LT = 0;
    public static final int EQ = 1;

    public static String getNewLabel() {
        return "L" + labelCount++;
    }

    public static String getNewTmpVar(String type, boolean isPointer, boolean isDeref) {
        String tmp = "t" + tmpVarCount++;
        Variables.declareTmpVar(tmp, type, isPointer, isDeref);
        return tmp;
    }

    // ===================================================================
    // ===================== NON-TERMINAL GENERATORS =====================

    public static String arithmetic(String e1, String op, String e2) {
        if(Variables.isPointer(e1) || Variables.isPointer(e2) || Variables.isDeref(e1) || Variables.isDeref(e2)) {
            _errorTrace("ALU Error: Arithmetics with pointers/derefs is not allowed");
        }
        String tmp = getNewTmpVar("int", false, false);
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

    public static void _goto(String label) {
        out.println(String.format("goto %s;", label));
    }
    public static void _label(String label) {
        out.println(String.format("label %s;", label));
    }

    public static void _applyAssign(String a, String b) {
        out.println(String.format("%s = %s;", a, b));
    }

    public static void _applyOp(String assignTo, String e1, String op, String e2) {
        out.println(String.format("%s = %s %s %s;", assignTo, e1, op, e2));
    }

    public static void _if_else(String cond, String lTrue, String lFalse) {
        out.println(String.format("if (%s) goto %s;", cond, lTrue));
        out.println(String.format("goto %s;", lFalse));
    }

    public static void _if(String cond, String lTrue) {
        out.println(String.format("if (%s) goto %s;", cond, lTrue));
    }

    public static void _else(String lFalse) {
        out.println(String.format("goto %s;", lFalse));
    }

    public static void _print(String exp) {
        out.println(String.format("print %s;", exp));
    }

    public static void _errorTrace(String info) {
        err.println("error;");
        err.println(String.format("# %s", info));
    }

    public static void _halt() {
        out.println("halt;");
    }

}
