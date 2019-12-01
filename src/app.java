import java.io.FileReader;

public class app {
//    private final String fnameOut = "app/out/result.out";

    public static void setTranslator() {
        Translator.out = System.out;
        Translator.err = System.err;
        Translator.tab = "";
    }

    public static void main(String[] args) throws Exception {
        String fnameIn = "app/in/g1.pl";
        System.out.println("Input file: " + fnameIn);

        setTranslator();

        Lexer scanner = new Lexer(new FileReader(fnameIn));
        parser p = new parser(scanner);

        Object result = p.parse().value;
    }
}
