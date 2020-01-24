import java.io.PrintStream;

/**
 * This class allows to apply and modify the control logic behind
 * intermediate code generation. It allows to convert from PL subset to Three-Address Code.
 */
public class Translator {
    private static final String _indent = "    ";
    private static int tmpVarCount = 0, labelCount = 0;
    protected static PrintStream out, err, logging;

    // Comparison states
    public static final int EQ = 0;
    public static final int NE = 1;
    public static final int LE = 2;
    public static final int GE = 3;

    public static String getNewLabel() {
        return "L" + labelCount++;
    }

    public static void errorTrace(String info) {
        throw new RuntimeException(info);
    }

    public static boolean isConst(String str) {
        return str.matches("0|[1-9][0-9]*");
    }

    // ===================================================================
    // ===================== NON-TERMINAL GENERATORS =====================

    // Case 0: expr is in stack (i.e. called "stack") => no instructions
    // Case 1: expr is a const => push(expr)
    // Case 2: expr is a variable => no instructions (it must be already loaded in ID:id in CUP)
    public static void doExprAction(String expr) {
        if (expr != "stack") {
            if (isConst(expr)) {
                _push(expr);
            }
            // else isVariable => nothing
            // else isFunction => ???
        }
    }

    public static void Return(String expr) {
        doExprAction(expr);
        Translator._returnMethod();
    }

    public static String arithmetic(String e1, String op, String e2) {
        doExprAction(e1);
        doExprAction(e2);
        switch (op) {
            case "+": _add(); break;
            case "*": _mult(); break;
            case "-": _sub(); break;
            case "/": _div(); break;
        }
        return "stack"; // The result of the arithmetic is in the stack
        // Even I can create a Stack instance to ensure temporal variable tX is in the stack (ONLY IF NEEDED)
    }

    public static Condition comparison(int op) {
        Condition tag = new Condition();
        String tTrue = tag.TrueLabel(), tFalse = tag.FalseLabel();

        _sub();
        switch (op) {
            case Translator.EQ: _ifeq(tTrue); break;
            case Translator.NE: _ifne(tTrue); break;
            case Translator.GE: _ifge(tTrue); break;
            case Translator.LE: _ifle(tTrue); break;
        }
        _goto(tFalse);

        return tag;
    }

    public static String assignment(String id, String assign) {
        String idx = Functions.indexOf(id);

        doExprAction(assign); // does a different action whether it's (constant, variable, ALU result)
        _store(idx);
        _blank();

        return id;
    }
    // ===================================================================
    // ===================== DEFAULT MESSAGES ============================

    public static void _label(String label) {
        out.println(String.format("%s:", label));
    }

    public static void _startMethod(String id) {
        out.println(String.format(".method public static %s(I)I", id));
    }

    public static void _returnMethod() {
        out.println(_indent + "ireturn");
    }

    public static void _invoke(String id) {
        out.println(String.format("invokestatic JPL/%s(I)I", id));
    }

    public static void _endMethod() {
        out.println(".end method");
        out.println();
    }

    public static void _call(String fun) {
        out.println(String.format("%sinvokestatic JPL/%s(I)I", _indent, fun));
    }

    public static void _push(String cnst) {
        out.println(_indent + "sipush " + cnst);
    }

    public static void _load(String pos) {
        out.println(_indent + "iload " + pos);
    }

    public static void _store(String pos) {
        out.println(_indent + "istore " + pos);
    }

    public static void _add() {
        out.println(_indent + "iadd");
    }

    public static void _sub() {
        out.println(_indent + "isub");
    }

    public static void _mult() {
        out.println(_indent + "imul");
    }

    public static void _div() {
        out.println(_indent + "idiv");
    }

    public static void _pop() {
        out.println(_indent + "pop");
    }

    public static void _duplicate() {
        out.println(_indent + "dup");
    }
    public static void _goto(String label) {
        out.println(String.format("%sgoto %s", _indent, label));
    }

    public static void _ifeq(String label) {
        out.println(String.format("%sifeq %s", _indent, label));
    }

    public static void _ifne(String label) {
        out.println(String.format("%sifne %s", _indent, label));
    }

    public static void _ifge(String label) {
        out.println(String.format("%sifge %s", _indent, label));
    }

    public static void _ifle(String label) {
        out.println(String.format("%sifle %s", _indent, label));
    }

    public static void _limitStack(String num) {
        out.println(String.format("%s.limit stack %s", _indent, num));
    }

    public static void _limitLocals(String num) {
        out.println(String.format("%s.limit locals %s", _indent, num));
    }

    public static void _nop() {
        out.println(_indent + "nop");
    }

    public static void _blank() {
        // improves intermediate code readability
        // out.println(_indent + "");
    }

    // ==== TEMPORAL METHODS ====

    public static void _ifgt(String label) {
        out.println(String.format("%sifgt %s", _indent, label));
    }

    public static void _iflt(String label) {
        out.println(String.format("%siflt %s", _indent, label));
    }

}
