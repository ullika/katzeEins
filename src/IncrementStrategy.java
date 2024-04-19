public class IncrementStrategy implements Strategy{
    public Move bestMove(Board board, Card[] deck, Card[] display){
        int pos=0;
        int displaypos=0;
        int deckpos=0;

        while (!board.isEmpty(pos)){
            pos++;
        }

        return new Move(deckpos, pos, displaypos);
    }
}
