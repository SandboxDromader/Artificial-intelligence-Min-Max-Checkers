import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

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
        System.out.println("--- END OF debugCurrentBoard() METHOD ---");
    }

    public static ArrayList<String> findPossibleMoves() {
        ArrayList<String> possibleMovesArrayList = new ArrayList<String>();

        // First moves that should be checked are takings. If any taking is possible no other move can be made, because taking is a must in checkers. Because of that finding any takings grant's us a right only to consider all takings and no other moves.

        // We also don't need to consider any moves from 8th line (on white move) and 1st line (on black move). It's because no piece of those sides can stay there. In that case the piece will disappear immediately in previous move and will not be standing there later.

        if (currentBoard.isWhiteToMove) {
            // ...
            for(short i=7;i>=0;i++) {
                for(short j=0;j<8;j++) {
                    // if () {

                    // }
                }
            }
        } else {
            // ...
        }

        return possibleMovesArrayList;
    }

        // Return all not taking a piece moves possible for a piece as a String with moves separated by commas
        // public static MoveOption[] findPossibleMoves(Cell[][] boardCells, CellPosition cellPosition) {
        
        // // short rowIndex, short columnIndex) {
        //     //

        //     if (isMovePossible(rowIndex, columnIndex)) {
        //         String possibleMoves = "";
    
        //         if (currentBoard.isWhiteToMove) {
        //             if (isGoalCellEmpty((short) (rowIndex - 1), (short) (columnIndex + 1)) && currentBoard.boardCells[rowIndex][columnIndex].state == Cell.CellState.WHITE) {
        //                 possibleMoves += "[" + rowIndex + "][" + columnIndex + "]=>[" + (rowIndex - 1) + "][" + (columnIndex + 1) + "]";
        //             }
        
        //             if (isGoalCellEmpty((short) (rowIndex + 1), (short) (columnIndex + 1)) && currentBoard.boardCells[rowIndex][columnIndex].state == Cell.CellState.WHITE) {
        //                 if (!possibleMoves.equals("")) {
        //                     possibleMoves += ",";
        //                 } 
        //                 possibleMoves += "[" + rowIndex + "][" + columnIndex + "]=>[" + (rowIndex + 1) + "][" + (columnIndex + 1) + "]";
        //             }
        //         } else {
        //             if (isGoalCellEmpty((short) (rowIndex - 1), (short) (columnIndex - 1)) && currentBoard.boardCells[rowIndex][columnIndex].state == Cell.CellState.BLACK) {
        //                 possibleMoves += "[" + rowIndex + "][" + columnIndex + "]=>[" + (rowIndex - 1) + "][" + (columnIndex - 1) + "]";
        //             }
    
        //             if (isGoalCellEmpty((short) (rowIndex + 1), (short) (columnIndex - 1)) && currentBoard.boardCells[rowIndex][columnIndex].state == Cell.CellState.BLACK) {
        //                 if (!possibleMoves.equals("")) {
        //                     possibleMoves += ",";
        //                 } 
        //                 possibleMoves += "[" + rowIndex + "][" + columnIndex + "]=>[" + (rowIndex + 1) + "][" + (columnIndex - 1) + "]";
        //             }
        //         }
        //         return possibleMoves;
        //     } else {
        //         return NO_MOVE_POSSIBLE_RESULT;
        //     }
        // }

    // Return all not taking a piece moves possible for a piece as a String with moves separated by commas
    // public static String findPossibleMoves(short rowIndex, short columnIndex) {

    //     if (isMovePossible(rowIndex, columnIndex)) {
    //         String possibleMoves = "";

    //         if (currentBoard.isWhiteToMove) {
    //             if (isGoalCellEmpty((short) (rowIndex - 1), (short) (columnIndex + 1)) && currentBoard.boardCells[rowIndex][columnIndex].state == Cell.CellState.WHITE) {
    //                 possibleMoves += "[" + rowIndex + "][" + columnIndex + "]=>[" + (rowIndex - 1) + "][" + (columnIndex + 1) + "]";
    //             }
    
    //             if (isGoalCellEmpty((short) (rowIndex + 1), (short) (columnIndex + 1)) && currentBoard.boardCells[rowIndex][columnIndex].state == Cell.CellState.WHITE) {
    //                 if (!possibleMoves.equals("")) {
    //                     possibleMoves += ",";
    //                 } 
    //                 possibleMoves += "[" + rowIndex + "][" + columnIndex + "]=>[" + (rowIndex + 1) + "][" + (columnIndex + 1) + "]";
    //             }
    //         } else {
    //             if (isGoalCellEmpty((short) (rowIndex - 1), (short) (columnIndex - 1)) && currentBoard.boardCells[rowIndex][columnIndex].state == Cell.CellState.BLACK) {
    //                 possibleMoves += "[" + rowIndex + "][" + columnIndex + "]=>[" + (rowIndex - 1) + "][" + (columnIndex - 1) + "]";
    //             }

    //             if (isGoalCellEmpty((short) (rowIndex + 1), (short) (columnIndex - 1)) && currentBoard.boardCells[rowIndex][columnIndex].state == Cell.CellState.BLACK) {
    //                 if (!possibleMoves.equals("")) {
    //                     possibleMoves += ",";
    //                 } 
    //                 possibleMoves += "[" + rowIndex + "][" + columnIndex + "]=>[" + (rowIndex + 1) + "][" + (columnIndex - 1) + "]";
    //             }
    //         }
    //         return possibleMoves;
    //     } else {
    //         return NO_MOVE_POSSIBLE_RESULT;
    //     }
    // }

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
        if (areIndexesCorrect(cellPosition.yIndex, cellPosition.xIndex) && currentBoard.boardCells[cellPosition.yIndex][cellPosition.xIndex].state == state) {
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

    public static void makeMove(Cell[][] boardCells, MoveOption moveOption) {
        if (moveOption.intermediateTakings == null || moveOption.intermediateTakings.length == 0) {
            boardCells[moveOption.endPosition.yIndex][moveOption.endPosition.xIndex].state = boardCells[moveOption.startPosition.yIndex][moveOption.startPosition.xIndex].state;
            boardCells[moveOption.startPosition.yIndex][moveOption.startPosition.xIndex].state = Cell.CellState.EMPTY;
        }
    }

    public static Cell[][] returnDuplicateAfterMove(Cell[][] boardCells, MoveOption moveOption) {
        Cell[][] boardCellsAfterMove = new Cell[8][8];

        for(short i=0;i<8;i++) {
            for(short j=0;j<8;j++) {
                boardCellsAfterMove[i][j] = new Cell(boardCells[i][j].state);
            }
        }

        if (moveOption.intermediateTakings == null || moveOption.intermediateTakings.length == 0) {
            boardCellsAfterMove[moveOption.endPosition.yIndex][moveOption.endPosition.xIndex].state = boardCellsAfterMove[moveOption.startPosition.yIndex][moveOption.startPosition.xIndex].state;
            boardCellsAfterMove[moveOption.startPosition.yIndex][moveOption.startPosition.xIndex].state = Cell.CellState.EMPTY;
        }

        return boardCellsAfterMove;
    }

    // this method returns all possible takings for selected side
    public static ArrayList<MoveOption> getAllPossibleSingleTakings(Cell[][] boardCells, Boolean isWhiteToMove) {
        ArrayList<MoveOption> moveOptionsList = new ArrayList<MoveOption>();
        ArrayList<MoveOption> tmpMoveOptionsList;
        for (short i=0;i<8;i++) {
            for (short j=0;j<8;j++) {
                //ArrayList<MoveOption> getAllPossibleSingleTakings(Cell[][] boardCells, CellPosition cellPosition, Boolean isWhiteBoolean)
                tmpMoveOptionsList = getAllPossibleSingleTakings(boardCells, new CellPosition(i,j), isWhiteToMove);
                if (tmpMoveOptionsList != null) {
                    moveOptionsList.addAll(tmpMoveOptionsList);
                }
                // isSingleTakingPossible(boardCells, new CellPosition(i,j), isWhiteToMove);
            }
        }
        // ArrayList<MoveOption> moveOptionsArrayList = new ArrayList<MoveOption>();

        // Cell.CellState cellState;
        // if (boardCells[currentPosition.yIndex][currentPosition.xIndex].state == Cell.CellState.BLACK) {
        //     cellState = Cell.CellState.BLACK;
        // } else if (boardCells[currentPosition.yIndex][currentPosition.xIndex].state == Cell.CellState.WHITE) {
        //     cellState = Cell.CellState.WHITE;
        // } else {
        //     // no moves are possible, there is no piece in currentPosition ==> return null
        //     return null;
        // }

        return moveOptionsList;
    }


    //     if (hasCellState((short) (rowIndex + 1), (short) (columnIndex + 1), cellState) && isGoalCellEmpty((short) (rowIndex + 2), (short) (columnIndex + 2))) {
    //         moveOptionsArrayList.add(new MoveOption(new CellPosition(rowIndex, columnIndex), new CellPosition((short) (rowIndex + 2), (short) (columnIndex + 2))));
    //     }

    //     if (hasCellState((short) (rowIndex - 1), (short) (columnIndex + 1), cellState) && isGoalCellEmpty((short) (rowIndex - 2), (short) (columnIndex + 2))) {
    //         moveOptionsArrayList.add(new MoveOption(new CellPosition(rowIndex, columnIndex), new CellPosition((short) (rowIndex - 2), (short) (columnIndex + 2))));
    //     }

    //     if (hasCellState((short) (rowIndex - 1), (short) (columnIndex - 1), cellState) && isGoalCellEmpty((short) (rowIndex - 2), (short) (columnIndex - 2))) {
    //         moveOptionsArrayList.add(new MoveOption(new CellPosition(rowIndex, columnIndex), new CellPosition((short) (rowIndex - 2), (short) (columnIndex - 2))));
    //     }

    //     if (hasCellState((short) (rowIndex + 1), (short) (columnIndex - 1), cellState) && isGoalCellEmpty((short) (rowIndex + 2), (short) (columnIndex - 2))) {
    //         moveOptionsArrayList.add(new MoveOption(new CellPosition(rowIndex, columnIndex), new CellPosition((short) (rowIndex + 2), (short) (columnIndex - 2))));
    //     }

    //     return moveOptionsArrayList;
    // }

    // public static ArrayList<MoveOption> getSinglePossibleSingleTakings(Cell[][] boardCells, CellPosition cellPosition, Boolean isWhiteToMove) {
    //     ArrayList<MoveOption> moveOptionsArrayList = new ArrayList<MoveOption>();


    //     Cell.CellState cellState;
    //     if (currentBoard.isWhiteToMove) {
    //         cellState = Cell.CellState.BLACK;
    //     } else {
    //         cellState = Cell.CellState.WHITE;
    //     }

    //     if (hasCellState((short) (rowIndex + 1), (short) (columnIndex + 1), cellState) && isGoalCellEmpty((short) (rowIndex + 2), (short) (columnIndex + 2))) {
    //         moveOptionsArrayList.add(new MoveOption(new CellPosition(rowIndex, columnIndex), new CellPosition((short) (rowIndex + 2), (short) (columnIndex + 2))));
    //     }

    //     if (hasCellState((short) (rowIndex - 1), (short) (columnIndex + 1), cellState) && isGoalCellEmpty((short) (rowIndex - 2), (short) (columnIndex + 2))) {
    //         moveOptionsArrayList.add(new MoveOption(new CellPosition(rowIndex, columnIndex), new CellPosition((short) (rowIndex - 2), (short) (columnIndex + 2))));
    //     }

    //     if (hasCellState((short) (rowIndex - 1), (short) (columnIndex - 1), cellState) && isGoalCellEmpty((short) (rowIndex - 2), (short) (columnIndex - 2))) {
    //         moveOptionsArrayList.add(new MoveOption(new CellPosition(rowIndex, columnIndex), new CellPosition((short) (rowIndex - 2), (short) (columnIndex - 2))));
    //     }

    //     if (hasCellState((short) (rowIndex + 1), (short) (columnIndex - 1), cellState) && isGoalCellEmpty((short) (rowIndex + 2), (short) (columnIndex - 2))) {
    //         moveOptionsArrayList.add(new MoveOption(new CellPosition(rowIndex, columnIndex), new CellPosition((short) (rowIndex + 2), (short) (columnIndex - 2))));
    //     }

    //     return moveOptionsArrayList;
    // }

    public static ArrayList<MoveOption> getAllPossibleSingleTakings(Cell[][] boardCells, CellPosition cellPosition, Boolean isWhiteToMove) {
        Cell.CellState cellState = boardCells[cellPosition.yIndex][cellPosition.xIndex].state;
        // System.out.println("in getAllPossibleSingleTakings method");
        // System.out.println("cellState = " + cellState);
        // System.out.println("isWhiteToMove = " + isWhiteToMove);

        if ((cellState == Cell.CellState.EMPTY) || (isWhiteToMove && cellState == Cell.CellState.BLACK) || (!isWhiteToMove && cellState == Cell.CellState.WHITE)) {
            return null;
        }

        Cell.CellState oppositeSideCellState;
        if (isWhiteToMove) {
            oppositeSideCellState = Cell.CellState.BLACK;
        } else {
            oppositeSideCellState = Cell.CellState.WHITE;
        }

        // if (isWhiteToMove) {

        // }

        ArrayList<MoveOption> moveOptionsArrayList = new ArrayList<MoveOption>();

        // Cell.CellState cellState;
        // if (isWhiteToMove) {
        //     cellState = Cell.CellState.BLACK;
        // } else {
        //     cellState = Cell.CellState.WHITE;
        // }

        if (hasCellState(boardCells, new CellPosition((short) (cellPosition.yIndex + 1), (short) (cellPosition.xIndex +1)), oppositeSideCellState) && isGoalCellEmpty(boardCells, new CellPosition((short) (cellPosition.yIndex + 2), (short) (cellPosition.xIndex + 2)))) {
// System.out.println("in IF 1");
            moveOptionsArrayList.add(new MoveOption(cellPosition, new CellPosition((short) (cellPosition.yIndex + 2), (short) (cellPosition.xIndex + 2))));
        }

        if (hasCellState(boardCells, new CellPosition((short) (cellPosition.yIndex - 1), (short) (cellPosition.xIndex + 1)), oppositeSideCellState) && isGoalCellEmpty(boardCells, new CellPosition((short) (cellPosition.yIndex - 2), (short) (cellPosition.xIndex + 2)))) {
// System.out.println("in IF 2");
            moveOptionsArrayList.add(new MoveOption(cellPosition, new CellPosition((short) (cellPosition.yIndex - 2), (short) (cellPosition.xIndex + 2))));
        }

        if (hasCellState(boardCells, new CellPosition((short) (cellPosition.yIndex - 1), (short) (cellPosition.xIndex - 1)), oppositeSideCellState) && isGoalCellEmpty(boardCells, new CellPosition((short) (cellPosition.yIndex - 2), (short) (cellPosition.xIndex - 2)))) {
// System.out.println("in IF 3");
            moveOptionsArrayList.add(new MoveOption(cellPosition, new CellPosition((short) (cellPosition.yIndex - 2), (short) (cellPosition.xIndex - 2))));
        }

        if (hasCellState(boardCells, new CellPosition((short) (cellPosition.yIndex + 1), (short) (cellPosition.xIndex - 1)), oppositeSideCellState) && isGoalCellEmpty(boardCells, new CellPosition((short) (cellPosition.yIndex + 2), (short) (cellPosition.xIndex - 2)))) {
// System.out.println("in IF 4");
            moveOptionsArrayList.add(new MoveOption(cellPosition, new CellPosition((short) (cellPosition.yIndex + 2), (short) (cellPosition.xIndex - 2))));
        }

// for (MoveOption mo : moveOptionsArrayList) {
//     mo.displayMove();
// }

        return moveOptionsArrayList;
    }

    // public String checkTakingMoves(CellPosition currentCellPosition, Boolean isWhiteToMove) {
    //     Cell.CellState cellState;
    //     if (isWhiteToMove) {
    //         cellState = Cell.CellState.BLACK;
    //     } else {
    //         cellState = Cell.CellState.WHITE;
    //     }
    // }

    // public String checkTakingMoves(CellPosition previousCellPosition, Boolean isWhiteToMove) {
    //     Cell.CellState cellState;
    //     if (isWhiteToMove) {
    //         cellState = Cell.CellState.BLACK;
    //     } else {
    //         cellState = Cell.CellState.WHITE;
    //     }
//=========

        // if (hasCellState((short) (rowIndex + 1), (short) (columnIndex + 1), cellState) && isGoalCellEmpty((short) (rowIndex + 2), (short) (columnIndex + 2))) {
            // previous state ==> BOTTOM_LEFT

            // checkTakingMoves(new CellPosition((short) (rowIndex + 2), (short) (columnIndex + 2)), MoveOption.PreviousState.BOTTOM_LEFT, new HashMap<String,>());
            // moveOptionsArrayList.add(new MoveOption(new CellPosition(rowIndex, columnIndex), new CellPosition((short) (rowIndex + 2), (short) (columnIndex + 2))));
        // }

        // if (taking is possible) {
        //     - add previous fields to historyString
        //     - trigger function checkTakings with next possible
        // }

        // check taking TOP-LEFT
        // if () {

        // }
        // check taking TOP-RIGHT
        // check taking BOTTOM-LEFT
        // check taking BOTTOM-RIGHT
    // }
// MoveOption.PreviousState

    // public String checkTakingMoves(CellPosition nextCellPosition, MoveOption.PreviousState previousState, HashMap overlayCellsMap, String takingsHistory) {}

    // Return true if more than one taking is possible in a single move
    // public static Boolean areManyTakingsPossible() {
    //     return false;
    // }

    public static int getNumberOfPossibleMoves(Boolean isWhiteToMove) {
        if (isWhiteToMove) {
            return 5;
        } else {
            return -5;
        }
    }

    public static void main(String[] args) {
        System.out.println("Inside BoardManager: a main class responsible for results display");

        readBoardFromFile("./res/board.txt");
        // displayCurrentBoard();

        // DiscountedPawnsValuationManager discountedPawnsValuationManager = new DiscountedPawnsValuationManager();
        // System.out.println("positionValuationManager.valuatePosition(board) == " + discountedPawnsValuationManager.valuatePosition(currentBoard));

        // LimitingOpponentMovesValuationManager limitingOppMovesValuationManager = new LimitingOpponentMovesValuationManager();
        // System.out.println("limitingOppMovesValuationManager.valuatePosition(board) == " + limitingOppMovesValuationManager.valuatePosition(currentBoard));

        // System.out.println("isSingleTakingPossible(3,3) == " + isSingleTakingPossible((short) 3,(short) 3));

        CellPosition startPosition = new CellPosition((short) 0, (short) 0);
        CellPosition endPosition = new CellPosition((short) 1, (short) 1);

        Cell[][] boardCellsAfterMove = new Cell[8][8];

        // for(short i=0;i<8;i++) {
        //     for(short j=0;j<8;j++) {
        //         boardCellsAfterMove[i][j] = new Cell(currentBoard.boardCells[i][j].state);
        //     }
        // }

        // displayCurrentBoard();
        // MoveOption moveOption = new MoveOption(startPosition, endPosition);
        // displayBoard(currentBoard.boardCells);
        // System.out.println();
        // boardCellsAfterMove = returnDuplicateAfterMove(currentBoard.boardCells, moveOption);
        // displayBoard(boardCellsAfterMove);
// Cell[][] boardCellsAfterMove =
// 
// makeMove(currentBoard.boardCells, moveOption);
// displayBoard(boardCellsAfterMove);
        // displayCurrentBoard();

// System.out.println("Moves display:");
// for (MoveOption moveOption : getAllPossibleSingleTakings(currentBoard.boardCells, new CellPosition((short) 3, (short) 3), currentBoard.isWhiteToMove)) {
//     moveOption.displayMove();
// }
// ArrayList<MoveOption> getAllPossibleSingleTakings(Cell[][] boardCells, Boolean isWhiteToMove)
        
    // System.out.println("getAllPossibleSingleTakings = " + getAllPossibleSingleTakings(currentBoard.boardCells, currentBoard.isWhiteToMove));
    
        System.out.println("getAllPossibleSingleTakings:");

        // getAllPossibleSingleTakings(currentBoard.boardCells, new CellPosition((short) 3, (short) 3), currentBoard.isWhiteToMove);
        for (MoveOption mo : getAllPossibleSingleTakings(currentBoard.boardCells, currentBoard.isWhiteToMove)) {
            mo.displayMove();
        }
    }
}