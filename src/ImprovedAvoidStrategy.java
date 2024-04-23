import java.util.Collections;

public class ImprovedAvoidStrategy implements Strategy{
    int penalty;
    public static boolean[][] invalidityMatrix(Game game){
        return new boolean[][]{
                new boolean[]{game.activeConstraints[0].invalid(game.board.colors, game.nColors),
                        game.activeConstraints[0].invalid(game.board.patterns, game.nPatterns)},
                new boolean[]{game.activeConstraints[1].invalid(game.board.colors, game.nColors),
                        game.activeConstraints[1].invalid(game.board.patterns, game.nPatterns)},
                new boolean[]{game.activeConstraints[2].invalid(game.board.colors, game.nColors),
                        game.activeConstraints[2].invalid(game.board.patterns, game.nPatterns)}
        };

    }

    public ImprovedAvoidStrategy(int penalty) {
        this.penalty = penalty;
    }



    public Move bestMove(Game game) {
        Board board = game.board;

        int pos = 0;
        while (!board.isEmpty(pos)) {
            pos++;
        }

        Move bestmove = new Move(0, pos, 0);
        int bestval = -1;
        for (int i = pos; i < board.n; i++) {
            if (!board.isEmpty(i)) {
                continue;
            }

        for (int deckpos = 0; deckpos < 2; deckpos++) {
            Game testgame = game.copy();


            Collections.shuffle(testgame.cardStack);
            Move testmove = new Move(deckpos, i, 0);
            testgame.move(testmove);
            if (testgame.points() > bestval) {
                int minus = 0;
                boolean[][] m = LessRandomStrategy.invalidityMatrix(testgame);

                for (boolean[] pair : m) {
                    if (pair[0] && pair[1]) {
                        minus += penalty;

                    }
                }

                if (testgame.points() - minus > bestval) {
                    bestmove = testmove;
                    bestval = testgame.points()-minus;
                }
            }
        }
    }
        return bestmove;
    }





}
