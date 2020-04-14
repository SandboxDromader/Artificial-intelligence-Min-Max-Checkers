public class DiscountedPawnsValuationManager extends PositionValuationManager {
    public static final double DISCOUNT_RATIO = 0.42857142857142857142857142857143;
    public double valuatePosition(Board board) {
        double pointsDiffCounter = super.valuatePosition(board);
        
        for (short i=0;i<8;i++) {
            for (short j=0;j<8;j++) {
                pointsDiffCounter += discountPiece(j, board.boardCells[i][j].state);
            }
        }

        return pointsDiffCounter;
    }

    public static double discountPiece(short rowIndex, Cell.CellState cellState) {
        if (cellState == Cell.CellState.WHITE) {
            return rowIndex * DISCOUNT_RATIO;
        } else if (cellState == Cell.CellState.BLACK) {;
            return (7 - rowIndex) * -1 * DISCOUNT_RATIO;
        } else {
            return 0;
        }
    } 
}