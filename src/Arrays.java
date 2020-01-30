public class Arrays {

    public static boolean isArray(String id) {
        return Variables.isDeclared(id) && Variables.getLength(id) != "0";
    }

    public static void init(String id, String list) {
        if (isArray(id)) {
            int length = Integer.parseInt(Variables.getLength(id));
            String type = Variables.getType(id);
            String[] values = list.split(",");
            if (length >= values.length) {
                // Case 1: The init list is smaller or equal than the array length
                String temp = Translator.getNewTmpVar(type);

                for (int i = 0; i < values.length; i++) {
                    String valType = Variables.getType(values[i]);

                    if (type != valType) {
                        // Case 1.1: An init list's value's type and array's type don't match => ERROR
                        Translator._errorTrace("Error de tipos al inicializar el Array " + id);
                    } else {
                        // Case 1.2: Types match
                        String val = (valType == "char") ? Char.toInt(values[i]) : values[i]; // if it's a char => toInt
                        // Translator._applyAssign(temp, val);
                        Translator._applyAssign(id + "[" + i + "]", val);
                    }
                }
            } else {
                // Case 2: The init list is longer than the array length => ERROR
                Translator._errorTrace("Error: La longitud de las listas " + id + " " + list + " no son iguales");
            }
        }
    }

    public static void check(String id, String pos) {
        int length = Integer.parseInt(Variables.getLength(id));

        if (Variables.isIntConstant(pos)) {
            int access = Integer.parseInt(pos);
            if (length <= access) {
                Translator._errorTrace("ArrayAccessError (" + id + ", " + pos + ")");
            }
        } else {
            Condition cond = new Condition();

            Translator._comment("Comprobacion de rango");
            Translator._if(pos + " < 0", cond.FalseLabel());
            Translator._if(length + "< " + pos, cond.FalseLabel());
            Translator._if(length + " == " + pos, cond.FalseLabel());
            Translator._goto(cond.TrueLabel());

            Translator._label(cond.FalseLabel());
            Translator._error();
            Translator._halt();
            Translator._label(cond.TrueLabel());
        }
    }

    public static void copy(String arrTo, String arrFrom) {
        int lengthTo = Integer.parseInt(Variables.getLength(arrTo));
        int lengthFrom = Integer.parseInt(Variables.getLength(arrFrom));

        if (lengthTo >= lengthFrom) {
            String temp = Translator.getNewTmpVar(Variables.getType(arrTo));

            for (int i = 0; i < lengthFrom; i++) {
                Translator._applyAssign(temp, arrFrom+"["+i+"]");
                Translator._applyAssign(arrTo+"["+i+"]", temp);
            }
        } else {
            Translator._errorTrace("Los arrays " + arrTo + ", " + arrFrom + " no tienen las mismas dimensiones");
        }
    }
}
