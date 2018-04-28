package model;

import controller.MainController;
import javafx.concurrent.Task;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by Kamil on 2018-04-26.
 */
public class DrawerTask extends Task {
    GraphicsContext graphicsContext;
    MainController mainController;
    boolean stopStatus;
    private int speed;

    public DrawerTask(GraphicsContext gc, MainController mainController) {
        this.mainController = mainController;
        this.graphicsContext = gc;
        stopStatus = false;
        speed = 500;
    }

    @Override
    protected Object call() throws Exception {
        while (!stopStatus) {
            mainController.drawCanvas();
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
