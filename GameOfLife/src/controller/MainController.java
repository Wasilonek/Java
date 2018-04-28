package controller;


import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import model.DrawerTask;
import model.Game;


public class MainController {

    @FXML
    private Slider speedSlider;

    @FXML
    private Canvas canvas;

    @FXML
    private ChoiceBox<String> structureChoiceBox;

    private GraphicsContext graphicsContext;

    private Game game;

    private int cellWidth, cellHeight;

    private DrawerTask drawerTask;

    private boolean isStartOn, isOneDrawnigThread;


    public MainController() {
        cellWidth = 5;
        cellHeight = 5;
        isStartOn = false;
        isOneDrawnigThread = true;
    }

    @FXML
    void initialize() {
        game = new Game((int) canvas.getWidth() / cellWidth);
        graphicsContext = canvas.getGraphicsContext2D();
        canvas.setOnMousePressed(me -> {
            int x = (int) me.getX();
            int y = (int) me.getY();
            System.out.println(x + " , " + y);
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
            game.setCell(((int) me.getX() / cellWidth), ((int) me.getY() / cellHeight));
        });

        setStructureChoiceBoxItems();
        structureChoiceBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> setStructure(newValue));
    }

    public void nextButtonAction() {
        if (!isStartOn)
            drawCanvas();
    }

    public void startButtonAction() {
        isStartOn = true;
        if (isOneDrawnigThread) {
            drawerTask = new DrawerTask(this);
            new Thread(drawerTask).start();
            isOneDrawnigThread = false;
        }
        speedSlider.setValue(drawerTask.getSpeed());
        speedSlider.valueProperty().addListener(observable -> drawerTask.setSpeed((int) speedSlider.getValue()));
    }

    public void stopButtonAction() {
        isStartOn = false;
        isOneDrawnigThread = true;
        drawerTask.setStopStatus(true);

    }

    public void clearButtonAction() {
        game.clearCellArray();
        clearCanvas();
    }

    private void setStructureChoiceBoxItems() {
        structureChoiceBox.getItems().addAll("Unchanging structure", "Oscillators", "Inconstant structure",
                "Glider", "Gun", "Random");
    }

    private void setStructure(String newValue) {
        String choice = newValue;
        switch (choice) {
            case "Unchanging structure": {
                game.clearCellArray();
                game.setUnchangingStructure();
                break;
            }
            case "Oscillators": {
                game.clearCellArray();
                game.setOscillatorsStructure();
                break;
            }
            case "Inconstant structure": {
                game.clearCellArray();
                game.setInconstantsStructure();
                break;
            }
            case "Glider": {
                game.clearCellArray();
                game.setGliderStructure();
                break;
            }
            case "Gun": {
                game.clearCellArray();
                game.setGunStructure();
                break;
            }
            case "Random": {
                game.clearCellArray();
                game.randomCells();
                break;
            }
        }
    }

    public void drawCanvas() {
        clearCanvas();
        for (int j = 0; j < game.getCellArray().length; j++) {
            for (int k = 0; k < game.getCellArray().length; k++) {
                if (game.getCell(j, k) == 1) {
                    graphicsContext.fillOval(j * cellWidth, k * cellHeight, cellWidth, cellHeight);
                }
            }
        }
        game.gameRules();
    }

    public void clearCanvas() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

}
