public class Compiler {

    public static void main(String[] args) throws Exception {
        String[] JFlexOpts = { "app/src/PLXC.flex" };
        String[] CupOpts = {"-destdir", "app/src", "app/src/PLXC.cup" };
        jflex.Main.generate(JFlexOpts);
        java_cup.Main.main(CupOpts);
    }
}
