import java.util.Arrays;

public class Strings {
    /** A class that contains common methods use to process strings in the compiler.
     *  All Strings have two double-quotes.
     */
    public static void print(String str) {
        // Prints either a String constant or a String variable
        if (Variables.isStringVar(str)) {
            String tmp = Translator.getNewTmpVar("char");
            int length = Integer.parseInt(Variables.getLength(str));

            for (int i = 0; i < length; i++) {
                Translator._applyAssign(tmp, String.format("%s[%s]", str, i));
                Translator._print(tmp, "string");
            }
        } else {
            str = str.replace("\"", "") + "\n"; // Adds an EOL character at the end (see PLX specification)
            String[] list = str.split(""); // take out DOUBLE QUOTES!!
            String expr;

            // System.out.println("Input list is " + Arrays.toString(list) + " " + str + " " + Arrays.toString(str.toCharArray()));

            for (int i = 0; i < list.length; i++) {
                expr = Chars.toInt(list[i]);
                Translator._print(expr, "string");
            }
        }
    }

    public static void assign(String id, String expr) {
        // Assigns either a String constant or other String var to ID. Ensures that the length is updated.
        String length;

        if (Variables.isStringVar(expr)) {
            length = Variables.getLength(expr);
            copy(id, expr);
        } else { // disjoint OR
            expr = expr.replace("\"", "") + "\n";
            String ls[] = expr.split("");

            for (int i = 0; i < ls.length; i++) {
                Translator._applyAssign(String.format("%s[%s]", id, i), ls[i]);
            }
            length = String.valueOf(ls.length);
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
