import java.io.FileReader;

public class app {
    private final String fnameOut = "app/out/result.out";
    public static void main(String[] args) throws Exception {
        String fnameIn = "app/in/a0.pypl";
        System.out.println("Input file: " + fnameIn);

        Lexer scanner = new Lexer(new FileReader(fnameIn));
        parser p = new parser(scanner);

        Object result = p.parse().value;
    }
}
