import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;


/**
 * This class allows to apply and modify the control logic behind
 * intermediate code generation. It allows to convert from PL subset to Three-Address Code.
 */
public class Translator {
    private static int tmpVarCount = 0, labelCount = 0;
    private static Map<String, Variable> variables = new HashMap<>();
    private static int blockLevel = 0;
    protected static PrintStream out, err;

    // Comparison states
    public static final String[] comp_operators = {"<", "=="};
    public static final int LT = 0;
    public static final int EQ = 1;

    public static String getNewTmpVar() { return "t" + tmpVarCount++; }
    public static String getNewLabel() {
        return "L" + labelCount++;
    }

    public static void incrementBlockLevel() {
        blockLevel++;
    }
    public static void decrementBlockLevel() {
        // when exiting the block level, clear the variables with this level
        for(Variable var : variables.values()) {
            var.clear(blockLevel);
        }
        blockLevel--;
    }

    public static void addVar(String id) {
        // If there's no variable, add it in the curr blocklevel
        // If there's a variable in the curr blocklevel => panic!
        // If there's a variable in other blocklevel, add it in the current one
        if(variables.containsKey(id)) {
            if(variables.get(id).atLevel(blockLevel)) {
                _errorTrace("Declaration error: Variable ya declarada");
            } else {
                variables.get(id).add(blockLevel);
            }
        } else {
            variables.put(id, new Variable(id, blockLevel));
        }
    }

    public static Optional<Tuple> checkID(String id) {
        // If ID is a variable defined in upper or curr level -> Optional.of(id_blocklevel)
        // If ID is a variable that's not been defined in upper or current level -> Optional.empty()
        Optional<Tuple> result;
        result = variables.containsKey(id) ? variables.get(id).getAt(blockLevel) : Optional.empty();
        return result;
    }

    public static boolean isVariable(String str) { return str.matches(".*[a-z].*"); }


    // ===================================================================
    // In all generators except "Comparison" we check if the expression-variables have been initialized
    // ===================== NON-TERMINAL GENERATORS =====================

    public static Condition comparison(String e1, int op, String e2, boolean perm) {
        Condition tag = new Condition();
        String cond = String.format("%s %s %s", e1, comp_operators[op], e2);

        if(perm) {
            _if(cond, tag.FalseLabel(), tag.TrueLabel());
        } else {
            _if(cond, tag.TrueLabel(), tag.FalseLabel());
        }
        return tag;
    }

    public static String arithmetic(String e1, String op, String e2, boolean autoAssign) throws RuntimeException {
        // 'expr' is not always a variable
        Optional<Tuple> check1 = isVariable(e1) ? checkID(e1) : Optional.of(new Tuple(e1,e1));
        Optional<Tuple> check2 = isVariable(e2) ? checkID(e2) : Optional.of(new Tuple(e2,e2));
        Tuple var = new Tuple("","");

        if(check1.isEmpty() || check2.isEmpty()) {
            _errorTrace("ALU Error: variable no declarada");
        } else {
            if(autoAssign) { var = check1.get(); }
            else { String tmp = getNewTmpVar(); var = new Tuple(tmp,tmp); addVar(tmp); }
            // adds temp var to varlist

            _applyOp(var.printable, check1.get().printable, op, check2.get().printable);
        }
        return var.id; // if errorTrace is thrown, this never happens
    }

    public static String assignment(String ident, String expr) throws RuntimeException {
        Optional<Tuple> check1 = isVariable(ident) ? checkID(ident) : Optional.empty();
        // 'expr' is not always a variable ('ident' is)
        Optional<Tuple> check2 = isVariable(expr) ? checkID(expr) : Optional.of(new Tuple(expr, expr));

        // out.println(String.format("(%d) %s %s %s %s", blockLevel, ident, expr, check1, check2));

        if(check1.isEmpty() || check2.isEmpty()) {
            _errorTrace("Assignment error: variable no declarada");
        } else {
            _applyAssign(check1.get().printable, check2.get().printable);
        }
        return check1.get().id; // if errorTrace is thrown, this never happens
    }

    public static void print(String exp) throws RuntimeException {
        // Check if it's a variable and it's not been declared. Otherwise, print!
        if(isVariable(exp)) {
            Optional<Tuple> check = checkID(exp);

            if(check.isEmpty()) { _errorTrace("IO error: variable no declarada"); }
            else { out.println(String.format("print %s;", check.get().printable)); }

        } else {
            out.println(String.format("print %s;", exp));
        }
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

    public static void _if(String cond, String lTrue, String lFalse) {
        out.println(String.format("if (%s) goto %s;", cond, lTrue));
        out.println(String.format("goto %s;", lFalse));
    }

    public static void _errorTrace(String info) throws RuntimeException {
        err.println("error;");
        err.println(String.format("# %s", info));
        System.err.println("Error at execution: " + info);
        System.exit(0);
        //throw new RuntimeException("Error at execution");
    }

    public static void _halt() { out.println("halt;"); }
}
