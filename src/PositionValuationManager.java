public class PositionValuationManager {
    public double valuatePosition(Board board) {
        double pointsDiffCounter = (double) (4 * (board.promotedWhitePieces - board.promotedBlackPieces));

        for(int i=0;i<8;i++) {
            for(int j=0;j<8;j++) {
                if (board.boardCells[i][j].state == Cell.CellState.WHITE) {
                    ++pointsDiffCounter;
                } else if (board.boardCells[i][j].state == Cell.CellState.BLACK) {
                    --pointsDiffCounter;
                }
            }
        }

        return pointsDiffCounter;
    }
}