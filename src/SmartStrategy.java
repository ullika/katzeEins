import java.util.Collections;

public class SmartStrategy implements Strategy {

    ValuePredictStrategy fallback;
    public SmartStrategy(ValuePredictStrategy fallback) {
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
        Game testgame = game.copy();
        testgame.move(bestmove);
        points0 = fallback.bestVal(testgame);


        int points1 = 0;
        testgame = game.copy();
        Move alternative = new Move(bestmove.deckpos, bestmove.fieldpos, 1);
        testgame.move(alternative);
        points1 = fallback.bestVal(testgame);

        int points2 = 0;
        testgame = game.copy();
        Move thirdOption = new Move(bestmove.deckpos, bestmove.fieldpos, 2);
        testgame.move(thirdOption);
        points2= fallback.bestVal(testgame);

        if (points1 > points0) {
            if (points2>points1) {
                return thirdOption;
            }
            return alternative;
        } else {
            if (points2 > points0) {
                return thirdOption;
            }
            return bestmove;
        }


    }
}
