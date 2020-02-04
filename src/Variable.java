import java.util.ArrayList;
import java.util.List;

public class Variable {
    private String id;
    private List<Integer> levels;

    public Variable(String _id, int blocklevel) {
        this.id = _id;
        this.levels = new ArrayList<>();
        this.levels.add(blocklevel);
    }

    public void add(int blocklevel) {
        this.levels.add(blocklevel);
    }

    public void clear(int blocklevel) {
        this.levels.removeIf(x -> x == blocklevel);
    }

    public boolean atLevel(int blocklevel) {
        return this.levels.contains(blocklevel);
    }

    public Optional<Tuple> getAt(int blocklevel) {
        // returns a Tuple of (variable ID, printable variable String)
        Optional<Tuple> result;
        String printable;
        Integer maxLevel = -1;

        for(Integer x : this.levels) {
            // Check if the variable level is our curr or upper level and get the max
            if(x <= blocklevel && x > maxLevel) {
                maxLevel = x;
            }
        }

        switch(maxLevel) {
            case -1: // the level (or upper) we're asking for is not there
                result = Optional.empty();
                break;
            case 0: // we've found the variable at root level
                result = Optional.of(new Tuple(this.id, this.id));
                break;
            default: // we've found the variable at inner levels
                String tmp = String.format("%s_%d", this.id, maxLevel);

                if(this.levels.size() > 1) { result = Optional.of(new Tuple(this.id, tmp)); }
                else { result = Optional.of(new Tuple(this.id, this.id)); }

                break;
        }

        return result;
    }
}
