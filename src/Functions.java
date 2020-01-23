import java.util.*;


class Function {
    private String arg;
    private boolean didReturn;
    private List<String> variables;
    private Stack<String> temporals; // A Last-In-First-Out (LIFO) List

    public Function(String arg) {
        // Creates a Function watcher with its Operation's Stack and Variable's Area
        this.arg = arg;
        this.didReturn = false;
        this.temporals = new Stack<>();
        this.variables = new ArrayList<>();
        this.variables.add(arg); // arg is always at pos 0 of the local variable's stack
    }

    public int getNumLocals() {
        return this.variables.size(); // at least 1 (for arg)
    }

    public int getNumStack() {
        return this.temporals.size(); // for the number of operations (ACTUALLY THIS IS NOT RIGHT)
    }

    public void enableReturn() {
        this.didReturn = true;
    }

    public boolean getReturn() {
        return this.didReturn;
    }

    public void declare(String id) {
        // Declares a variable
        if (this.variables.contains(id)) {
            Translator.errorTrace("Variable ya declarada");
        } else {
            this.variables.add(id);
            String idx = indexOf(id);
            // PUSH - STORE (STORE ALREADY DOES A POP)
            Translator._push("0");
            Translator._store(idx);
            Translator._blank(); // READABILITY
        }
    }

    public boolean isDeclared(String id) {
        return this.variables.contains(id);
    }

    public String indexOf(String id) {
        return String.valueOf(this.variables.indexOf(id));
    }
}

public class Functions {
    private static Map<String, Function> funTable = new HashMap<>();
    private static Function currFun = null;
    private static final String type = "int";

    // ==========================================
    // ============== DECLARATIONS ==============

    public static void createFun(String id, String optArgs) {
        // Stores the function and sets it as the current one
        // Also prints the default function messages
        Function fun = new Function(optArgs);
        funTable.put(id, fun);
        currFun = fun;

        Translator._startMethod(id);
        // return fun;
    }

    public static void declareLocal(String id) {
        // Declares a local variable in the current active function
        checkNullFun();
        currFun.declare(id);
    }

    // ==========================================
    // ================ ROUTINES ================

    public static void enableReturn(String expr) {
        // Enables the return on currFun
        checkNullFun();
        currFun.enableReturn();
    }

    // ==========================================
    // ================ CHECKERS ================

    public static void checkNullFun() {
        // checks if currFun has been initialized
        if (currFun == null) {
            Translator.errorTrace("ERROR: Instruccion fuera de una funcion");
        }
    }
    public static boolean didReturn() {
        // Check if currFun did return
        checkNullFun();
        return currFun.getReturn();
    }

    public static boolean isDeclared(String id) {
        // Checks if it is declared in currFun
        checkNullFun();
        return currFun.isDeclared(id);
    }

    // ==========================================
    // ================= GETTERS ================

    public static String indexOf(String id) {
        checkNullFun();
        return currFun.indexOf(id);
    }
}
