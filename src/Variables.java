import org.w3c.dom.Attr;

import java.util.HashMap;
import java.util.Map;

class Attributes {
    protected String type;

    public Attributes(String type) {
        this.type = type;
    }
}

public class Variables {
    private static Map<String, Attributes> variables = new HashMap<>();
    private static String currentType = "none";

    public static void declareVar(String id) {
        variables.put(id, new Attributes(currentType));
    }

    public static void declareTemp(String id, String type) {
        variables.put(id, new Attributes(type));
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

    public static boolean isChar(String str) {
        return str.contains("\'");
    }

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
}
