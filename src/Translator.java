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

    public static String getNewTmpVar() { return "t" + tmpVarCount++; }
    public static String getNewLabel() {
        return "L" + labelCount++;
    }

    private static boolean isTemporal(String id) { return id.matches("t[0-9]+"); }
    private static boolean isVariable(String str) { return str.matches("[a-zA-Z][a-zA-Z0-9_]*") || isTemporal(str); }
    private static boolean isArrayElement(String str) { return str.matches("[a-zA-Z]\\[[a-zA-Z0-9_]+\\]"); }
    public static boolean isIntConst(String str) { return str.matches("0|[1-9][0-9]*"); }
    private static boolean isFloatConst(String str) { return !isIntConst(str) && !isVariable(str); }

    public static boolean isValid(String str) {
        // An expression is valid if it's either (constant, temporal, declared variable).
        // logging.println("Checking if variable " + str + " is valid.");
        if(isVariable(str)) {
            return Variables.isDeclared(str);
        } else {
            return true;
        }
    }

    public static boolean isInt(String str) {
        // An expr is int if it's an int constant or an int variable (this also checks for x[i] expressions)
        if (isIntConst(str)) {
            return true;
        } else if (isVariable(str)) { // done not to get float const into this
            return Variables.getType(str) == "int";
        } else if (isArrayElement(str)) {
            return Variables.getType(String.valueOf(str.charAt(0))) == "float";
        } else {
            return false;
        }
    }

    public static boolean isFloat(String str) {
        // An expr is float if it's a float constant or a float variable (this also checks for x[i] expressions)
        if (isFloatConst(str)) {
            return true;
        } else if (isVariable(str)) { // done not to get int const into this
            logging.println("isFloat " + str + " ?");
            return Variables.getType(str) == "float";
        } else if (isArrayElement(str)) { // THIS IS ACTUALLY NOT NEEDED - I WASNT DOING A NICE CHECK ON expr: ID[..]
            logging.println("isFloat " + str + " ?");
            return Variables.getType(Character.toString(str.charAt(0))) == "float";
        } else {
            return false;
        }
    }

    // ===================================================================
    // ===================== NON-TERMINAL GENERATORS =====================

    public static String getType(String cnst) {
        return isInt(cnst) ? "int" : "float";
    }

    public static String arithmetic(String e1, String op, String e2) {
        // ====  HERE I DONT CHECK IF e1 OR e2 ARE ELEMENTS OF AN ARRAY => isFloat/isInt CANNOT DETECT THAT ====
        String temp_type = "int";
        // If at least one is Float, change the OP and TMP type.
        if (isFloat(e1)) {
            op += "r";
            temp_type = "float";
            if (isInt(e2)) {
                String t0 = getNewTmpVar();
                Variables.declareTempVar(t0, "0", "float");
                _applyCastedAssign(t0, "(float)", e2);
                e2 = t0;
            }
        }
        // If the expression types are different, the int type is casted to float
        else if (isFloat(e2)) {
            op += "r";
            temp_type = "float";
            if (isInt(e1)) {
                String t0 = getNewTmpVar();
                Variables.declareTempVar(t0, "0", "float");
                _applyCastedAssign(t0, "(float)", e1);
                e1 = t0;
            }
        }

        String tmp = getNewTmpVar();
        Variables.declareTempVar(tmp, "0", temp_type);

        _applyOp(tmp, e1, op, e2);
        return tmp;
    }

    public static String assignment(String ident, String expr) {
        // Case 1: INT = FLOAT => INT = (INT) FLOAT
        if (isInt(ident) && isFloat(expr)) {
            if (isFloatConst(expr)) { _errorTrace("Translator Error: Assignment (float const to int)"); }
            _applyCastedAssign(ident, "(int)", expr);
        }
        // Case 2: FLOAT = INT => FLOAT = (FLOAT) INT
        else if (isFloat(ident) && isInt(expr)) {
            if (isIntConst(expr)) { _errorTrace("Translator Error: Assignment (int const to float)"); }
            _applyCastedAssign(ident, "(float)", expr);
        }
        // Otherwise: no casting
        else {
            _applyAssign(ident, expr);
        }
        return ident;
    }

    public static String assignment2Array(String ident, String size, String expr) {
        String varID = String.valueOf(ident.charAt(0));
        String _ident = String.format("%s[%s]", ident, size);

        // Case 1: INT = FLOAT => INT = (INT) FLOAT
        if (isInt(varID) && isFloat(expr)) {
            String t0 = getNewTmpVar();
            _applyCastedAssign(t0, "(int)", expr);
            expr = t0;
        }
        // Case 2: FLOAT = INT => FLOAT = (FLOAT) INT
        else if (isFloat(varID) && isInt(expr)) {
            String t0 = getNewTmpVar();
            _applyCastedAssign(t0, "(float)", expr);
            expr = t0;
        }
        // Otherwise
        _applyAssign(_ident, expr);

        return ident;
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

    public static void print(String exp) {
        out.println(String.format("print %s;", exp));
    }

    // ===================================================================
    // ===================== DEFAULT MESSAGES ============================

    public static void _goto(String label) {
        out.println(String.format("goto %s;", label));
    }

    public static void _label(String label) {
        out.println(String.format("label %s;", label));
    }

    public static void _applyCastedAssign(String a, String cast, String b) {
        out.println(String.format("%s = %s %s;", a, cast, b));
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

    public static void _errorTrace(String info) {
        String out = "error;\n" + String.format("# %s", info);
        err.println(out);
        logging.println(out);
        System.exit(0);
    }

    public static void _error() {
        err.println("error;");
    }
    public static void _comment(String info) {
        out.println("# " + info);
    }

    public static void _halt() {
        out.println("halt;");
    }
}
