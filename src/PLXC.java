import java.io.*;

public class PLXC {
    /** Compilador del lenguaje PL(C) a codigo intermedio de tres direcciones. El lenguaje contiene
     *  * Expresiones relacionales de condicion
     *  * Expresiones logicas de condicion (AND, OR, NOT)
     *  * print
     *  * Sentencia condicional IF e IF-ELSE
     *  * Sentencia WHILE y DO-WHILE
     *  * Sentencia FOR
     *  * Fin de programa (HALT)
     *
     *  Ademas, el codigo intermedio no tiene instrucciones para
     *  * Menos UNARIO -> 0 - a
     *  * Solo condiciones (==) y (<)
     *  * Las etiquetas son representadas: bien (L0:) o bien (label L0;)
     */


    public static PrintStream out;

    public static void setTranslator(PrintStream Pout, PrintStream Perr) {
        Translator.out = Pout;
        Translator.err = Perr;
        Translator.debug = false;
        Translator.checkRanges = true;
    }

    public static void main(String argv[]) {
        try {
            Reader in = new InputStreamReader(System.in);
            out = System.out;

            in = (argv.length>0) ? new FileReader(argv[0]) : in;
            out = (argv.length>1) ? new PrintStream(new FileOutputStream(argv[1])) : out;

            setTranslator(out, out);


            parser p = new parser(new Yylex(in));
            Object result = p.parse().value;

        } catch (Exception e) {
            System.err.println("Exception found");
            e.printStackTrace();
        } catch (Error e) {
            System.err.println("Error found\n" + e.getMessage());
        }
    }
}
