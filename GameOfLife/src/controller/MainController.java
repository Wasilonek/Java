package controller;


import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import model.Game;


public class MainController {

    @FXML
    Button nextButton;

    @FXML
    Canvas canvas;

    private GraphicsContext graphicsContext;

    private Game game;

    private int cellWidth, cellHeight, numberOfSteps;

    public MainController() {
        game = new Game();
        numberOfSteps = 5;
        cellWidth = 10;
        cellHeight = 10;


    }

    @FXML
    void initialize() {
        graphicsContext = canvas.getGraphicsContext2D();

        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                int x = (int) me.getX();
                int y = (int) me.getY();
                graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                System.out.println(me.getX() + " " + me.getY());
                game.setCell(((int) me.getX() / cellWidth), ((int) me.getY() / cellHeight));
//                System.out.println(game.getCell(x, y));
            }
        });
    }

    public void unchangingStructureButtonAction() {
        game.clearCellArray();
        game.setUnchangingStructure();
    }

    public void oscillatorsButtonAction() {
        game.clearCellArray();
        game.setOscillatorsStructure();
    }

    public void inconstantStructureButtonAction() {
        game.clearCellArray();
        game.setInconstantsStructure();
    }

    public void gliderButtonAction() {
        game.clearCellArray();
        game.setGliderStructure();
    }

    public void GunButtonAction() {
        game.clearCellArray();
        game.setGunStructure();
    }

    public void randomButtonAction() {
        game.clearCellArray();
        game.randomCells();
    }

    public void nextButtonAction() {
        draw();
    }

    public void nextFiveStepsButtonAction() {
        for (int i = 0; i < 5; i++) {
            game.gameRules();
        }
        draw();

    }


    public void draw() {
        graphicsContext.clearRect(0, 0, 600, 600);
        for (int j = 0; j < game.getCellArray().length; j++) {
            for (int k = 0; k < game.getCellArray().length; k++) {
                if (game.getCell(j, k) == 1) {
                    graphicsContext.fillRect(k * cellWidth, j * cellHeight, cellWidth, cellHeight);
                }
            }
        }
        game.gameRules();
    }

    public void drawWithoutClear() {
        for (int j = 0; j < game.getCellArray().length; j++) {
            for (int k = 0; k < game.getCellArray().length; k++) {
                if (game.getCell(j, k) == 1) {
                    graphicsContext.fillRect(k * cellWidth, j * cellHeight, cellWidth, cellHeight);
                }
            }
        }
        game.gameRules();
    }

    public void startButtonAction() {
        for (int i = 0; i < numberOfSteps; i++) {
            func();
        }
    }

    private void func() {
        for (int i = 0; i < numberOfSteps; i++) {
            draw();
//            SequentialTransition seqTransition = new SequentialTransition (
//                    new PauseTransition(Duration.millis(1000)) // wait a second
//            );
//            seqTransition.play();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clearCanvas();
        }
    }

    public void clearCanvas() {
        graphicsContext.clearRect(0, 0, 600, 600);
    }

    public void clearButtonAction() {
        game.clearCellArray();
        graphicsContext.clearRect(0, 0, 600, 600);
    }
}
