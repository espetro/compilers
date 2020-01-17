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

    public static void declareArray(String id, String[] dims) {
        List<Integer> int_dims = new ArrayList<>();
        for (String str : dims) {
            int_dims.add(Integer.parseInt(str));
        }
        variables.put(id, new Attributes(currentType, int_dims));
    }

    // ================================================================
    // ======================== REGEX CHECKERS ========================

    public static boolean isConst(String str) {
        return str.matches("0|[1-9][0-9]*");
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
            Translator._errorTrace("CUP Error: variable " + id + " no declarada");
            System.exit(0);
        }
        return variables.get(id).getSize();
    }

    public static void checkArrayAssign(String id, String[] assign) {
//        int numDims = variables.get(id).getNumDims();
//
//        switch (numDims) {
//            case 0:
//                Translator._errorTrace("Array assign error: Trying to assign to non-array");
//                break;
//            case 1:
//                int size = variables.get(id).getSize();
//                if (assign.length > size) {
//                    Translator._errorTrace("Array assign error: las dimensiones no encajan con la inicializacion");
//                }
//                Translator._updateList(id, assign);
//                break;
//            default:
//                int size = variables.get(id).getSize(); // total length of the 1D array
//                if (assign.length > size || )
//                Translator._updateArray(id, assign, numDims);
//                break;
//        }
//        if(assign.length > sz) {
//            Translator._errorTrace("CUP Error: Array initialization length mismatch (ID[n] = {..})");
//        }
    }

    public static String checkArrayAccess(String id, String[] access) {
        Integer arrLength = getSize(id), arrN = variables.get(id).getNumDims();
        String t0 = new String();

        Translator._comment(String.format(" == arr %s: (length %s, dims %s) == ", id, arrLength, arrN));

        if (arrN > 1) { // i.e multidimensional array
            List<Integer> arrDims = variables.get(id).getDims();

            for (int i = 0; i < (access.length - 1); i++) {
                // Comprobar si el acceso se hace dentro del rango de la dimension arrDim[i]
                String iDim = arrDims.get(i).toString();
                Translator._checkRange(iDim, access[i]);

                // Calcula la posicion del N-Array en el array 1D que usa el compilador (usa saltos)
                // i < (access.length - 1) pues siempre accedemos al nextVal
                String nextDim = arrDims.get(i + 1).toString(),
                       nextVal = access[i + 1],
                       currVal = (i == 0) ? access[i] : t0;

                String t1 = Translator.arithmetic(nextDim, "*", currVal, false);
                t1 = Translator.arithmetic(t1, "+", nextVal, true);
                t0 = t1;
            }
        } else { // i.e 1D array - list
            Translator._checkRange(arrLength.toString(), access[0]);
            t0 = access[0];
        }

        Translator.logging.println("(checkArrayAccess) " + t0);
        return t0;
    }
}
