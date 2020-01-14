/**
 * This class allows to create and modify 3AC labels eg. L0, L1, ..
 */
public class Condition {
    private String trueLabel;
    private String falseLabel;
    private String nextLabel;

    /**
     * Creates a new pair of labels (trueLabel, falseLabel)
     */
    public Condition() {
        this.trueLabel = Translator.getNewLabel();
        this.falseLabel = Translator.getNewLabel();
        this.nextLabel = "";
    }

    public Condition(String nextTag) {
        // Builds a Switch-based condition with a "pointer" for the next "case" tag
        this.trueLabel = Translator.getNewLabel();
        this.falseLabel = Translator.getNewLabel();
        this.nextLabel = nextTag;
    }

    public String TrueLabel() {
        return this.trueLabel;
    }

    public String FalseLabel() { return this.falseLabel; }

    public void setTrueLabel(String newT) {
        this.trueLabel = newT;
    }

    public void setFalseLabel(String newF) {
        this.falseLabel = newF;
    }

    /**
     * (trueLabel, falseLabel) -> (falseLabel, trueLabel)
     */
    public void permute() {
        String aux = this.trueLabel;
        this.trueLabel = this.falseLabel;
        this.falseLabel = aux;
    }
}
