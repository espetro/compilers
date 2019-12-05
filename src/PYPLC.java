import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class PYPLC {
	public static PrintStream out;

	public static void setTranslator(PrintStream Pout, PrintStream Perr) {
	    Translator.out = Pout;
	    Translator.err = Perr;
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
