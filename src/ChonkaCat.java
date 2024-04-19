public class ChonkaCat extends Cat {

    public ChonkaCat(int[] patterns) {
        super(patterns);
    }

    @Override
    int[] occupation(Board board, int position, int direction) {
        return new int[]{
                position,
                board.goDirection(position, direction, 1),
                board.goDirection(position, direction, 2),
                board.goDirection(position, (direction - 1) % 6, 1),
                board.goDirection(board.goDirection(position, (direction - 1 % 6), 1), direction, 1)
        };

    };

}
