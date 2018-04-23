package ThreeMethods;

/**
 * Created by Kamil on 2018-04-19.
 */
public class NinetyCellRule extends CellRule {

    public NinetyCellRule() {
        size = 50;
        alive = 1;
        dead = 0;
        rows = 50;
        oldCellArray = new Cell[size];
        for (int i = 0; i < size; i++) {
            oldCellArray[i] = new Cell(dead);
        }
        oldCellArray[25].setOldState(alive);
    }

    public void play() {
        int j = 0;
        int next = 0, prev = 0;
        while (j < rows) {
            for (int i = 0; i < size; i++) {
                if (i == 0) {
                    next = oldCellArray[i + 1].getOldState();
                    prev = oldCellArray[size - 1].getOldState();
                } else if (i == size - 1) {
                    next = oldCellArray[0].getOldState();
                    prev = oldCellArray[i - 1].getOldState();
                } else if (i > 0 & i < size - 1) {
                    next = oldCellArray[i + 1].getOldState();
                    prev = oldCellArray[i - 1].getOldState();
                }
                if (next == alive & prev == alive) {
                    oldCellArray[i].setNewState(dead);
                } else if (next == alive | prev == alive) {
                    oldCellArray[i].setNewState(alive);
                } else if (next == dead & prev == dead) {
                    oldCellArray[i].setNewState(dead);
                }
                if (oldCellArray[i].getNewState() == alive)
                    System.out.print("*");
                else
                    System.out.print(" ");
            }
            for (int y = 0; y < size; y++) {
                oldCellArray[y].setOldState(oldCellArray[y].getNewState());
            }
            j++;
            System.out.println();
        }
    }
}
