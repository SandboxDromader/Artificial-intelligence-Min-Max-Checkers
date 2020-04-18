public class CellPosition {
    public short xIndex;
    public short yIndex;
 
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof CellPosition)) {
            return false;
        } else {
            CellPosition cellPosition = (CellPosition) o;
            if (this.xIndex == cellPosition.xIndex && this.yIndex == cellPosition.yIndex) {
                return true;
            } else {
                return false;
            }
        }
    }

    public CellPosition(short xIndex, short yIndex) {
        this.xIndex = xIndex;
        this.yIndex = yIndex;
    }
}