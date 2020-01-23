public class Compiler {

    public static void main(String[] args) throws Exception {
        String[] JFlexOpts = { "app/src/JPLC.flex" };
        String[] CupOpts = {"-destdir", "app/src", "app/src/JPLC.cup" };
        jflex.Main.generate(JFlexOpts);
        java_cup.Main.main(CupOpts);
    }
}
