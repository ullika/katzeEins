public class FlexCat extends Cat {
    int[] patterns;

    boolean isFlexible=false;
    int points=3;
    int size=3;




    @Override
    public int[] occupation(Board board, int position, int direction) {
        return new int[0];
    }

    @Override
    public int[] getDirections() {
        return new int[0];
    }



    public FlexCat(int[] patterns) {
        super(patterns);
    }
}
