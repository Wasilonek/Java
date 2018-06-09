package Grains.model;

import javafx.scene.paint.Color;

/**
 * Created by Kamil on 2018-05-23.
 */
public class Grain {
    private int state, nextState, id, newId;
    private Color color, newColor;
    private int energy;

    public Grain() {
        state = nextState = 0;
        id = newId = -1;
        color = Color.GREEN;
        newColor = Color.WHITE;
        energy = 0;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNewId(int newId) {
        this.newId = newId;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setNewColor(Color newColor) {
        this.newColor = newColor;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
}
