public class SmallCat extends Cat {


    public SmallCat(int[] patterns) {
        super(patterns);
        this.directions=new int[]{0,1,2};
        this.size=2;
        this.isFlexible=false;
        this.points=2;
        this.name = "Small";
    }
    public int[] occupation(Board board, int position, int direction) {
        return new int[]{
                position,
                board.goDirection(position, direction, 1)
        };

    }


}
