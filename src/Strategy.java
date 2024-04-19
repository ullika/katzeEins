public interface Strategy {
    Move bestMove(Board board,Card[] deck,Card[] display);
}
