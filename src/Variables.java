import org.w3c.dom.Attr;

import java.util.HashMap;
import java.util.Map;

class Attributes {
    private String type;

    public Attributes(String type) {
        this.type = type;
    }

    public String type() { return this.type; }

}

public class Variables {
    private static Map<String, Attributes> variables = new HashMap<>();
    private static String currentType = new String();
    private static int tmpVarCount = 0;

    // ================================================================
    // ================= DECLARATIONS / GLOBAL MODIFIERS ==============

    public static void setCurrentType(String type) {
        currentType = type;
    }

    public static void declareVar(String id) {
        variables.put(id, new Attributes(currentType));
    }

    public static String declareTempVar(String type) {
        String id = "t" + tmpVarCount++;
        variables.put(id, new Attributes(type));
        return id;
    }

    public static void declareList(String id) {

    }

    public static void declareMatrix(String id) {

    }

    // ================================================================
    // ============================ CHECKERS ==========================

    public static boolean isDeclared(String id) {
        return variables.containsKey(id);
    }

}
