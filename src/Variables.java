import org.w3c.dom.Attr;

import java.util.HashMap;
import java.util.Map;

class Attributes {
    protected String type;
    protected String length;

    public Attributes(String type, String length) {
        this.type = type;
        this.length = length;
    }
}

public class Variables {
    private static Map<String, Attributes> variables = new HashMap<>();
    private static String currentType = "none";

    public static void declareVar(String id, String dim) {
        String length = dim.isEmpty() ? "0" : dim;
        variables.put(id, new Attributes(currentType, length));
    }

    public static void declareTemp(String id, String type) {
        variables.put(id, new Attributes(type, "0"));
    }
    public static void setCurrentType(String type) {
        currentType = type;
    }

    public static void checkVar(String id) {
        // Checks if 'id' is already declared
        if(!variables.containsKey(id)) {
            Translator._errorTrace("Variable " + id + " no declarada");
        }
    }

    public static boolean isDeclared(String id) {
        return variables.containsKey(id);
    }

    public static boolean isTemporal(String id) { return id.matches("t[0-9]+"); }

    public static boolean isChar(String str) {
        return str.contains("\'");
    }

    public static boolean isArrayExpr(String str) { return str.contains("["); }

    public static boolean isArray(String str) {
        // 'str' is an array if it's a variable and its length is not 0
        return isDeclared(str) && !variables.get(str).length.equals("0");
    }

    public static boolean isIntConstant(String str) { return str.matches("0|([1-9][0-9]*)"); }

    public static String getType(String expr) {
        // Gets the type of the given expr (either a constant or a variable)
        String type;
        if (isDeclared(expr)) {
            type = variables.get(expr).type;
        } else {
            type = isChar(expr) ? "char" : "int";
        }
        return type;
    }

    public static String getLength(String id) {
        return isDeclared(id) ? variables.get(id).length : "0";
    }
}
