
public class Board {
    public Boolean isWhiteToMove;
    public short promotedWhitePieces;
    public short promotedBlackPieces;
    public Cell[][] boardCells;

    public Board(Boolean isWhiteToMove, short promotedWhitePieces, short promotedBlackPieces, Cell[][] boardCells) {
        this.isWhiteToMove = isWhiteToMove;
        this.promotedWhitePieces = promotedWhitePieces;
        this.promotedBlackPieces = promotedBlackPieces;
        this.boardCells = boardCells;
    }
}