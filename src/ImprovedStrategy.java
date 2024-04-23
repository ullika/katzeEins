import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class ImprovedStrategy implements Strategy{
    // next improvements: chose optimal card from display (currently: random)
    // punish cards that invalidate the constraints


    public Move bestMove(Game game){

        Board board = game.board;

        int pos=0;
        while (!board.isEmpty(pos)){
            pos++;
        }

        Move bestmove= new Move(0, pos, 0);
        int bestval=0;

//        System.out.println("---------- new move ----------");
        for (int i = pos; i < board.n; i++) {
            if (!board.isEmpty(i)) {
                continue;
            }
            Game testgame = game.copy();
            Collections.shuffle(testgame.cardStack);
            Move testmove=new Move(0,i,0);
            testgame.move(testmove);
           // System.out.printf("check position %d estimated points: %d. %n",i,testgame.points());
            if (testgame.points()>bestval) {
                    bestmove=testmove;
                    bestval= testgame.points();
            }

            testgame = game.copy();
            Collections.shuffle(testgame.cardStack);
            testmove=new Move(1,i,0);
            testgame.move(testmove);
            if (testgame.points()>bestval) {
                bestmove=testmove;
                bestval= testgame.points();
            }


        }
        return bestmove;


    }
}
