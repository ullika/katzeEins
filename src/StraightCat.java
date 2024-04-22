import java.util.List;

public class StraightCat extends Cat {


    public StraightCat(int[] patterns) {
        super(patterns);
        this.directions=new int[]{0,1,2};
        this.size=3;
        this.isFlexible=false;
        this.points=5;
        this.name = "Straight";
    }

    public int[] occupation(Board board, int position, int direction) {
        return new int[]{
                position,
                board.goDirection(position, direction, 1),
                board.goDirection(position, direction, 2)
                };

    };

}
