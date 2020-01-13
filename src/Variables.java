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

    public static boolean isValid(String str) { return true; }

    public static boolean isPointer(String str) {
        return true;
    }

    public static boolean isIntConst(String str) { return str.matches("0|[1-9][0-9]*"); }
    public static boolean isInt(String str) { return true; }
}
