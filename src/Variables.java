import org.w3c.dom.Attr;

import java.util.HashMap;
import java.util.Map;

class Attributes {
    private String type;
    private int size;

    public Attributes(String type, int size) {
        this.type = type;
        this.size = size;
    }

    public String type() { return this.type; }
    public int size() { return this.size; }

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
        variables.put(id, new Attributes(currentType, 0));
    }

    public static String declareTemp(String type, String size) {
        int sz = Integer.parseInt(size);
        String id = "t" + tmpVarCount++;

        variables.put(id, new Attributes(type, sz));
        return id;
    }

    public static void declareList(String id, String size) {
        int sz = Integer.parseInt(size);
        variables.put(id, new Attributes(currentType, sz));
    }

    public static void declareMatrix(String id) {

    }

    // ================================================================
    // ============================ CHECKERS ==========================

    public static boolean isDeclared(String id) {
        return variables.containsKey(id);
    }

    public static boolean isList(String id) {
        if (isDeclared(id)) {
            return variables.get(id).size() > 0;
        } else {
            return false;
        }
    }

    public static boolean isConst(String str) {
        return str.matches("0|[1-9][0-9]*");
    }

    public static int getSize(String id) {
        if (!isDeclared(id)) {
            Translator._errorTrace("Variables error " + id + ": Variable no declarada");
            System.exit(0);
        }
        return variables.get(id).size();
    }
}
