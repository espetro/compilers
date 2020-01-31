public class Arrays {

    public static void init(String id, String init) {
        String type = Variables.getType(id);
        int length = Integer.parseInt(Variables.getLength(id));
        String[] values = init.split(",");

        if (length >= values.length) {

            for (int i = 0; i < values.length; i++) {
                if (Variables.getType(values[i]) != type) {
                    Translator._errorTrace("Array: error de tipos (" + id + ", " + values[i] + ")");
                }
                String to = String.format("%s[%s]", id, i);
                Translator._applyAssign(to, values[i]);
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
}
