public class Chars {
    /** A class that contains common methods use to process characters in the compiler.
     *  All Characters have two single-quotes.
     */

    public static final String defaultChar = "'\u0000'";

    public static char matchControl(String str) {
        switch (str) {
            case "\b": return '\b';
            case "\f": return '\f';
            case "\r": return '\r';
            case "\t": return '\t';
            case "\'": return '\'';
            case "\"": return '\"';
            case "\n": return '\n';
            default: return '\\';
        }
    }

    public static String toInt(String str) {
        char tmp = str.replace("'", "").charAt(0);
        return String.valueOf((int) tmp);
    }

    public static String fromInt(String num) {
        int n = Integer.parseInt(num);
        return "'" + String.valueOf((char) n) + "'";
    }
}
