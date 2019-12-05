import java_cup.*;

public class Compiler {

    public static void main(String[] args) throws Exception {
        String[] JFlexOpts = { "app/src/PYPLC.flex" };
        String[] CupOpts = {"-destdir", "app/src", "app/src/PYPLC.cup" };
        jflex.Main.generate(JFlexOpts);
        java_cup.Main.main(CupOpts);
    }
}
