package UniversalMethod.Model;

/**
 * Created by Kamil on 2018-04-23.
 */
public class Cell {
    private int state, nextState;

    public Cell() {
        state = 0;
        nextState = 0;
    }

    public void copy(int oldState){
        this.state = oldState;
    }

    public int getNextState() {
        return nextState;
    }

    public void setNextState(int nextState) {
        this.nextState = nextState;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
