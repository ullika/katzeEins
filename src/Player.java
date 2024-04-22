public class Player {
    Strategy strategy;
    public Player(Strategy strategy) {
        this.strategy=strategy;
    }
    Move bestMove(Game game){
        return strategy.bestMove(game);
    }
}
