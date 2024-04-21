import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        Cat[] cats={new StraightCat(new int[]{0, 1}),new FlexCat(new int[]{2,3}),new ChonkaCat(new int[]{4,5})};
        Constraint[] constraints={new SampleConstraint1(),new SampleConstraint2(),new SampleConstraint3()};
        Player player= new Player(new IncrementStrategy());
        Board board=new SampleBoard();
        Game currentgame = new Game(
                board,
                cats,
                constraints,
                player,
                22,6,6
        );
        final View view = new View(currentgame);
        ActionListener[] al = new ActionListener[3];
        al[0]=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                step(currentgame, view);
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
    static void step(Game game, View view) {
        if (!game.finished()) {
            game.move();
        }
        view.update();

    }
}