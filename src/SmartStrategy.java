import java.util.Collections;

public class SmartStrategy implements Strategy {
    // next improvements: chose optimal card from display (currently: random)
    // punish cards that invalidate the constraints

    Strategy fallback;
    public SmartStrategy(Strategy fallback) {
        this.fallback = fallback;
    }



    public Move bestMove(Game game) {
        Card[] deck = game.deck;
        Card[] display = game.display;
        Board board = game.board;

        Move bestmove = fallback.bestMove(game);

        // best field to occupy is determined. now decide what to take from display!
        if (1 == game.board.nEmpty) {
            return bestmove;
        }


        int points0 = 0;
        Game testgame = (Game) game.copy();
        testgame.move(bestmove);
        testgame.move(fallback.bestMove(testgame));
        points0 = testgame.points();

        int points1 = 0;
        testgame = (Game) game.copy();
        Move alternative = new Move(bestmove.deckpos, bestmove.fieldpos, 1);
        testgame.move(alternative);
        testgame.move(fallback.bestMove(testgame));
        points1 = testgame.points();

        if (points1 > points0) {
            return alternative;
        } else {
            return bestmove;
        }


    }
}
