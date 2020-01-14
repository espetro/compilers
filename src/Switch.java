public class Switch {
    private String endLabel;
    private String nextCaseLabel;
    private String varArg;

    public Switch() {
        this.endLabel = Translator.getNewLabel();
        this.nextCaseLabel = "";
        this.varArg = "";
    }

    public String getEndLabel() { return this.endLabel; }
    public String getVarArg() { return this.varArg; }
    public String getNextCaseLabel() { return this.nextCaseLabel; }

    public void setVarArg(String newVar) { this.varArg = newVar; }
    public void setNextCaseLabel(String newL) { this.nextCaseLabel = newL; }

    public void printGotoIfBreak(String br) {
        // Prints the usual 'goto' at the end of each 'case' depending on the 'break' presence
        String newLabel = Translator.getNewLabel();

        if (br == "break") { Translator._goto(this.endLabel); } // go to END
        else { Translator._goto(newLabel); } // no break

        this.nextCaseLabel = newLabel;
    }
}
