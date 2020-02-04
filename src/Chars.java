public class Chars {
    /** A class that contains common methods use to process characters in the compiler.
     *  All Characters have two single-quotes.
     */

    public static final char defaultChar = '\u0000';

    public static char matchControlNoBackslash(String str) {
        switch (str) {
            case "\b":
            case "b":
                return '\b';
            case "\f":
            case "f":
                return '\f';
            case "\r":
            case "r":
                return '\r';
            case "\t":
            case "t":
                return '\t';
            case "\n":
            case "n":
                return '\n';
            case "\'": return '\'';
            case "\"": return '\"';
            case "\\": return '\\';
            default: return defaultChar;
        }
    }

    public static char matchControl(String str) {
        switch (str) {
            case "\b": return '\b';
            case "\f": return '\f';
            case "\r": return '\r';
            case "\t": return '\t';
            case "\'": return '\'';
            case "\"": return '\"';
            case "\n": return '\n';
            case "\\": return '\\';
            default: return defaultChar;
        }
    }

    public static String toInt(String str) {
        str = str.replace("'", "");
        str = str.length() > 1 ? str.trim() : str;  // dont delete whitespace if it's the only char (whitespaces can also be printed!)
        char res, maybeSpecial = matchControl(str);

        if (maybeSpecial == defaultChar) {
            // not-an-special
            res = str.charAt(0);
        } else {
            res = maybeSpecial;
        }
        return String.valueOf((int) res);
    }

    public static String fromInt(String num) {
        int n = Integer.parseInt(num);
        return "'" + String.valueOf((char) n) + "'";
    }
}
