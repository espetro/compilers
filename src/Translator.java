import java.io.PrintStream;

/**
 * This class allows to apply and modify the control logic behind
 * intermediate code generation. It allows to convert from PL subset to Three-Address Code.
 */
public class Translator {
    private static final String _indent = "   ";
    private static int tmpVarCount = 0, labelCount = 0;
    protected static PrintStream out, err;

    // Comparison states
    public static final String comp_operators[] = {"<", "=="};
    public static final int LT = 0;
    public static final int EQ = 1;

    // EOF Character
    public static final int EOF = 10;

    public static String getNewTmpVar(String type) {
        String id = "t" + tmpVarCount++;
        Variables.declareTemp(id, type, "0");
        return id;
    }

    public static String getNewTmpArr(String type, String length) {
        String id = "t" + tmpVarCount++;
        Variables.declareTemp(id, type, length);
        return id;
    }

    public static String getNewLabel() {
        return "L" + labelCount++;
    }

    // ===================================================================
    // ===================== NON-TERMINAL GENERATORS =====================

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

    public static String arithmetic(String e1, String op, String e2) {
        String tmp = getNewTmpVar("int"); // as of now, arithmetic operators are only defined for "INT"
        _applyOp(tmp, e1, op, e2);
        return tmp;
    }

    public static String assignment(String ident, String expr) {
        boolean cond1 = !Variables.getType(ident).equals(Variables.getType(expr));  // types are not equal
        boolean cond2 = !Variables.isTemporal(ident) && !Variables.isTemporal(expr); // both aren't temporals
        if (cond1 && cond2) {
            Translator._errorTrace("ASSIGN: Tipos no compatibles ("+ ident + ", "+ expr);
        }
        expr = Variables.isCharConst(expr) ? Chars.toInt(expr) : expr;

        _applyAssign(ident, expr);
        return ident;
    }

    public static void print(String expr) {
        if (Variables.isChar(expr)) {
            if (Variables.isArray(expr)) {
                Arrays.print(expr, "char");
            } else {
                expr = Variables.isCharConst(expr) ? Chars.toInt(expr) : expr;
                _print(expr, "char");
            }
        } else if (Variables.isArrayConst(expr)) { // i.e. {1,2,3} or similar
            String[] tempArray = Arrays.parse(expr);
            _print(tempArray[0], tempArray[1]);
        } else {
            if (Variables.isArray(expr)) {
                Arrays.print(expr, "int");
            } else {
                _print(expr, "int");
            }
        }
    }

    // ===================================================================
    // ===================== DEFAULT MESSAGES ============================

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
        _if(cond, lTrue);
        _goto(lFalse);
    }

    public static void _if(String cond, String label) {
        out.println(String.format("%sif (%s) goto %s;", _indent, cond, label));
    }

    public static void _print(String exp, String type) {
        if (type == "char") {
            out.println(String.format("%sprintc %s;", _indent, exp));
        } else {
            out.println(String.format("%sprint %s;", _indent, exp));
        }
    }

    public static void _errorTrace(String info) {
        _error();
        _comment(info);
        throw new RuntimeException(info);
    }

    public static void _comment(String info) {
        out.println(String.format("# %s", info));
    }

    public static void _error() {
        err.println(_indent + "error;");
    }
    public static void _halt() {
        out.println(_indent + "halt;");
    }
}
