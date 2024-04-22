public class IncrementStrategy implements Strategy{
    public Move bestMove(Game game){
        Card[] deck = game.deck;
        Card[] display = game.display;
        Board board = game.board;

        int pos=0;
        int displaypos=0;
        int deckpos=0;

        while (!board.isEmpty(pos)){
            pos++;
        }

        return new Move(deckpos, pos, displaypos);
    }
}
