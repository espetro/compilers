import java_cup.*;

public class Compiler {

    public static void main(String[] args) throws Exception {
        String[] JFlexOpts = { "app/src/lexer.flex" };
        String[] CupOpts = {"-destdir", "app/src", "app/src/parser.cup" };
        jflex.Main.generate(JFlexOpts);
        java_cup.Main.main(CupOpts);
    }
}
