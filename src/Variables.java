import java.util.HashMap;
import java.util.Map;

class Attributes {
    private int size;
    private String type;

    public Attributes(int sz, String tp) {
        this.size = sz;
        this.type = tp;
    }
}

public class Variables {
    private static Map<String, Attributes> variables = new HashMap<>();
    private static String currentType = new String();

    // ============================================================
    // ================== VARIABLE DECLARATIONS ===================

    public static void setCurrentType(String type) {
        currentType = type;
    }

    public static void declareVar(String id, String size) {
        int sz = Integer.parseInt(size);
        variables.put(id, new Attributes(sz, currentType));
    }

    public static void declareTmpVar(String id, String size, String type) {
        int sz = Integer.parseInt(size);
        variables.put(id, new Attributes(sz, type));
    }

    // ==================================================
    // ==================== CHECKERS ====================

    public static boolean isValid(String str) {
        // A string is valid if it's either a declared variable or pointer, or a constant
        if (isVariable(str)) {
            return variables.containsKey(str);
        }
        else if (isPointer(str)) {
            return true; // TBD
        }
        else if (isIntConst(str)) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean isPointer(String str) {
        return true;
    }

    public static boolean isTemporal(String str) {
        return str.matches("t[0-9]+");
    }

    public static boolean isVariable(String str) {
        return str.matches("[a-zA-Z_][a-zA-Z0-9_]*");
    }

    public static boolean isIntConst(String str) { return str.matches("0|[1-9][0-9]*"); }
    public static boolean isInt(String str) { return true; }
}
