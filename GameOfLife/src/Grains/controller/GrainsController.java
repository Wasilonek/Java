package Grains.controller;

import Grains.model.GrowthGrains;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.Random;


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

    private double maxWidth , maxHeight;

    private GraphicsContext graphicsContext;

    private GrowthGrains growthGrains;

    private int grainWidth, grainHeight;


    @FXML
    void initialize() {
        grainWidth = 10;
        grainHeight = 10;
        growthGrains = new GrowthGrains(this);
        maxWidth = 600;
        maxHeight = 600;
        graphicsContext = grainCanvas.getGraphicsContext2D();
//        graphicsContext.setFill(Color.GREEN);
//        graphicsContext.fillRect(0, 0, grainCanvas.getWidth(), grainCanvas.getHeight());
        sizeLabel.setVisible(false);
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
        }else{
            grainCanvas.setHeight(Double.valueOf(heightField.getText()));
        }
//
//        graphicsContext.setFill(Color.rgb(234 , 112,231));
//        graphicsContext.fillRect(0, 0, grainCanvas.getWidth(), grainCanvas.getHeight());
//        System.out.println(grainCanvas.getWidth() + " " + grainCanvas.getHeight());
        widthField.clear();
        heightField.clear();
        growthGrains.createGrid();
        growthGrains.clearArray();
        //drawCanvas();
    }


    public void drawCanvas() {
        Random random = new Random();
        Platform.runLater(() -> {
            clearCanvas();
            for (int k = 0; k < growthGrains.getWidth(); k++) {
                for (int j = 0; j < growthGrains.getHeight(); j++) {
                    if (growthGrains.getGrainState(j, k) == 1) {
                        //graphicsContext.setFill(Color.rgb(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                        //graphicsContext.setFill(growthGrains.getColorOfGrain(growthGrains.getGrainId(j,k)));
                        graphicsContext.setFill(growthGrains.getGrain(j,k).getColor());
                        graphicsContext.fillRect(j * grainWidth, k * grainHeight, grainWidth, grainHeight);
                    }
                }
            }
        });
        growthGrains.grainRules();
    }

    public void clearCanvas() {
        graphicsContext.clearRect(0, 0, grainCanvas.getWidth(), grainCanvas.getHeight());
    }

    @FXML
    public void oneStepAction(){
        drawCanvas();
    }

    @FXML
    public void setGrainsAction() {
        growthGrains.clearArray();
        growthGrains.randomGrains();
        drawCanvas();
    }

    public double getCanvasWidth(){
        return grainCanvas.getWidth();
    }
    public double getCanvasHeight(){
        return grainCanvas.getHeight();
    }

    public Canvas getGrainCanvas() {
        return grainCanvas;
    }

    public int getNumberOfGrains(){
        return Integer.valueOf(numberOfGrainsField.getText());
    }

    public int getGrainWidth() {
        return grainWidth;
    }

    public void setGrainWidth(int grainWidth) {
        this.grainWidth = grainWidth;
    }

    public int getGrainHeight() {
        return grainHeight;
    }

    public void setGrainHeight(int grainHeight) {
        this.grainHeight = grainHeight;
    }
}
