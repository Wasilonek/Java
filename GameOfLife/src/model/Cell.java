package model;

/**
 * Created by Kamil on 2018-04-24.
 */
public class Cell {
    private int state, nextState, id;

    public Cell() {
        state = nextState = id = 0;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getNextState() {
        return nextState;
    }

    public void setNextState(int nextState) {
        this.nextState = nextState;
    }
}
