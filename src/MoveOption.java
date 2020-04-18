import java.util.ArrayList;

public class MoveOption {
    public CellPosition startPosition;
    public CellPosition endPosition;
    public ArrayList<CellPosition> intermediateTakingsArrayList;

    public MoveOption() {}

    public MoveOption(CellPosition startPosition, CellPosition endPosition) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public MoveOption(CellPosition startPosition, CellPosition endPosition, ArrayList<CellPosition> intermediateTakingsArrayList) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.intermediateTakingsArrayList = intermediateTakingsArrayList;
    }

    public MoveOption(MoveOption toBeExtended, MoveOption toBeAdded) {
        startPosition = toBeExtended.startPosition;
        intermediateTakingsArrayList = new ArrayList<CellPosition>();

        if (toBeExtended.intermediateTakingsArrayList != null && toBeExtended.intermediateTakingsArrayList.size() > 0) {
            intermediateTakingsArrayList.addAll(toBeExtended.intermediateTakingsArrayList);
        }

        if (toBeExtended.endPosition == toBeAdded.startPosition) {
            intermediateTakingsArrayList.add(toBeExtended.endPosition);
            if (toBeAdded.intermediateTakingsArrayList != null) {
                intermediateTakingsArrayList.addAll(toBeAdded.intermediateTakingsArrayList);
            }
            endPosition = toBeAdded.endPosition;
        }
    }

    public String displayCoordinates(CellPosition position) {
       return "x[" + position.xIndex + "],y[" + position.yIndex + "]"; 
    }

    public String getMove() {
        String fullMove = displayCoordinates(startPosition) + "=>";

        if (intermediateTakingsArrayList != null) {
            for (CellPosition pos : intermediateTakingsArrayList) {
                fullMove += displayCoordinates(pos) + "=>";
            }
        }

        return fullMove + displayCoordinates(endPosition);
    }

    public void displayMove() {
        System.out.println(getMove());
    }
}