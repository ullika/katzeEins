import java.util.Collections;

public class LessRandomStrategy implements ValuePredictStrategy {

    class MoveValuePair {
        Move move;
        int value;

        MoveValuePair(Move move, int value) {
            this.move = move;
            this.value = value;
        }
    }


    int penalty;
    int reward;

    public static boolean[][] invalidityMatrix(Game game) {
        return new boolean[][]{
                new boolean[]{game.activeConstraints[0].invalid(game.board.colors, game.nColors),
                        game.activeConstraints[0].invalid(game.board.patterns, game.nPatterns)},
                new boolean[]{game.activeConstraints[1].invalid(game.board.colors, game.nColors),
                        game.activeConstraints[1].invalid(game.board.patterns, game.nPatterns)},
                new boolean[]{game.activeConstraints[2].invalid(game.board.colors, game.nColors),
                        game.activeConstraints[2].invalid(game.board.patterns, game.nPatterns)}
        };

    }

    public LessRandomStrategy(int penalty, int reward) {
        this.penalty = penalty;
        this.reward = reward;
    }


    public MoveValuePair bestPair(Game game) {
        Board board = game.board;

        Cat[] morecats = new Cat[game.activeCats.length + 2];
        int[] smallPatterns = {};
        int[] miniPatterns = {};
        for (int i = 0; i < morecats.length - 2; i++) {
            morecats[i] = game.activeCats[i];
            if (game.activeCats[i] instanceof StraightCat) {
                smallPatterns = game.activeCats[i].getPatterns();
            } else if (game.activeCats[i] instanceof FlexCat) {
                miniPatterns = game.activeCats[i].getPatterns();
            }
        }

        morecats[morecats.length - 2] = new SmallCat(smallPatterns);
        morecats[morecats.length - 1] = new MiniCat(miniPatterns);


        int pos = 0;
        while (!board.isEmpty(pos)) {
            pos++;
        }


        int legacyMinus = 0;
        double square = penalty + 1 / 3 * (penalty * (board.nEmpty / 22.0)) * (penalty * (board.nEmpty / 22.0));


        int currentpenalty = (int) square;
        for (boolean[] pair : ImprovedAvoidStrategy.invalidityMatrix(game)) {

            if (pair[0] && pair[1]) {
                legacyMinus += currentpenalty;
                continue;
            }
            if (pair[0] || pair[1]) {
                legacyMinus += (currentpenalty - currentpenalty % 2) / 2;

            }
        }
        Move bestmove = new Move(0, pos, 0);
        int bestval = game.points() - legacyMinus - 1;

//        System.out.println("---------- new move ----------");
        for (int i = pos; i < board.n; i++) {
            if (!board.isEmpty(i)) {
                //  System.out.printf("Skip pos %d because it is occupied.%n",i);

                continue;
            }


            // System.out.printf("check position %d estimated points: %d. %n",i,testgame.points());
            for (int deckpos = 0; deckpos < 2; deckpos++) {
                Game testgame = game.copy();

                testgame.activeCats = morecats;
                testgame.smallFlowerAddPoints = 1;
                Collections.shuffle(testgame.cardStack);
                Move testmove = new Move(deckpos, i, 0);
                testgame.move(testmove);
                Card card = game.deck[testmove.deckpos];
                int plus = 0;
                for (Constraint ac : game.activeConstraints) {
                    if (ac.dependsOn(testmove.fieldpos)) {
                        if (((ac.invalid(game.board.colors, game.nColors) ==
                                ac.invalid(testgame.board.colors, testgame.nColors))
                                &&
                                (ac.invalid(game.board.patterns, game.nPatterns) ==
                                        ac.invalid(testgame.board.patterns, testgame.nPatterns)))) {
                            //plus += ac.benefit(game.board.colors, card.color, reward);
                            //plus += ac.benefit(game.board.patterns, card.pattern, reward);
                            plus += reward;
                            //  System.out.printf("plus is %d.%n",plus);

                        }
                    }
                }
                // System.out.printf("Check pos %d, points: %d (constr.plus: %d).%n",testmove.fieldpos,testgame.points()+plus,plus);
                if (testgame.points() + plus - legacyMinus > bestval) {
                    int minus = 0;
                    boolean[][] m = LessRandomStrategy.invalidityMatrix(testgame);

                    for (boolean[] pair : m) {
                        if (pair[0] && pair[1]) {
                            minus += currentpenalty;
                            continue;
                        }
                        if (pair[0] || pair[1]) {
                            minus += (currentpenalty - currentpenalty % 2) / 2;

                        }

                    }
                    if (testgame.points() - minus + plus > bestval) {
                        bestmove = testmove;
                        bestval = plus + testgame.points() - minus;
                    }
                }
            }
        }
        //   System.out.printf("-------- BEST: %d. Current penalty: %d.%n",bestmove.fieldpos,currentpenalty);
        return new MoveValuePair(bestmove, bestval);


    }

    public int bestVal(Game game) {
        return bestPair(game).value;
    }

    public Move bestMove(Game game) {
        return bestPair(game).move;
    }
}
