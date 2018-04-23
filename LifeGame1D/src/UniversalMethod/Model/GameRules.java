package UniversalMethod.Model;

/**
 * Created by Kamil on 2018-04-23.
 */
public class GameRules {
    private Cell cellArray[];
    private int size = 50;
    private int alive = 1;
    private int dead = 0;
    private int actual, next, prev;
    private int numberOfRule[];

    public GameRules() {
        cellArray = new Cell[size];
        for (int i = 0; i < size; i++) {
            cellArray[i] = new Cell();
        }
        cellArray[size / 2].setState(alive);
        numberOfRule = new int[8];
    }

    public void getBit() {
        for (int i = 0; i < 8; i++) {
            numberOfRule[i] = (250 >> i) & 1;
            System.out.println(numberOfRule[i]);
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

            if (prev == alive && actual == alive && next == alive)
                cellArray[i].setNextState(numberOfRule[0]);

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
}