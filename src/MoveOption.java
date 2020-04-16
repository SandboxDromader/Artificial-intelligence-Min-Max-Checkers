public class MoveOption {
    // enum PreviousState {
    //     TOP_RIGHT,
    //     BOTTOM_RIGHT,
    //     BOTTOM_LEFT,
    //     TOP_LEFT
    // }

    public CellPosition startPosition;
    public CellPosition endPosition;
    public CellPosition[] intermediateTakings;

    public MoveOption() {}

    public MoveOption(CellPosition startPosition, CellPosition endPosition) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public MoveOption(CellPosition startPosition, CellPosition endPosition, CellPosition[] intermediateTakings) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.intermediateTakings = intermediateTakings;
    }

    public String displayCoordinates(CellPosition position) {
       return "x[" + position.xIndex + "],y[" + position.yIndex + "]"; 
    }

    public String getMove() {
        String fullMove = displayCoordinates(startPosition) + "=>";

        if (intermediateTakings != null) {
            for (CellPosition pos : intermediateTakings) {
                fullMove += displayCoordinates(pos) + "=>";
            }
        }

        return fullMove + displayCoordinates(endPosition);
    }

    public void displayMove() {
        System.out.println(getMove());
    }
}