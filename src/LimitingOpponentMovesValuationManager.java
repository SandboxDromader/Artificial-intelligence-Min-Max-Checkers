public class LimitingOpponentMovesValuationManager extends PositionValuationManager {
    public static final double MOVE_RATIO = 0.05;
    public double valuatePosition(Board board) {
        double pointsDiffCounter = super.valuatePosition(board);
        return pointsDiffCounter + MOVE_RATIO * BoardManager.getNumberOfPossibleMoves(board.isWhiteToMove);
    }
}