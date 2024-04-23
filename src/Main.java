import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Main {
    public static void viewExample(Strategy strategy) {
        Cat[] cats = {new StraightCat(new int[]{0, 1}), new FlexCat(new int[]{2, 3}), new ChonkaCat(new int[]{4, 5})};
        Constraint[] constraints = {new SampleConstraint1(), new SampleConstraint2(), new SampleConstraint3()};

        Board board = new SampleBoard();
        Game currentgame = new Game(
                board,
                cats,
                constraints,
                22, 6, 6
        );
        final View view = new View(currentgame);
        ActionListener[] al = new ActionListener[3];
        al[0] = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                step(currentgame, strategy, view);
            }
        };
        al[1] = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Board board = new SampleBoard();
                currentgame.setBoard(board);
                currentgame.reset(currentgame.originalStack);
                view.update();

            }
        };

        al[2] = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Board board = new SampleBoard();
                currentgame.setBoard(board);
                currentgame.reset(currentgame.newStack(6, 6));
                //view.init();
                view.update();
            }
        };
        view.addActionListener(al);
        view.init();
        view.update();
    }


    public static void main(String[] args) {


        viewExample(new SmartStrategy(new LessRandomStrategy(6, 14)));
        // simulate(new SmartStrategy(new LessRandomStrategy(6, 15)), 1000);
    }

    static void simulate(Strategy strategy, int nGames) {
        Cat[] cats = {new StraightCat(new int[]{0, 1}), new FlexCat(new int[]{2, 3}), new ChonkaCat(new int[]{4, 5})};
        Constraint[] constraints = {new SampleConstraint1(), new SampleConstraint2(), new SampleConstraint3()};
        Board board = new SampleBoard();

        int[] pointCounter = new int[nGames];

        Game currentgame = new Game(
                board,
                cats,
                constraints,
                22, 6, 6
        );
        for (int i = 0; i < nGames; i++) {
            currentgame.reset(currentgame.newStack(6, 6));
            currentgame.setBoard(new SampleBoard());
            while (!currentgame.finished()) {
                currentgame.move(strategy.bestMove(currentgame));
            }
            pointCounter[i] = currentgame.points();
        }
        System.out.println(Arrays.toString(pointCounter));
        System.out.printf("Median: %d%n", median(pointCounter));


    }


    static void step(Game game, Strategy strategy, View view) {
        if (!game.finished()) {
            game.move(strategy.bestMove(game));
            boolean[][] m = ImprovedAvoidStrategy.invalidityMatrix(game);
//            System.out.printf("constr %s: %s%n", game.activeConstraints[0].toString(),Arrays.toString(m[0]));
//            System.out.printf("constr %s: %s%n", game.activeConstraints[1].toString(),Arrays.toString(m[1]));
//            System.out.printf("constr %s: %s%n", game.activeConstraints[2].toString(),Arrays.toString(m[2]));


            for (boolean[] pair : m) {
                if (pair[0] && pair[1]) {
                    //            System.out.printf("There is a constraint that is 0 percent fulfilled!%n");

                }
            }

        }
        view.update();

    }

    static int median(int[] array) {
        Arrays.sort(array);
        if (array.length % 2 == 0) {
            return array[array.length / 2];
        } else {
            return (array[(array.length - 1) / 2] + array[(array.length + 1) / 2]) / 2;
        }

    }
}