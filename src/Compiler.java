public class Compiler {

    public static void main(String[] args) throws Exception {
        String[] JFlexOpts = { "app/src/PLC.flex" };
        String[] CupOpts = {"-destdir", "app/src", "app/src/PLC.cup" };
        jflex.Main.generate(JFlexOpts);
        java_cup.Main.main(CupOpts);
    }
}
