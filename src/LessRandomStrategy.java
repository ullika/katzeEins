import java.util.Collections;

public class LessRandomStrategy implements Strategy {


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

    public LessRandomStrategy(int penalty,int reward) {
        this.penalty = penalty;
        this.reward=reward;
    }



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



        int defaultMinus=0;
        for (boolean[] pair : ImprovedAvoidStrategy.invalidityMatrix(game)) {

            if (pair[0] && pair[1]) {
                defaultMinus += penalty;
                continue;
            }
            if (pair[0] || pair[1]) {
                defaultMinus += (penalty-penalty%2)/2;

            }
        }
        Move bestmove = new Move(0, pos, 0);
        int bestval = game.points()-defaultMinus-1;

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
                int plus=0;
                for (Constraint ac : game.activeConstraints) {
                    if (ac.dependsOn(testmove.fieldpos)&&ac.nOccupied(testgame.board.colors)>0) {
                        if ((ac.invalid(game.board.colors, game.nColors) ==
                                ac.invalid(testgame.board.colors, testgame.nColors))
                                &&
                                (ac.invalid(game.board.patterns, game.nPatterns) ==
                                ac.invalid(testgame.board.patterns, testgame.nPatterns))) {
                            plus=reward;

                        }
                    }
                }

                if (testgame.points()+plus -defaultMinus > bestval) {
                    int minus = 0;
                    boolean[][] m = LessRandomStrategy.invalidityMatrix(testgame);

                    for (boolean[] pair : m) {
                        if (pair[0] && pair[1]) {
                            minus += penalty;
                            continue;
                        }
                        if (pair[0] || pair[1]) {
                            minus += (penalty-penalty%2)/2;

                        }

                    }
                    if (testgame.points() - minus+plus > bestval) {
                        bestmove = testmove;
                        bestval = plus+testgame.points()-minus;
                    }
                }
            }
        }
        return bestmove;


    }
}
