package model;

import controller.GameOfLifeController;
import javafx.concurrent.Task;

/**
 * Created by Kamil on 2018-04-26.
 */
public class DrawerTask extends Task {
    private GameOfLifeController gameOfLifeController;
    private boolean stopStatus;
    private int speed;

    public DrawerTask(GameOfLifeController gameOfLifeController) {
        this.gameOfLifeController = gameOfLifeController;
        stopStatus = false;
        speed = 500;
    }

    @Override
    protected Object call() throws Exception {
        while (!stopStatus) {
            gameOfLifeController.drawCanvas();
            Thread.sleep(speed);
        }
        return 1;
    }

    public void setStopStatus(boolean stopStatus) {
        this.stopStatus = stopStatus;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

}
