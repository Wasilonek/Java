package UniversalMethod.Model;

/**
 * Created by Kamil on 2018-04-23.
 */
public class GameRules {

    private int decimalNumberOfRule;
    private Cell cellArray[];
    private int size = 200;
    private int alive = 1;
    private int dead = 0;
    private int actual, next, prev;
    private int numberOfRule[];

    public GameRules() {
        decimalNumberOfRule = 90;
        cellArray = new Cell[size];
        for (int i = 0; i < size; i++) {
            cellArray[i] = new Cell();
        }
        cellArray[size / 2].setState(alive);
        numberOfRule = new int[8];
        getBit();
    }

    public void fillCellArray() {
        for (int i = 0; i < cellArray.length; i++) {
            cellArray[i].setState(0);
            cellArray[i].setNextState(0);
        }
        cellArray[size / 2].setState(1);
    }

    public void getBit() {
        for (int i = 0; i < 8; i++) {
            numberOfRule[i] = (decimalNumberOfRule >> i) & 1;
        }

    }

    public void draw() {
        for (int i = 0; i < cellArray.length; i++) {
            if (cellArray[i].getState() == alive) System.out.print("*");
            else System.out.print(" ");
        }
        System.out.println();
    }

    public Cell[] generateNextPopulation() {

        actual = prev = next = 0;

        for (int i = 0; i < size; i++) {
            if (i == 0) {
                actual = cellArray[i].getState();
                next = cellArray[i + 1].getState();
                prev = cellArray[size - 1].getState();
            } else if (i == size - 1) {
                actual = cellArray[i].getState();
                next = cellArray[0].getState();
                prev = cellArray[i - 1].getState();
            } else if (i > 0 && i < size - 1) {
                actual = cellArray[i].getState();
                next = cellArray[i + 1].getState();
                prev = cellArray[i - 1].getState();
            }


            if (prev == alive && actual == alive && next == alive) cellArray[i].setNextState(numberOfRule[7]);
            if (prev == alive && actual == alive && next == dead) cellArray[i].setNextState(numberOfRule[6]);
            if (prev == alive && actual == dead && next == alive) cellArray[i].setNextState(numberOfRule[5]);
            if (prev == alive && actual == dead && next == dead) cellArray[i].setNextState(numberOfRule[4]);
            if (prev == dead && actual == alive && next == alive) cellArray[i].setNextState(numberOfRule[3]);
            if (prev == dead && actual == alive && next == dead) cellArray[i].setNextState(numberOfRule[2]);
            if (prev == dead && actual == dead && next == alive) cellArray[i].setNextState(numberOfRule[1]);
            if (prev == dead && actual == dead && next == dead) cellArray[i].setNextState(numberOfRule[0]);

        }

        for (int i = 0; i < size; i++) {
            cellArray[i].copy(cellArray[i].getNextState());
        }
        return cellArray;
    }

    public int getCell(int i) {
        return cellArray[i].getState();
    }

    public void setCell(int i) {
        cellArray[i].setState(i);
    }

    public Cell[] getCellArray() {
        return cellArray;
    }

    public void setCellArray(Cell[] cellArray) {
        this.cellArray = cellArray;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getAlive() {
        return alive;
    }

    public void setAlive(int alive) {
        this.alive = alive;
    }

    public int getDead() {
        return dead;
    }

    public void setDead(int dead) {
        this.dead = dead;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getPrev() {
        return prev;
    }

    public void setPrev(int prev) {
        this.prev = prev;
    }

    public int[] getNumberOfRule() {
        return numberOfRule;
    }

    public void setNumberOfRule(int[] numberOfRule) {
        this.numberOfRule = numberOfRule;
    }

    public int getDecimalNumberOfRule() {
        return decimalNumberOfRule;
    }

    public void setDecimalNumberOfRule(int decimalNumberOfRule) {
        this.decimalNumberOfRule = decimalNumberOfRule;
    }
}