import java.util.List;

public abstract class Cat {
    int size;
    int[] patterns;
    int points;

    boolean isFlexible;
    int[] directions; //directions the cat can head without symmetries
    int getSize() {
        return this.size;}

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
    }
    int getPoints() {
        return this.points;}
}
