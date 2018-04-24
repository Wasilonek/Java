package controller;


import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import model.Game;


public class MainController {

    @FXML
    Button nextButton;

    @FXML
    Canvas canvas;

    private GraphicsContext graphicsContext;

    private Game game;

    private int cellWidth, cellHeight;

    public MainController() {
        game = new Game();
    }

    @FXML
    void initialize() {
        graphicsContext = canvas.getGraphicsContext2D();
    }

    public void draw() {


        graphicsContext.clearRect(0, 0, 600, 600);
        int numberOfPeriod = 100;
//        int cellWidth = (int) canvas.getWidth() / game.getCellArray().length;
//        int cellHeight = (int) canvas.getHeight() / numberOfPeriod;
        int cellWidth = 4;
        int cellHeight = 4;

        for (int j = 0; j < game.getCellArray().length; j++) {
            for (int k = 0; k < game.getCellArray().length; k++) {
                if (game.getCell(j, k) == 1) {
                    graphicsContext.fillRect(k * cellWidth, j * cellHeight, cellWidth, cellHeight);
                }
            }

        }
        game.gameRules();
    }


    public void nextButtonAction() {
        System.out.println("Kamil");
        draw();
    }
}
