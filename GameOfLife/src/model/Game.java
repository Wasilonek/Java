package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kamil on 2018-04-24.
 */
public class Game {
    private Cell[][] cellArray;
    private int size = 600;
    private int alive = 1;
    private int dead = 0;
    private int aliveNeighbour;
    //   private Map<Integer , String> neighbours;

    public Game() {
        cellArray = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cellArray[i][j] = new Cell();
            }
        }
//        cellArray[5][5].setState(alive);
//        cellArray[5][7].setState(alive);
//        cellArray[5][9].setState(alive);
//        cellArray[6][5].setState(alive);
//        cellArray[6][9].setState(alive);
//        cellArray[7][5].setState(alive);
//        cellArray[7][9].setState(alive);
//        cellArray[8][5].setState(alive);
//        cellArray[8][9].setState(alive);
//        cellArray[9][5].setState(alive);
//        cellArray[9][7].setState(alive);
//        cellArray[9][9].setState(alive);

        cellArray[50][50].setState(alive);
        cellArray[50][51].setState(alive);
        cellArray[50][52].setState(alive);
        cellArray[51][50].setState(alive);
        cellArray[52][51].setState(alive);


//        cellArray[6][9].setState(alive);
//        cellArray[7][5].setState(alive);
//        cellArray[7][9].setState(alive);
//        cellArray[8][5].setState(alive);
//        cellArray[8][9].setState(alive);
//        cellArray[9][5].setState(alive);
//        cellArray[9][7].setState(alive);
//        cellArray[9][9].setState(alive);

        aliveNeighbour = 0;
//        neighbours = new HashMap< Integer , String >();
//        neighbours.put(0 , "aliveNeighbour");
//        neighbours.put(0 , "deadNeighbour");
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
