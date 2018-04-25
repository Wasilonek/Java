package model;

/**
 * Created by Kamil on 2018-04-24.
 */
public class Game {
    private Cell[][] cellArray;
    private int size = 600;
    private int alive = 1;
    private int dead = 0;
    private int aliveNeighbour;

    public Game() {
        cellArray = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cellArray[i][j] = new Cell();
            }
        }
        aliveNeighbour = 0;
    }

    public void clearCellArray() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                cellArray[i][j].setState(0);
                cellArray[i][j].setNextState(0);
            }
        }
    }

    public void setInconstantsStructure() {
        cellArray[50][50].setState(alive);
        cellArray[50][52].setState(alive);
        cellArray[50][54].setState(alive);
        cellArray[51][50].setState(alive);
        cellArray[51][54].setState(alive);
        cellArray[52][50].setState(alive);
        cellArray[52][54].setState(alive);
        cellArray[53][50].setState(alive);
        cellArray[53][54].setState(alive);
        cellArray[54][50].setState(alive);
        cellArray[54][52].setState(alive);
        cellArray[54][54].setState(alive);
    }

    public void setUnchangingStructure() {
        cellArray[50][50].setState(alive);
        cellArray[50][51].setState(alive);
        cellArray[51][49].setState(alive);
        cellArray[51][52].setState(alive);
        cellArray[52][50].setState(alive);
        cellArray[52][51].setState(alive);

    }

    public void setOscillatorsStructure() {
        cellArray[50][50].setState(alive);
        cellArray[50][51].setState(alive);
        cellArray[50][52].setState(alive);
        cellArray[51][49].setState(alive);
        cellArray[51][50].setState(alive);
        cellArray[51][51].setState(alive);

    }

    public void setGliderStructure() {

        cellArray[50][50].setState(alive);
        cellArray[50][53].setState(alive);
        cellArray[51][54].setState(alive);
        cellArray[52][50].setState(alive);
        cellArray[52][54].setState(alive);
        cellArray[53][51].setState(alive);
        cellArray[53][52].setState(alive);
        cellArray[53][53].setState(alive);
        cellArray[53][54].setState(alive);

    }

    public void setGunStructure() {

        cellArray[50][50].setState(alive);
        cellArray[51][50].setState(alive);
        cellArray[52][50].setState(alive);

        cellArray[49][51].setState(alive);
        cellArray[53][51].setState(alive);

        cellArray[48][52].setState(alive);
        cellArray[54][52].setState(alive);

        cellArray[48][53].setState(alive);
        cellArray[54][53].setState(alive);

        cellArray[51][54].setState(alive);

        cellArray[49][55].setState(alive);
        cellArray[53][55].setState(alive);

        cellArray[50][56].setState(alive);
        cellArray[51][56].setState(alive);
        cellArray[52][56].setState(alive);

        cellArray[51][57].setState(alive);

        cellArray[50][60].setState(alive);
        cellArray[49][60].setState(alive);
        cellArray[48][60].setState(alive);

        cellArray[50][61].setState(alive);
        cellArray[49][61].setState(alive);
        cellArray[48][61].setState(alive);

        cellArray[51][62].setState(alive);
        cellArray[47][62].setState(alive);

        cellArray[46][64].setState(alive);
        cellArray[47][64].setState(alive);
        cellArray[51][64].setState(alive);
        cellArray[52][64].setState(alive);

        //left square
        cellArray[50][41].setState(alive);
        cellArray[51][41].setState(alive);

        cellArray[50][40].setState(alive);
        cellArray[51][40].setState(alive);

        //right square
        cellArray[49][74].setState(alive);
        cellArray[48][74].setState(alive);

        cellArray[49][75].setState(alive);
        cellArray[48][75].setState(alive);

    }


    public void drawCellArray() {
        for (Cell x[] : cellArray) {
            for (Cell y : x) {
                if (y.getState() == alive) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public Cell[][] gameRules() {

        for (int i = 1; i < size - 1; i++) {
            for (int j = 1; j < size - 1; j++) {
                if (cellArray[i - 1][j - 1].getState() == alive) aliveNeighbour++;
                if (cellArray[i - 1][j].getState() == alive) aliveNeighbour++;
                if (cellArray[i - 1][j + 1].getState() == alive) aliveNeighbour++;
                if (cellArray[i][j - 1].getState() == alive) aliveNeighbour++;
                if (cellArray[i][j + 1].getState() == alive) aliveNeighbour++;
                if (cellArray[i + 1][j - 1].getState() == alive) aliveNeighbour++;
                if (cellArray[i + 1][j].getState() == alive) aliveNeighbour++;
                if (cellArray[i + 1][j + 1].getState() == alive) aliveNeighbour++;


                if (cellArray[i][j].getState() == alive && aliveNeighbour < 2) {
                    cellArray[i][j].setNextState(0);
                }
                if (cellArray[i][j].getState() == alive && aliveNeighbour == 2 || aliveNeighbour == 3) {
                    cellArray[i][j].setNextState(1);
                }
                if (cellArray[i][j].getState() == alive && aliveNeighbour > 3) {
                    cellArray[i][j].setNextState(0);
                }
                if (cellArray[i][j].getState() == dead && aliveNeighbour == 3) {
                    cellArray[i][j].setNextState(1);
                }
                aliveNeighbour = 0;
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cellArray[i][j].setState(cellArray[i][j].getNextState());
            }
        }

        return cellArray;
    }

    public int getCell(int i, int j) {
        return cellArray[i][j].getState();
    }


    public Cell[][] getCellArray() {
        return cellArray;
    }

    public void setCellArray(Cell[][] cellArray) {
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

    public int getAliveNeighbour() {
        return aliveNeighbour;
    }

    public void setAliveNeighbour(int aliveNeighbour) {
        this.aliveNeighbour = aliveNeighbour;
    }
}
