package Grains.model;

import Grains.controller.GrainsController;
import javafx.concurrent.Task;


/**
 * Created by Kamil on 2018-05-23.
 */
public class GrainsTask extends Task {
    private GrainsController grainsController;
    private boolean stopStatus;
    private int speed;

    public GrainsTask(GrainsController grainsController) {
        this.grainsController = grainsController;
        stopStatus = false;
        speed = 100;
    }

    @Override
    protected Object call() throws Exception {
        while (!stopStatus) {
            grainsController.drawCanvas();
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
