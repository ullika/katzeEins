import java.util.Collections;

public class LessRandomAvoidStrategy implements Strategy {








    public Move bestMove(Game game) {
        Board board = game.board;

        Cat[] morecats = new Cat[game.activeCats.length + 1];
        int[] patterns = {};
        for (int i = 0; i < morecats.length - 1; i++) {
            morecats[i] = game.activeCats[i];
            if (game.activeCats[i] instanceof StraightCat) {
                patterns = game.activeCats[i].getPatterns();
            }
        }
        if (patterns.length > 0) {
            morecats[morecats.length - 1] = new SmallCat(patterns);

        } else {
            morecats = game.activeCats;
        }
        ;

        int pos = 0;
        while (!board.isEmpty(pos)) {
            pos++;
        }




        Move bestmove = new Move(0, pos, 0);
        int bestval = game.points();

//        System.out.println("---------- new move ----------");
        for (int i = pos; i < board.n; i++) {
            if (!board.isEmpty(i)) {
                continue;
            }


            // System.out.printf("check position %d estimated points: %d. %n",i,testgame.points());
            for (int deckpos = 0; deckpos < 2; deckpos++) {
                Game testgame = (Game) game.copy();

                testgame.activeCats = morecats;
                testgame.smallFlowerAddPoints = 1;
                Collections.shuffle(testgame.cardStack);
                Move testmove = new Move(deckpos, i, 0);
                testgame.move(testmove);
                if (testgame.points()  > bestval) {
                    boolean avoid=false;
                    boolean[][] m = ImprovedAvoidStrategy.invalidityMatrix(testgame);

                    for (boolean[] pair : m) {
                        if (pair[0] && pair[1]) {
                            avoid=true;
                        }


                    }
                    if (!avoid) {
                        bestmove = testmove;
                        bestval = testgame.points();
                    }
                }
            }
        }
        return bestmove;


    }
}
