import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JPLC {
    /** Compilador del lenguaje JPL(C) a codigo intermedio de tres direcciones. El lenguaje contiene
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
        Translator.logging = System.out;
    }

    public static void main(String argv[]) {
        try {
            Reader in = new InputStreamReader(System.in);
            out = System.out;

            in = (argv.length>0) ? new FileReader(argv[0]) : in;
            out = (argv.length>1) ? new PrintStream(new FileOutputStream(argv[1])) : out;

            setTranslator(out, out);

            // ==== Escribe jplcore.j en el output (antes que la salida del parser!) ==== (SE HACE EN scripts/jpl.bat)
            // Stream<String> core = Files.lines(Paths.get("app/in/fixed/jplcore.j"));
            // core.forEach(out::println);
            // core.close();

            parser p = new parser(new Yylex(in));
            Object result = p.parse().value;

            // Para probar la ejecucion => scripts/jpl.bat $fichero_en_out $input

        } catch (IOException e) {
            System.err.println("Error al leer un archivo:");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception found");
            e.printStackTrace();
        } catch (Error e) {
            System.err.println("Error found\n" + e.getMessage());
        }
    }
}
