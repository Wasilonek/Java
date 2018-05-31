package Grains.controller;

import Grains.model.GrainsTask;
import Grains.model.GrowthGrains;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;


/**
 * Created by Kamil on 2018-05-23.
 */
public class GrainsController {

    @FXML
    Canvas grainCanvas;

    @FXML
    TextField widthField;

    @FXML
    TextField heightField;

    @FXML
    TextField numberOfGrainsField;

    @FXML
    Label sizeLabel;

    @FXML
    Slider speedSlider;

    @FXML
    ChoiceBox<String> neighboursChioceBox;

    private double maxWidth, maxHeight;

    private GraphicsContext graphicsContext;

    private GrowthGrains growthGrains;

    private int grainWidth, grainHeight;

    private boolean isStartOn, isOneDrawnigThread;

    private GrainsTask grainsTask;


    @FXML
    void initialize() {

        setNeighbourChoiceBoxItems();
        neighboursChioceBox.setValue("Moore");
        numberOfGrainsField.setText("50");
        grainWidth = 2;
        grainHeight = 2;
        growthGrains = new GrowthGrains(this);
        maxWidth = 700;
        maxHeight = 700;
        graphicsContext = grainCanvas.getGraphicsContext2D();
        sizeLabel.setVisible(false);

        isStartOn = false;
        isOneDrawnigThread = true;

        //neighboursChioceBox.getSelectionModel().selectedItemProperty().addListener( (v , oldValue , newValue) -> );
    }


    @FXML
    public void setSizeAction() {

        sizeLabel.setVisible(false);

        if (Double.valueOf(widthField.getText()) > maxWidth) {
            grainCanvas.setWidth(maxWidth);
            sizeLabel.setVisible(true);
        } else {
            grainCanvas.setWidth(Double.valueOf(widthField.getText()));
        }
        if (Double.valueOf(heightField.getText()) > maxHeight) {
            grainCanvas.setHeight(maxHeight);
            sizeLabel.setVisible(true);
        } else {
            grainCanvas.setHeight(Double.valueOf(heightField.getText()));
        }
        growthGrains.createGrid();
        growthGrains.clearArray();
    }


    public void drawCanvas() {
        Platform.runLater(() -> {
            clearCanvas();
            for (int k = 0; k < growthGrains.getWidth(); k++) {
                for (int j = 0; j < growthGrains.getHeight(); j++) {
                    if (growthGrains.getGrainState(k, j) == 1) {
                        graphicsContext.setFill(growthGrains.getGrain(k, j).getColor());
                        graphicsContext.fillRect(k * grainWidth,j * grainHeight, grainWidth, grainHeight);
                    }
                }
            }
        });

        String choice = neighboursChioceBox.getValue();
        switch (choice) {
            case "Moore": {
                if (growthGrains.moore())
                    stopAction();
                break;
            }
            case "Von Neuman":{
                if (growthGrains.vonNeuman())
                    stopAction();
                break;
            }
        }

    }

    public void clearCanvas() {
        graphicsContext.clearRect(0, 0, grainCanvas.getWidth(), grainCanvas.getHeight());
    }

    @FXML
    public void oneStepAction() {
        drawCanvas();
    }

    @FXML
    public void setGrainsAction() {
        clearCanvas();
        growthGrains.clearArray();
        growthGrains.randomGrains();
        drawCanvas();
    }

    @FXML
    public void startAction() {
        isStartOn = true;
        if (isOneDrawnigThread) {
            grainsTask = new GrainsTask(this);
            Thread thread = new Thread(grainsTask);
            thread.setDaemon(true);
            thread.start();
            isOneDrawnigThread = false;
        }
        speedSlider.setValue(grainsTask.getSpeed());
        speedSlider.valueProperty().addListener(observable -> grainsTask.setSpeed((int) speedSlider.getValue()));
    }

    @FXML
    public void stopAction() {
        isStartOn = false;
        isOneDrawnigThread = true;
        grainsTask.setStopStatus(true);
    }

    private void setNeighbourChoiceBoxItems() {
        neighboursChioceBox.getItems().addAll("Moore", "Von Neuman");
    }

    public Canvas getGrainCanvas() {
        return grainCanvas;
    }

    public int getNumberOfGrains() {
        return Integer.valueOf(numberOfGrainsField.getText());
    }

    public int getGrainWidth() {
        return grainWidth;
    }

    public int getGrainHeight() {
        return grainHeight;
    }
}

