import java.util.HashMap;
import java.util.Map;

class Attributes {
    public String type;
    public int pointerLevel;
    public int derefLevel;

    public Attributes(String tp, int ptrLevel, int drfLevel) {
        this.type = tp;
        this.pointerLevel = ptrLevel;
        this.derefLevel = drfLevel;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s, %s)", this.type, this.pointerLevel, this.derefLevel);
    }

    @Override
    public boolean equals(Object other) {
        Attributes _other = (Attributes) other;
        return this.type == _other.type && this.pointerLevel == _other.pointerLevel && this.derefLevel == _other.derefLevel;
    }
}

public class Variables {
    private static Map<String, Attributes> variables = new HashMap<>();
    private static String currentType = new String();
    private static int pointerLevel = 0;
    private static int derefLevel = 0;

    // ============================================================
    // ================== STATIC SETTER / GETTERS =================

    public static void incPointerLevel() { pointerLevel++; }
    public static void decPointerLevel() { pointerLevel--; }
    public static void resetPointerLevel() { pointerLevel = 0; }
    public static void incDerefLevel() { derefLevel++; }

    public static void decDerefLevel() { derefLevel--; }
    public static void resetDerefLevel() { derefLevel = 0; }

    public static int getPointerLevel() { return pointerLevel; }
    public static int getPointerLevel(String id) { return variables.get(id).pointerLevel; }
    public static int getDerefLevel() { return derefLevel; }
    public static int getDerefLevel(String id) { return variables.get(id).derefLevel; }

    public static void setCurrentType(String type) {
        currentType = type;
    }

    // ============================================================
    // ================== VARIABLE DECLARATIONS ===================

    public static void declareVar(String id) {
        variables.put(id, new Attributes(currentType, 0, 0));
    }

    public static void declareTmpVar(String id, String type, boolean isPointer, boolean isDeref) {
        int ptrLevel = isPointer ? pointerLevel : 0;
        int drfLevel = isDeref ? derefLevel : 0;
        variables.put(id, new Attributes(type, ptrLevel, drfLevel));
    }

    public static void declarePointer(String id) {
        variables.put(id, new Attributes(currentType, pointerLevel, 0));
    }

    public static void updateVar(String id, String type, boolean isPointer, boolean isDeref) {
        int ptrLevel = isPointer ? pointerLevel : 0;
        int drfLevel = isDeref ? derefLevel : 0;
        variables.replace(id, new Attributes(type, ptrLevel, drfLevel));
    }

    // ==================================================
    // ==================== CHECKERS ====================

    public static boolean isValid(String str) {
        // A string is valid if it's either a declared variable, a pointer, a de-referenced value or a constant
        return isIntConst(str) || isVariable(str) || isPointer(str) || isDeref(str);
    }

    public static boolean isPointer(String id) {
        // A string is a pointer if it's a variable and its pointer level is greater than 0
        String _ID = id.replace("*", "");
        if (variables.containsKey(_ID)) { // safe check if it's a variable
            return variables.get(_ID).pointerLevel > 0;
        } else {
            return false;
        }
    }

    public static boolean isDeref(String id) {
        // A string is a dereferenced value if its dereference level is greater than 0
        String _ID = id.replace("&", "");
        if (variables.containsKey(_ID)) { // safe check if it's a variable
            return variables.get(_ID).derefLevel > 0;
        } else {
            return false;
        }
    }

    public static boolean isVariable(String id) {
        // A string is a variable if it starts by a letter and it's declared
        return isVariableExpr(id) && variables.containsKey(id);
    }

    public static boolean isTemporalExpr(String str) { return str.matches("t[0-9]+"); }
    public static boolean isVariableExpr(String str) { return str.matches("[a-zA-Z_][a-zA-Z0-9_]*"); }
    public static boolean isIntConst(String str) { return str.matches("0|[1-9][0-9]*"); }

    // ==================================================
    // ==================== OVERWRITES ==================

    @Override
    public String toString() {
        return variables.toString();
    }
}
