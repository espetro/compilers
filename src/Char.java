public class Char {
    public static String toInt(String expr) {
        // All characters are a single expression between single-quotes
        // e.g. '\u0040', '\b', 'A', '!', ...
        String result = String.valueOf((int) expr.replace("'", "").charAt(0));
        System.out.println("Casting to (int) char " + expr + " : " + result);

        return result;
    }

    public static String fromInt(String number) {
        return "'" + String.valueOf((char) Integer.parseInt(number)) + "'";
    }
}
