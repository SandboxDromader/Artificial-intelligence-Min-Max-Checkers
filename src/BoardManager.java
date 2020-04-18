import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Math;

public class BoardManager {
    public static Board currentBoard;
    public static final String NO_MOVE_POSSIBLE_RESULT = "NO_MOVE_POSSIBLE";

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

            for (short i=0;i<8;i++) {
                boardFields[i] = new Cell[8];
            }

            short rowsCounter = 7;
            while (boardFileScanner.hasNextLine()) {
                scannerLine = boardFileScanner.nextLine();

                if (scannerLine.contains("_")) {
                    for (short i=0;i<8;i++) {
                        if (scannerLine.charAt(i) == '_') {
                            boardFields[i][rowsCounter] = new Cell(Cell.CellState.EMPTY);
                            boardFields[i][rowsCounter] = new Cell(Cell.CellState.EMPTY);
                        } else if (scannerLine.charAt(i) == 'W') {
                            boardFields[i][rowsCounter] = new Cell(Cell.CellState.WHITE);
                        } else if (scannerLine.charAt(i) == 'B') {
                            boardFields[i][rowsCounter] = new Cell(Cell.CellState.BLACK);
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
        System.out.println("======== currentBorad.promotedWhitePieces ========");
        System.out.println("currentBorad.promotedWhitePieces == " + currentBoard.promotedWhitePieces);
        System.out.println("currentBorad.promotedBlackPieces == " + currentBoard.promotedBlackPieces);

        for (short i=7;i>=0;i--) {
            for (short j=0;j<8;j++) {
                if (currentBoard.boardCells[j][i].state == Cell.CellState.EMPTY) {
                    System.out.print('_');
                } else if (currentBoard.boardCells[j][i].state == Cell.CellState.BLACK) {
                    System.out.print('B');
                } else if (currentBoard.boardCells[j][i].state == Cell.CellState.WHITE) {
                    System.out.print('W');
                }
            }
            System.out.println();
        }
        System.out.println("==================================================");
    }

    public static void displayBoard(Cell[][] boardCells) {
        for (short i=7;i>=0;i--) {
            for (short j=0;j<8;j++) {
                if (boardCells[j][i].state == Cell.CellState.EMPTY) {
                    System.out.print('_');
                } else if (boardCells[j][i].state == Cell.CellState.BLACK) {
                    System.out.print('B');
                } else if (boardCells[j][i].state == Cell.CellState.WHITE) {
                    System.out.print('W');
                }
            }
            System.out.println();
        }
    }

    public static void debugCurrentBoard() {
        System.out.println("--- START OF debugCurrentBoard() METHOD ---");
        if (currentBoard.boardCells == null) {
            System.out.println("currentBoard.boardCells == null");
        }
        for(short i=0;i<currentBoard.boardCells.length;i++) {
            for(short j=0;j<currentBoard.boardCells[i].length;j++) {
                System.out.println("currentBoard.boardCells[" + i + "][" + j + "] = " + currentBoard.boardCells[i][j]);
            }
        }
    }

    public static Cell[][] duplicateCurrentCells() {
        Cell[][] boardCells = new Cell[8][8];

        for(short i=0;i<8;i++) {
            for(short j=0;j<8;j++) {
                boardCells[i][j] = new Cell(currentBoard.boardCells[i][j].state);
            }
        }

        return boardCells;
    }

    public static ArrayList<MoveOption> findPossibleMoves() {

        ArrayList<MoveOption> possibleTakingMoves = getAllPossibleSingleTakings(currentBoard.boardCells, currentBoard.isWhiteToMove);

        ArrayList<MoveOption> possibleCompletedTakingMoves = new ArrayList<MoveOption>();

        Cell[][] boardCells;
        MoveOption tmpMoveOption;
        ArrayList<MoveOption> newMoveOptions;

        while (!possibleTakingMoves.isEmpty()) {
            tmpMoveOption = possibleTakingMoves.get(0);
            possibleTakingMoves.remove(0);
            boardCells = duplicateCurrentCells();

            makeMove(boardCells, tmpMoveOption);

            if (isMovePossible(boardCells, tmpMoveOption.endPosition, currentBoard.isWhiteToMove)) {
                newMoveOptions = new ArrayList<MoveOption>();

                for (MoveOption newIntermediateMoveOption : getAllPossibleSingleTakings(boardCells, tmpMoveOption.endPosition, currentBoard.isWhiteToMove)) {
                    possibleTakingMoves.add(new MoveOption(tmpMoveOption, newIntermediateMoveOption));
                }
            } else {
                possibleCompletedTakingMoves.add(tmpMoveOption);
            }
        }

        return possibleCompletedTakingMoves;
    }

    // Return true if any not taking a piece move is possible for a piece standing on current cell [rowIndex][columnIndex]
    public static Boolean isMovePossible(Cell[][] boardCells, CellPosition cellPosition, Boolean isWhiteToMove) {
        // if white to move
        if (isWhiteToMove && boardCells[cellPosition.yIndex][cellPosition.xIndex].state == Cell.CellState.WHITE) {
            if (isGoalCellEmpty(boardCells, new CellPosition((short) (cellPosition.yIndex + 1),(short) (cellPosition.xIndex - 1)))) {
                return true;
            } else if (isGoalCellEmpty(boardCells, new CellPosition((short) (cellPosition.yIndex + 1),(short) (cellPosition.xIndex + 1)))) {
                return true;
            } else {
                return false;
            }
        } else if(!isWhiteToMove && boardCells[cellPosition.yIndex][cellPosition.xIndex].state == Cell.CellState.BLACK) {
            // if black to move
            if (isGoalCellEmpty(boardCells, new CellPosition((short) (cellPosition.yIndex - 1),(short) (cellPosition.xIndex - 1)))) {
                return true;
            } else if (isGoalCellEmpty(boardCells, new CellPosition((short) (cellPosition.yIndex - 1),(short) (cellPosition.xIndex + 1)))) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static Boolean areIndexesCorrect(short rowIndex, short columnIndex) {
        if (rowIndex >= 0 && rowIndex < 8 && columnIndex >= 0 && columnIndex < 8) {
            return true;
        } else {
            return false;
        }
    }

    public static Boolean isGoalCellEmpty(Cell[][] boardCells, CellPosition cellPosition) {
        if (areIndexesCorrect(cellPosition.yIndex, cellPosition.xIndex) && boardCells[cellPosition.yIndex][cellPosition.xIndex].state == Cell.CellState.EMPTY) {
            return true;
        } else {
            return false;
        }
    }

    public static Boolean hasCellState(Cell[][] boardCells, CellPosition cellPosition, Cell.CellState state) {
        if (areIndexesCorrect(cellPosition.yIndex, cellPosition.xIndex) && boardCells[cellPosition.yIndex][cellPosition.xIndex].state == state) {
            return true;
        }

        return false;
    }

    // Return true if exactly one taking is possible in a single move
    public static Boolean isSingleTakingPossible(Cell[][] boardCells, CellPosition cellPosition, Boolean isWhiteToMove) {
        Cell.CellState cellState;
        if (isWhiteToMove) {
            cellState = Cell.CellState.BLACK;
        } else {
            cellState = Cell.CellState.WHITE;
        }

        if (hasCellState(boardCells, new CellPosition((short) (cellPosition.yIndex + 1), (short) (cellPosition.xIndex +1)), cellState) && isGoalCellEmpty(boardCells, new CellPosition((short) (cellPosition.yIndex + 2), (short) (cellPosition.xIndex + 2)))) {
            return true;
        } else if (hasCellState(boardCells, new CellPosition((short) (cellPosition.yIndex - 1), (short) (cellPosition.xIndex + 1)), cellState) && isGoalCellEmpty(boardCells, new CellPosition((short) (cellPosition.yIndex - 2), (short) (cellPosition.xIndex + 2)))) {
            return true;
        } else if (hasCellState(boardCells, new CellPosition((short) (cellPosition.yIndex - 1), (short) (cellPosition.xIndex - 1)), cellState) && isGoalCellEmpty(boardCells, new CellPosition((short) (cellPosition.yIndex - 2), (short) (cellPosition.xIndex - 2)))) {
            return true;
        } else if (hasCellState(boardCells, new CellPosition((short) (cellPosition.yIndex + 1), (short) (cellPosition.xIndex - 1)), cellState) && isGoalCellEmpty(boardCells, new CellPosition((short) (cellPosition.yIndex + 2), (short) (cellPosition.xIndex - 2)))) {
            return true;
        }

        return false;
    }

    public static void makeSingleTaking(Cell[][] boardCells, CellPosition startPosition, CellPosition endPosition) {
        boardCells[endPosition.yIndex][endPosition.xIndex].state = boardCells[startPosition.yIndex][startPosition.xIndex].state;
        boardCells[startPosition.yIndex][startPosition.xIndex].state = Cell.CellState.EMPTY;
        boardCells[(endPosition.yIndex + startPosition.yIndex)/2][(endPosition.xIndex + startPosition.xIndex)/2].state = Cell.CellState.EMPTY;
    }

    public static void makeMove(Cell[][] boardCells, MoveOption moveOption) {
        if (moveOption.intermediateTakingsArrayList == null || moveOption.intermediateTakingsArrayList.size() == 0) {
            if (Math.abs(moveOption.endPosition.yIndex - moveOption.startPosition.yIndex) == 1) {
                boardCells[moveOption.endPosition.yIndex][moveOption.endPosition.xIndex].state = boardCells[moveOption.startPosition.yIndex][moveOption.startPosition.xIndex].state;
                boardCells[moveOption.startPosition.yIndex][moveOption.startPosition.xIndex].state = Cell.CellState.EMPTY;
            } else {
                // if it's single taking case
                makeSingleTaking(boardCells, moveOption.startPosition, moveOption.endPosition);
            }
        } else {
            // if any intermediate taking exists
            CellPosition previousCellPosition = moveOption.startPosition;

            for (CellPosition cellPosition : moveOption.intermediateTakingsArrayList) {
                makeSingleTaking(boardCells, previousCellPosition, cellPosition);
                previousCellPosition = cellPosition;
            }

            makeSingleTaking(boardCells, previousCellPosition, moveOption.endPosition);
        }
    }

    public static Cell[][] returnDuplicateAfterMove(Cell[][] boardCells, MoveOption moveOption) {
        Cell[][] boardCellsAfterMove = duplicateCurrentCells();
        makeMove(boardCellsAfterMove, moveOption);
        return boardCellsAfterMove;
    }

    // this method returns all possible takings for selected side
    public static ArrayList<MoveOption> getAllPossibleSingleTakings(Cell[][] boardCells, Boolean isWhiteToMove) {
        ArrayList<MoveOption> moveOptionsList = new ArrayList<MoveOption>();
        ArrayList<MoveOption> tmpMoveOptionsList;
        for (short i=0;i<8;i++) {
            for (short j=0;j<8;j++) {
                tmpMoveOptionsList = getAllPossibleSingleTakings(boardCells, new CellPosition(i,j), isWhiteToMove);
                if (tmpMoveOptionsList != null) {
                    moveOptionsList.addAll(tmpMoveOptionsList);
                }
            }
        }

        return moveOptionsList;
    }

    public static ArrayList<MoveOption> getAllPossibleSingleTakings(Cell[][] boardCells, CellPosition cellPosition, Boolean isWhiteToMove) {
        Cell.CellState cellState = boardCells[cellPosition.yIndex][cellPosition.xIndex].state;

        if ((cellState == Cell.CellState.EMPTY) || (isWhiteToMove && cellState == Cell.CellState.BLACK) || (!isWhiteToMove && cellState == Cell.CellState.WHITE)) {
            return null;
        }

        Cell.CellState oppositeSideCellState;
        if (isWhiteToMove) {
            oppositeSideCellState = Cell.CellState.BLACK;
        } else {
            oppositeSideCellState = Cell.CellState.WHITE;
        }

        ArrayList<MoveOption> moveOptionsArrayList = new ArrayList<MoveOption>();

        if (hasCellState(boardCells, new CellPosition((short) (cellPosition.yIndex + 1), (short) (cellPosition.xIndex +1)), oppositeSideCellState) && isGoalCellEmpty(boardCells, new CellPosition((short) (cellPosition.yIndex + 2), (short) (cellPosition.xIndex + 2)))) {
            moveOptionsArrayList.add(new MoveOption(cellPosition, new CellPosition((short) (cellPosition.yIndex + 2), (short) (cellPosition.xIndex + 2))));
        }

        if (hasCellState(boardCells, new CellPosition((short) (cellPosition.yIndex - 1), (short) (cellPosition.xIndex + 1)), oppositeSideCellState) && isGoalCellEmpty(boardCells, new CellPosition((short) (cellPosition.yIndex - 2), (short) (cellPosition.xIndex + 2)))) {
            moveOptionsArrayList.add(new MoveOption(cellPosition, new CellPosition((short) (cellPosition.yIndex - 2), (short) (cellPosition.xIndex + 2))));
        }

        if (hasCellState(boardCells, new CellPosition((short) (cellPosition.yIndex - 1), (short) (cellPosition.xIndex - 1)), oppositeSideCellState) && isGoalCellEmpty(boardCells, new CellPosition((short) (cellPosition.yIndex - 2), (short) (cellPosition.xIndex - 2)))) {
            moveOptionsArrayList.add(new MoveOption(cellPosition, new CellPosition((short) (cellPosition.yIndex - 2), (short) (cellPosition.xIndex - 2))));
        }

        if (hasCellState(boardCells, new CellPosition((short) (cellPosition.yIndex + 1), (short) (cellPosition.xIndex - 1)), oppositeSideCellState) && isGoalCellEmpty(boardCells, new CellPosition((short) (cellPosition.yIndex + 2), (short) (cellPosition.xIndex - 2)))) {
            moveOptionsArrayList.add(new MoveOption(cellPosition, new CellPosition((short) (cellPosition.yIndex + 2), (short) (cellPosition.xIndex - 2))));
        }
        return moveOptionsArrayList;
    }

    public static void main(String[] args) {
        readBoardFromFile("./res/board.txt");
        displayCurrentBoard();

        System.out.println("Possible moves:");
        for (MoveOption mo : findPossibleMoves()) {
            mo.displayMove();
        }

        DiscountedPawnsValuationManager discPawnsValuationManager = new DiscountedPawnsValuationManager();
        System.out.println("");
        System.out.println("Position valuation: " + discPawnsValuationManager.valuatePosition(currentBoard));
    }
}