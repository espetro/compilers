import java.util.List;

public class Arrays {
    /**
     * Utility class to simplify action code for arrays
     */

    public static void insertOrUpdate(String id, String[] assign) {
        Integer arrLength = Variables.getSize(id),
                arrN = Variables.getNumDims(id);
        List<Integer> arrDims = Variables.getDimsList(id);

        // assign is a list of temporal arrays (for ND Arrays) or expressions (for 1D Arrays)

        // 1st check: if the list of temporal arrays matches the root size of ID (inner lengths are checked at 'init_list' in CUP)
        // as of 1D arrays, we can do "int a[5] = {1,2,3} or {1..5}", but we can't do int "a[5] = {1..6}"
        if (arrDims.get(0) < assign.length) {
            String info = String.format("Error: dimensiones de los arrays (%s, %d) y (%s, %d)",
                    id, arrDims.get(0), print(assign), assign.length);
            Translator._errorTrace(info);
        }

        // 2nd check: multidimensional or 1D array?
        if (arrN > 1) {
            // check if the total size of the temporal arrays matches that of ID
            String pos, aux = Variables.declareTemp("int");

            for (int assignPos = 0; assignPos < assign.length; assignPos++) {
                String tmpArr = assign[assignPos];
                int tmpSize = Variables.getSize(tmpArr);

                for (int valPos = 0; valPos < tmpSize; valPos++) {
                    pos = String.valueOf(valPos + (tmpSize * assignPos));
                    // this ensures the inner arrays are flattened into the 1D-ID

                    Translator._applyAssign(aux, tmpArr + "[" + valPos + "]");
                    Translator._applyAssign(id + "[" + pos + "]", aux);
                }
            }
        } else {
            Translator._updateArray(id, assign);
        }
    }

    public static void copyArray(String idTo, String idFrom) {
        // An array can only be copied to another array of equal or bigger capacity
//        Translator.logging.println(
//            String.format(
//                "Copying array %s[%d] to array %s[%d]",
//                idFrom, Variables.getSize(idFrom), idTo, Variables.getSize(idTo)
//            )
//        );

        if (Variables.getSize(idTo) >= Variables.getSize(idFrom)) { // x = y iff size(x) >= size(y)
            String eTo, eFrom, t0 = Variables.declareTemp("int");
            int size = Variables.getSize(idFrom);

            for (int i = 0; i < size; i++) {
                eTo = String.format("%s[%s]", idTo, i);
                eFrom = String.format("%s[%s]", idFrom, i);
                Translator._applyAssign(t0, eFrom);
                Translator._applyAssign(eTo, t0);
            }
        } else {
            Translator._errorTrace("CUP Error " + idTo + "," + idFrom + ": La capacidad de las listas no coinciden");
        }
    }

    public static String getPos(String id, String[] access) {
        String t0 = new String();
        Integer arrLength = Variables.getSize(id),
                arrN = Variables.getNumDims(id);

        if (arrN > 1) { // i.e multidimensional array
            List<Integer> arrDims = Variables.getDimsList(id);

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

//        Translator.logging.println("(checkArrayAccess) " + t0);
        return t0;
    }

    public static String print(String[] arr) {
        String res = "";
        for (String str : arr) {
            res += str + " ";
        }
        return res;
    }

    // eg t0 = [2][3] => "2,3" => ["2","3"]
    public static String[] getDimsFromLiteral(String[] arr) {
        // gets the total length of the current + inner temporal arrays
        String dims = String.valueOf(arr.length);

        if (!Variables.isConst(arr[0])) {
            // arguably all inner arrays are of the same size
            dims += "," + Variables.getDims(arr[0]);
        }

        return dims.split(",");
    }
}
