import java.util.Arrays;

public class Strings {
    /** A class that contains common methods use to process strings in the compiler.
     *  All Strings have two double-quotes.
     */
    public static void print(String str) {
        // Prints either a String constant or a String variable
        str = str.replace("\"", "");

        if (Variables.isStringVar(str)) {
            String tmp = Translator.getNewTmpVar("char");
            int length = Integer.parseInt(Variables.getLength(str));

            for (int i = 0; i < length; i++) {
                Translator._applyAssign(tmp, String.format("%s[%s]", str, i));
                Translator._print(tmp, "string");
            }
        } else {
            if (!str.isEmpty()) {
                String[] list = str.split("");
                String expr;

                for (int i = 0; i < list.length; i++) {
                    expr = Variables.toVMType("'" + list[i] + "'");
                    Translator._print(expr, "string"); // as it's done as a String, it needs to be printed as String
                }
            }
            Translator._print("10", "string"); // prints a '\n'
        }
    }

    public static void assign(String id, String expr) {
        // Assigns either a String constant or other String var to ID. Ensures that the length is updated.
        String length;

        if (Variables.isStringVar(expr)) {
            length = Variables.getLength(expr);
            copy(id, expr);
        } else { // disjoint OR
            expr = expr.replace("\"", "");
            String ls[] = expr.split("");

            for (int i = 0; i < ls.length; i++) {
                String chr = Variables.toVMType("'" + ls[i] + "'"); // convert from char to int
                Translator._applyAssign(String.format("%s[%s]", id, i), chr);
            }
            Translator._applyAssign(String.format("%s[%s]", id, ls.length), "10"); // adds the '\n'
            length = String.valueOf(ls.length + 1);
        }

        Variables.updateString(id, length);
    }

    public static void copy(String strTo, String strFrom) {
        String temp = Translator.getNewTmpVar("char");
        int strFromLength = Integer.parseInt(Variables.getLength(strFrom));

        for (int i = 0; i < strFromLength; i++) {
            Translator._applyAssign(temp, strFrom+"["+i+"]");
            Translator._applyAssign(strTo+"["+i+"]", temp);
        }
    }
}
