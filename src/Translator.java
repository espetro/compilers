import java.io.PrintStream;

/**
 * This class allows to apply and modify the control logic behind
 * intermediate code generation. It allows to convert from PL subset to Three-Address Code.
 */
public class Translator {
    private static final String _indent = "    ";
    private static int tmpVarCount = 0, labelCount = 0;
    protected static PrintStream out, err;

    // Comparison states
    public static final String comp_operators[] = {"<", "=="};
    public static final int LT = 0;
    public static final int EQ = 1;

    public static String getNewTmpVar(String type) {
        String id = "t" + tmpVarCount++;
        Variables.declareTemp(id, type);
        return id;
    }

    public static String getNewLabel() {
        return "L" + labelCount++;
    }

    // ===================================================================
    // ===================== NON-TERMINAL GENERATORS =====================

    public static String arithmetic(String e1, String op, String e2) {
        String type1 = Variables.getType(e1);
        String type2 = Variables.getType(e2);
        String tmp;

        if (type1 == type2) {
            tmp = (type1 == "char") ? getNewTmpVar("char") : getNewTmpVar("int");

            switch (type1) {
                case "char":
                    // Case 1: Both expr are "char"
                    // Case 1.1: Operation is not "+" or "-"
                    if (op == "*" || op == "/") { Translator._errorTrace("Error: " + op + " no disp. para char"); }
                    if (!Variables.isDeclared(e1)) {
                        e1 = Char.toInt(e1);
                    }
                    if (!Variables.isDeclared(e2)) {
                        e2 = Char.toInt(e2);
                    }
                    break;
                default: break; // Case 2: Both expr are "int"
            }
        } else {
            // Case 3: Different types (no implicit casting is allowed) => If "+" or "-" and one is INT, then it's OK
            tmp = getNewTmpVar("int");

            if (op == "*" || op == "/") { Translator._errorTrace("Error: Operacion entre tipos sin 'casting'"); }
            if (type1 == "char" && !Variables.isDeclared(e1)) {
                e1 = Char.toInt(e1);
            } else if (type2 == "char" && !Variables.isDeclared(e2)) {
                e2 = Char.toInt(e2);
            }
        }

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


    public static String assignment(String id, String expr) {
        String type0 = Variables.getType(id);
        String type1 = Variables.getType(expr);

        if (type0 != type1 && (!Variables.isTemporal(id) && !Variables.isTemporal(expr))) {
            // Case 0: they differ in type (unless both are temporal, which is an internal operation and ofc allowed (charoper8.plx)
            Translator._errorTrace("Las expresiones " + id + ", " + expr + " no son tipo-compatibles");
        } else {
            // Case 1: both have the same type
            if (type1 == "char" && !Variables.isDeclared(expr)) {
                expr = String.valueOf((int) expr.replace("\'", "").charAt(0));
            }
            _applyAssign(id, expr);
        }
        return id;
    }

    public static void print(String expr) {
        if (Variables.isChar(expr)) {
            int chVal = (int) expr.replace("'", "").charAt(0);
            String res = String.valueOf(chVal);
            _printChar(res);
        } else if((Variables.isDeclared(expr) && Variables.getType(expr) == "char")) {
            _printChar(expr);
        } else {
            _print(expr);
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
        _goto(lFalse); // _else
    }

    public static void _if(String cond, String lTrue) {
        out.println(String.format("%sif (%s) goto %s;", _indent, cond, lTrue));
    }

    public static void _print(String exp) {
        out.println(String.format("%sprint %s;", _indent, exp));
    }

    public static void _printChar(String exp) {
        out.println(String.format("%sprintc %s;", _indent, exp));
    }

    public static void _errorTrace(String info) {
        _comment(info);
        err.println(_indent + "error;");
    }

    public static void _comment(String info) {
        err.println(String.format("# %s", info));
    }

    public static void _error() {
        out.println(_indent + "error;");
    }

    public static void _halt() {
        out.println(_indent + "halt;");
    }
}
