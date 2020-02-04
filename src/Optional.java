import javax.swing.text.html.Option;
import java.util.NoSuchElementException;

public class Optional<T> {
    // Overwrite of the java.util class Optional<T>

    private static final Optional<?> EMPTY = new Optional<>(null);
    private T value;

    public static<T> Optional<T> empty() {
        Optional<T> t = (Optional<T>) EMPTY;
        return t;
    }

    private Optional(T value) { this.value = value; }

    public static<T> Optional<T> of(T val) {
        return new Optional<>(val);
    }

    public boolean isEmpty() { return value == null; }
    public boolean isPresent() { return value != null; }

    public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }
}
