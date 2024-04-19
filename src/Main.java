import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        Cat[] cats={new StraightCat(new int[]{0, 1}),new FlexCat(new int[]{2,3})};
        Constraint[] constraints={new SampleConstraint1(),new SampleConstraint2(),new SampleConstraint3()};
        Player player= new Player(new IncrementStrategy());
        Board board=new SampleBoard();
        Game game = new Game(
                board,
                cats,
                constraints,
                player
        );
        System.out.println(Arrays.toString(game.board.colors));
        View view=new View(game);
        view.update();

       // while (!game.finished()) {
        //    game.move();
        //    view.update();
       // }



    }
}