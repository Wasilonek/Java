package model;

import controller.MainController;
import javafx.concurrent.Task;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by Kamil on 2018-04-26.
 */
public class DrawerTask extends Task {
    GraphicsContext graphicsContext;
    private int repetition;
    MainController mainController;
    Game game;
    boolean stopStatus;

    public DrawerTask(GraphicsContext gc, int repetition, MainController mainController) {
        this.mainController = mainController;
        this.graphicsContext = gc;
        this.repetition = repetition;
        stopStatus = false;
    }


    @Override
    protected Object call() throws Exception {
        while (!stopStatus) {
            mainController.draw();
            Thread.sleep(200);
        }
        return 1;
    }

    public boolean isStopStatus() {
        return stopStatus;
    }

    public void setStopStatus(boolean stopStatus) {
        this.stopStatus = stopStatus;
    }
}
