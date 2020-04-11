
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

    // public Board(String boardFilePath) {
    //     boardCells = new Cell[8][8];

    //     Scanner boardFileScanner = null;
    //     String result = '';

    //     try {
    //         File boardFile = new File(boardFilePath);
    //         scanner = new Scanner(boardFile);



    //     } finally  {
    //         if (boardFileIS != null) {
    //             try {
    //                 boardFileIS.close();
    //             } catch (IOException ex) {
    //                 ex.printStackTrace();
    //             }
    //         }
    //     }
    // }
}