/**
 * Created by Kamil on 2018-04-19.
 */
public class Cell {
    private int oldState;
    private int newState;
    private int rightNeighbourState;
    private int leftNeighbourState;

    public Cell(int oldState, int rightNeighbourState, int leftNeighbourState) {
        this.oldState = oldState;
        this.rightNeighbourState = rightNeighbourState;
        this.leftNeighbourState = leftNeighbourState;
    }

    public void copy(Cell cell) {
        oldState = cell.oldState;
        rightNeighbourState = cell.rightNeighbourState;
        leftNeighbourState = cell.leftNeighbourState;
    }


    public int getNewState() {
        return newState;
    }

    public void setNewState(int newState) {
        this.newState = newState;
    }

    public Cell(int oldState) {
        this.oldState = oldState;
    }

    public int getOldState() {
        return oldState;
    }

    public void setOldState(int oldState) {
        this.oldState = oldState;
    }

    public int getRightNeighbourState() {
        return rightNeighbourState;
    }

    public void setRightNeighbourState(int rightNeighbourState) {
        this.rightNeighbourState = rightNeighbourState;
    }

    public int getLeftNeighbourState() {
        return leftNeighbourState;
    }

    public void setLeftNeighbourState(int leftNeighbourState) {
        this.leftNeighbourState = leftNeighbourState;
    }
}
