package Grains.model;

import Grains.controller.GrainsController;
import javafx.concurrent.Task;

/**
 * Created by Kamil on 2018-06-09.
 */
public class MonteCarloTask extends Task {
    private GrainsController grainsController;
    private boolean stopStatus;
    private int speed;

    public MonteCarloTask(GrainsController grainsController) {
        this.grainsController = grainsController;
        stopStatus = false;
        speed = 20;
    }

    @Override
    protected Object call() throws Exception {
        while (!stopStatus) {
            grainsController.nextMonteCarloPeriod();
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
