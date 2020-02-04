public class Arrays {

    public static String parse(String init) {
        // Parses an init list and creates a temporal holding it. The type has to be consistent in the init list.
        // Returns a tuple the temporal_id

        String[] list = init.split(",");
        String type = Variables.getType(list[0]),
               temp = Translator.getNewTmpArr(type, String.valueOf(list.length));

        // System.out.println("Array type is " + type + " and length " + list.length + " " + init);
        String expr;

        for (int i = 0; i < list.length; i++) {
            if (!Variables.getType(list[i]).equals(type)) { // type isn't consistent => ERROR
                Translator._errorTrace("El tipo de la lista " + init + " no es consistente");
            }

            expr = list[i];
            if (Variables.isChar(list[i])) {
                expr = Chars.toInt(expr);
            }

            Translator._applyAssign(String.format("%s[%s]", temp, i), expr);
        }

        return temp;
    }
    public static void init(String id, String init) {
        int length = Integer.parseInt(Variables.getLength(id));
        String val, type = Variables.getType(id);
        String[] values = init.split(",");

        if (length >= values.length) {

            for (int i = 0; i < values.length; i++) {
                if (Variables.getType(values[i]) != type) {
                    Translator._errorTrace("Array: error de tipos (" + id + ", " + values[i] + ")");
                }
                String to = String.format("%s[%s]", id, i);
                val = Variables.toVMType(values[i]);
                Translator._applyAssign(to, val);
            }
        } else {
            Translator._errorTrace("Las dimensiones no coinciden (" + id + ", " + init + ")");
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

    public static void check(String id, String pos) {
        int length = Integer.parseInt(Variables.getLength(id));

        if (Variables.isIntConst(pos)) {
            int access = Integer.parseInt(pos);
            if (length <= access) {
                Translator._errorTrace("ArrayAccessError (" + id + ", " + pos + ")");
            }
        } else {
            Condition cond = new Condition();

            Translator._comment("Comprobacion de rango");
            Translator._if(pos + " < 0", cond.FalseLabel());
            Translator._if(length + " < " + pos, cond.FalseLabel());
            Translator._if(length + " == " + pos, cond.FalseLabel());
            Translator._goto(cond.TrueLabel());

            Translator._label(cond.FalseLabel());
            Translator._error();
            Translator._halt();
            Translator._label(cond.TrueLabel());
        }
    }

    public static void print(String id, String type) {
        String tmp = Translator.getNewTmpVar(type);
        int length = Integer.parseInt(Variables.getLength(id));

        for (int i = 0; i < length; i++) {
            Translator._applyAssign(tmp, String.format("%s[%s]", id, i));
            Translator._print(tmp, type);
        }
    }
}
