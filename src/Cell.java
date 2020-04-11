public class Cell {
    enum CellState {
        EMPTY,
        BLACK,
        WHITE
    }

    public CellState state;

    public Cell(Cell.CellState state) {
        this.state = state;
    }
}