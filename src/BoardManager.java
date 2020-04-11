import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Scanner;

public class BoardManager {
    public static Board currentBoard;

    public static void readBoardFromFile(String boardFilePath) {
        try {
            String boardFileContent = new String(
                Files.readAllBytes(Paths.get(boardFilePath)), StandardCharsets.UTF_8
            );

            Scanner boardFileScanner = new Scanner(boardFileContent);
            String scannerLine;
            String[] promotedPieces = null;
            Boolean isWhiteToMove = false;

            Cell[][] boardFields = new Cell[8][8];
            short rowsCounter = 7;
            while (boardFileScanner.hasNextLine()) {
                scannerLine = boardFileScanner.nextLine();

                if (scannerLine.contains("_")) {
                    boardFields[rowsCounter] = new Cell[8];
                    for (short i=0;i<8;i++) {
                        if (scannerLine.charAt(i) == '_') {
                            boardFields[rowsCounter][i] = new Cell(Cell.CellState.EMPTY);
                            boardFields[rowsCounter][i] = new Cell(Cell.CellState.EMPTY);
                        } else if (scannerLine.charAt(i) == 'W') {
                            boardFields[rowsCounter][i] = new Cell(Cell.CellState.WHITE);
                        } else if (scannerLine.charAt(i) == 'B') {
                            boardFields[rowsCounter][i] = new Cell(Cell.CellState.BLACK);
                        }
                    }
                    rowsCounter--;
                } else if (scannerLine.contains("/")) {
                    promotedPieces = scannerLine.split("/");
                } else if (scannerLine.equals("WTM")) {
                    isWhiteToMove = true;
                } else if (scannerLine.equals("BTM")) {
                    isWhiteToMove = false;
                }
            }

            boardFileScanner.close();

            if (promotedPieces == null) {
                currentBoard = new Board(isWhiteToMove, (short) 0, (short) 0, boardFields);
            } else {
                currentBoard = new Board(isWhiteToMove, Short.parseShort(promotedPieces[0]), Short.parseShort(promotedPieces[1]), boardFields);
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }

    public static void displayCurrentBoard() {
        System.out.println("currentBorad.isWhiteToMove == " + currentBoard.isWhiteToMove);
        System.out.println("currentBorad.promotedWhitePieces == " + currentBoard.promotedWhitePieces);
        System.out.println("currentBorad.promotedBlackPieces == " + currentBoard.promotedBlackPieces);

        for (short i=7;i>=0;i--) {
            for (short j=0;j<8;j++) {
                if (currentBoard.boardCells[i][j].state == Cell.CellState.EMPTY) {
                    System.out.print('_');
                } else if (currentBoard.boardCells[i][j].state == Cell.CellState.BLACK) {
                    System.out.print('B');
                } else if (currentBoard.boardCells[i][j].state == Cell.CellState.WHITE) {
                    System.out.print('W');
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        System.out.println("Inside BoardManager: a main class responsible for results display");

        readBoardFromFile("./res/board.txt");
        displayCurrentBoard();
    }
}