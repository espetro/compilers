/**
 * This class allows to create and modify 3AC labels eg. L0, L1, ..
 */
public class Condition {
    private String trueLabel;
    private String falseLabel;

    /**
     * Creates a new pair of labels (trueLabel, falseLabel)
     */
    public Condition() {
        this.trueLabel = Translator.getNewLabel();
        this.falseLabel = Translator.getNewLabel();
    }

    public String TrueLabel() {
        return trueLabel;
    }

    public String FalseLabel() {
        return falseLabel;
    }

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
