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

    public static void setCurrentType(String type) { currentType = type; }

    public static void declareVar(String id, String length) {
        variables.put(id, new Attributes(currentType, length));
    }

    public static void declareTemp(String id, String type, String len) {
        variables.put(id, new Attributes(type, len));
    }

    public static void updateString(String id, String length) {
        variables.replace(id, new Attributes("string", length));
    }
    // ================================= GETTERS =================================

    public static String getType(String id) {
        if (isDeclared(id)) {
            return variables.get(id).type;
        } else {
            if (isIntConst(id)) { return "int"; }
            else if (isCharConst(id)) { return "char"; }
            else { return "string"; }
            // no strong-checking: by default it returns "string"
        }
    }

    public static String getLength(String id) {
        Attributes _default = new Attributes("none", "0"); // returned if it's not a variable
        return variables.getOrDefault(id, _default).length;
    }

    // ================================= CHECKERS =================================

    public static void checkVar(String id) {
        if (!isDeclared(id)) {
            Translator._errorTrace("Error: Variable " + id + " no declarada");
        }
    }

    public static boolean isDeclared(String id) { return variables.containsKey(id); }

    public static boolean isTemporal(String id) { return id.matches("t[0-9]"); }

    public static boolean isIntConst(String str) { return str.matches("0|([1-9][0-9]*)"); }

    public static boolean isCharConst(String str) { return str.contains("\'") && !isArrayConst(str); }

    public static boolean isStringConst(String str) { return str.contains("\"") && !isArrayConst(str); }

    public static boolean isArrayConst(String str) { return str.contains(","); } // eg {1,2,3}

    public static boolean isArrayAccess(String str) { return str.contains("["); } // eg st[0]

    public static boolean isArray(String id) {
        Attributes _default = new Attributes("none", "0");
        return !variables.getOrDefault(id, _default).length.equals("0"); // only array if length != 0
    }

    public static boolean isCharVar(String id) {
        return (isDeclared(id) && getType(id) == "char");
    }

    public static boolean isIntVar(String id) {
        return (isDeclared(id) && getType(id) == "int");
    }

    public static boolean isStringVar(String id) {
        return (isDeclared(id) && getType(id) == "string");
    }

    public static boolean isChar(String str) {
        return isCharVar(str) || isCharConst(str);
    }

    public static boolean isInt(String str) {
        return isIntVar(str) || isIntConst(str);
    }

    public static boolean isString(String str) {
        return isStringVar(str) || isStringConst(str);
    }
}
