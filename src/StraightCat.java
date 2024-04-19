import java.util.List;

public class StraightCat extends Cat {
    int nPoints=5;
    int[] patterns;
    int[] directions={0,1,2};

    boolean isFlexible=false;

    int size=3;

    public StraightCat(int[] patterns) {
        super(patterns);
    }



    public int[] occupation(Board board, int position, int direction) {
        return new int[]{
                position,
                board.goDirection(position, direction, 1),
                board.goDirection(position, direction, 2)};

    };

}
