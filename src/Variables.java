import java.util.HashMap;
import java.util.Map;

class Attributes {
    public static enum Type {None, Int, Float};
    protected String type;
    protected int scope;
    protected int size;

    public Attributes(String t, int scope, int size) {
        this.type = t;
        this.scope = scope;
        this.size = size;
    }

}

/**
 * This class allows to keep a runtime representation of the variables used to transpile code. It plays along with
 * a static instance of class Translator
 */
public class Variables {
    private static Map<String, Attributes> variables = new HashMap<>();
    private static String currentType = new String();

    public static void setCurrentType(String type) {
        switch(type) {
            case "int":
                currentType = "int";
                break;
            case "float":
                currentType = "float";
                break;
            default:
                Translator._errorTrace("Variable Error: CurrentType");
        }
    }

    public static void declareTempVar(String id, String size, String type) {
        int sz = Integer.parseInt(size);
        variables.put(id, new Attributes(type, 0, sz));
    }

    public static void declareVar(String id, String size) {
        int sz = Integer.parseInt(size);
        variables.put(id, new Attributes(currentType,0, sz));
    }

    public static boolean isDeclared(String id) {
        return variables.containsKey(id);
    }

    public static String getType(String id) {
        return variables.get(id).type;
    }
}
