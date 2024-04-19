public class Player {
    Strategy strategy;
    public Player(Strategy strategy) {
        this.strategy=strategy;
    }
    Move bestMove(Board board, Card[] deck, Card[] display){
        return strategy.bestMove(board, deck, display);
    }
}
