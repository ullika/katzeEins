import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Main {
public static void viewExample(Strategy strategy) {
    Cat[] cats={new StraightCat(new int[]{0, 1}),new FlexCat(new int[]{2,3}),new ChonkaCat(new int[]{4,5})};
    Constraint[] constraints={new SampleConstraint1(),new SampleConstraint2(),new SampleConstraint3()};

    Board board=new SampleBoard();
    Game currentgame = new Game(
            board,
            cats,
            constraints,
            22,6,6
    );
    final View view = new View(currentgame);
    ActionListener[] al = new ActionListener[3];
    al[0]=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            step(currentgame, strategy,view);
        }
    };
    al[1]=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Board board=new SampleBoard();
            currentgame.setBoard(board);
            currentgame.reset(currentgame.originalStack);
            view.update();

        }
    };

    al[2]=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Board board=new SampleBoard();
            currentgame.setBoard(board);
            currentgame.reset(currentgame.newStack(6,6));
            //view.init();
            view.update();
        }
    };
    view.addActionListener(al);
    view.init();
    view.update();
}



    public static void main(String[] args) {
        //viewExample(new ImprovedStrategy());
        simulate(new ImprovedStrategy(),1000);

    }


    static void simulate(Strategy strategy,int nGames) {
        Cat[] cats={new StraightCat(new int[]{0, 1}),new FlexCat(new int[]{2,3}),new ChonkaCat(new int[]{4,5})};
        Constraint[] constraints={new SampleConstraint1(),new SampleConstraint2(),new SampleConstraint3()};
        //Strategy strategy= new IncrementStrategy();
        Board board=new SampleBoard();

        int[] pointCounter = new int[nGames];

        Game currentgame = new Game(
                board,
                cats,
                constraints,
                22,6,6
        );
        for (int i = 0; i < nGames; i++) {
            currentgame.reset(currentgame.newStack(6,6));
            currentgame.setBoard(new SampleBoard());
            while (!currentgame.finished()) {
                currentgame.move(strategy.bestMove(currentgame));
            }
            pointCounter[i] = currentgame.points();
        }
        System.out.println(Arrays.toString(pointCounter));


    }


    static void step(Game game,Strategy strategy, View view) {
        if (!game.finished()) {
            game.move(strategy.bestMove(game));
        }
        view.update();

    }
}