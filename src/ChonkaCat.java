public class ChonkaCat extends Cat {

    public ChonkaCat(int[] patterns) {
        super(patterns);

        this.points=10;
        this.size=5;
        this.isFlexible=false;
        this.directions=new int[]{1, 2, 3, 4, 5, 6};
        this.name = "Chonka ";
    }



    @Override
    int[] occupation(Board board, int position, int direction) {
        return new int[]{
                position,
                board.goDirection(position, direction, 1),
                board.goDirection(position, direction, 2),
                board.goDirection(position, (direction +5) % 6, 1),
                board.goDirection(board.goDirection(position, (direction +5 % 6), 1), direction, 1)
        };

    };

}
