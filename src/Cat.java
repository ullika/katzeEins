import java.util.List;

public abstract class Cat {
    int size;
    int[] patterns;
    int points;
    String name;


    boolean isFlexible;
    int[] directions;

    int getSize() {
        return this.size;}

    public String toString() {
        return this.name;
    }

    boolean isFlexible() {
        return isFlexible;
    }

    int[] getPatterns() {
        return this.patterns;}

    public Cat(int[] patterns) {
        this.patterns = patterns;
    }


    boolean hasPattern(int pattern) {
        for (int p:patterns
             ) {
            if (pattern == p) {
                return true;
            }
        }
        return false;
    }
    abstract int[] occupation(Board board, int position, int direction);

    int[] getDirections() {
        return this.directions;
    } //directions the cat can head without symmetries
    int getPoints() {
        return this.points;}
}
