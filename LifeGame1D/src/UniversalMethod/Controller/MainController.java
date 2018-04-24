package UniversalMethod.Controller;

import UniversalMethod.Model.GameRules;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


/**
 * Created by Kamil on 2018-04-23.
 */
public class MainController {

    @FXML
    private Button drawButton;

    @FXML
    private Canvas canvas;

    @FXML
    private TextField textField;

    private GraphicsContext graphicsContext;

    private GameRules gameRules;

    public MainController() {
        gameRules = new GameRules();
    }

    @FXML
    void initialize() {
        graphicsContext = canvas.getGraphicsContext2D();
    }

    public void textFiledAction(){
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    gameRules.setDecimalNumberOfRule(Integer.valueOf(textField.getText()));
                    gameRules.fillCellArray();
                    gameRules.getBit();
                }
            }
        });
    }

    public void drawButtonAction(){
        drawCells();
    }

    public void drawCells() {

        graphicsContext.clearRect(0,0,600,400);
        int numberOfPeriod = 100;
        int cellWidth = (int) canvas.getWidth() / gameRules.getCellArray().length;
        int cellHeight = (int) canvas.getHeight() / numberOfPeriod;

        for (int i = 0; i < numberOfPeriod; i++) {
            for (int j = 0; j < gameRules.getCellArray().length; j++) {
                if (gameRules.getCell(j) == 1) {
                    graphicsContext.fillRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                }
            }
            gameRules.generateNextPopulation();
        }
    }

}
