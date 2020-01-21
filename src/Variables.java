import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Attributes {
    private String type;
    private List<Integer> dims;

    public Attributes(String type) {
        this.type = type;
        this.dims = new ArrayList<>();
    }

    public Attributes(String type, List<Integer> dims) {
        this.type = type;
        this.dims = dims;
    }

    public String getType() { return this.type; }

    public int getSize() {
        // 1 if it's not an array, 2 or more if it is
        int prod = 1;
        for (Integer n : this.dims) {
            prod *= n;
        }
        return prod;
    }

    public int getNumDims() {
        // 0 if it's not an array, 1 or more if it is
        return this.dims.size();
    }

    public List<Integer> getDims() {
        return this.dims;
    }

    public boolean isArray() {
        return this.dims.size() > 0;
    }
}

public class Variables {
    private static Map<String, Attributes> variables = new HashMap<>();
    private static String currentType = new String();
    private static int tmpVarCount = 0;

    // ================================================================
    // ================= DECLARATIONS / GLOBAL CLASSES ================

    public static void setCurrentType(String type) {
        currentType = type;
    }

    public static void declareVar(String id) {
        variables.put(id, new Attributes(currentType));
    }

    public static String declareTemp(String type) {
        String id = "t" + tmpVarCount++;
        variables.put(id, new Attributes(type));
        return id;
    }

    public static String declareTempList(String type, String[] dims) {
        // Declares a multidimensional temporal array
        String id = "t" + tmpVarCount++;
        List<Integer> intDims = new ArrayList<>();

        for (String str : dims) {
            intDims.add(Integer.parseInt(str));
        }

        variables.put(id, new Attributes(type, intDims));
        return id;
    }

    public static void declareArray(String id, String[] dims) {
        List<Integer> intDims = new ArrayList<>();
        for (String str : dims) {
            intDims.add(Integer.parseInt(str));
        }
        variables.put(id, new Attributes(currentType, intDims));

        Translator._comment(
                String.format(" == arr %s: (length %s, dims %s) == ", id, variables.get(id).getSize(), intDims.size())
        );
    }

    // ================================================================
    // ======================== REGEX CHECKERS ========================

    public static boolean isConst(String str) {
        return str.matches("0|[1-9][0-9]*");
    }

    public static boolean isVariableExpr(String str) {
        return str.matches("[a-zA-Z][a-zA-Z0-9_]*");
    }

    // ================================================================
    // ======================== VARIABLE CHECKERS =====================

    public static boolean isDeclared(String id) {
        return variables.containsKey(id);
    }

    public static void checkID(String id) {
        if(!isDeclared(id)) {
            Translator._errorTrace("CUP Error: variable " + id + " no declarada");
        }
    }

    public static boolean isArray(String id) {
        // id is an array if it's declared and it's dims are > 0
        return isDeclared(id) && variables.get(id).isArray();
    }

    public static int getSize(String id) {
        if (!isDeclared(id)) {
            Translator._errorTrace("(getSize) CUP Error: variable " + id + " no declarada");
            System.exit(0);
        }
        return variables.get(id).getSize();
    }

    public static int getNumDims(String id) {
        if (!isDeclared(id)) {
            Translator._errorTrace("(getSize) CUP Error: variable " + id + " no declarada");
            System.exit(0);
        }
        return variables.get(id).getNumDims();
    }

    public static String getDims(String id) {
        // returns a String in order to be able to compare
        if(!isDeclared(id)) {
            Translator._errorTrace("(getDims) CUP Error: variable " + id + " no declarada");
            System.exit(0);
        }

        String res = variables.get(id).getDims().toString()
                              .replace("[","")
                              .replace("]","");
        return res;
    }

    public static List<Integer> getDimsList(String id) {
        if(!isDeclared(id)) {
            Translator._errorTrace("(getDims) CUP Error: variable " + id + " no declarada");
            System.exit(0);
        }
        return variables.get(id).getDims();
    }
}
