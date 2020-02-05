public class Chars {
    /** A class that contains common methods use to process characters in the compiler.
     *  All Characters have two single-quotes.
     */

    public static final char defaultChar = '\u0000';

    public static String stripQuotes(String str) {
        // returns the string as-it-is, without the leading and trailing \' that identify the Char type
        if (str.charAt(0) != '\'' || str.charAt(str.length() - 1) != '\'') {
            throw new RuntimeException("Error at stripping Char quotes: " + str);
        }
        return str.substring(1, str.length() - 1);
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
        str = stripQuotes(str);
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
