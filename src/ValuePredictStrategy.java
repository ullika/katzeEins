public interface ValuePredictStrategy extends Strategy {
     Move bestMove(Game game);

    int bestVal(Game game);
    
}
