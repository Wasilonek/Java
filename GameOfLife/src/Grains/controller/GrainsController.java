package Grains.controller;

import Grains.model.GrainsTask;
import Grains.model.GrowthGrains;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;


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
    Label toMuchGrainsLabel;

    @FXML
    Label sizeLabel; // Wyswietlanie błedu o zbyt dużym Canvasie

    @FXML
    Slider speedSlider; // Ustawienie szybkości rozrostu

    @FXML
    ChoiceBox<String> neighboursChioceBox; // Zmiana sąsiedztwa

    @FXML
    ChoiceBox<String> placementChoiceBox; // Zmiana rozmieszczenia ziaren

    @FXML
    CheckBox periodicityCheckBox;

    @FXML
    TextField radiusTextField;

    private double maxWidth, maxHeight;

    private GraphicsContext graphicsContext;

    private GrowthGrains growthGrains;

    private int grainWidth, grainHeight;

    private boolean isStartOn, isOneDrawnigThread; // zarzadzanie startowaniem i zatrzymywaniem wątku

    private GrainsTask grainsTask;


    @FXML
    void initialize() {

        // maksymalna wielkość dla Canvas
        maxWidth = 700;
        maxHeight = 700;

        toMuchGrainsLabel.setVisible(false);

        sizeLabel.setVisible(false);

        // wielkość ziaren
        grainWidth = 2;
        grainHeight = 2;

        setNeighbourChoiceBoxItems();
        neighboursChioceBox.setValue("Heksagonalne Lewe");

        setPlacementChoiceBoxItems();
        placementChoiceBox.setValue("Losowe");

        // zmiana periodyczności na zamkniete
        periodicityCheckBox.setSelected(false);

        // pole do zmiany ilosci ziaren do losowania
        numberOfGrainsField.setText("50");

        // domyslna wartosc promienia
        radiusTextField.setText("30");

        // obiektu z głowna logika programu
        growthGrains = new GrowthGrains(this);


        graphicsContext = grainCanvas.getGraphicsContext2D();

        // Domyslnie wątek rysujący jest wyłacząny
        isStartOn = false;
        isOneDrawnigThread = true;
    }

    // Dodawanie ziarna do siatki za pomocą myszki
    @FXML
    public void addGrainUsingMouse() {
        grainCanvas.setOnMousePressed(me -> {
            int x = (int) me.getX();
            int y = (int) me.getY();
            int canvasX = (x / grainHeight) * grainHeight;
            int canvasY = (y / grainWidth) * grainWidth;

            int gridX = canvasX / grainHeight;
            int gridY = canvasY / grainWidth;

            //System.out.println(x + " , " + y);

            growthGrains.addSingleGrain(gridX, gridY);
            refreshCanvas();
        });
    }

    // Zmiana rozmiaru Canvasu podczas działania programu
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

        // przebudowanie siatki  po zmianie wielkości
        growthGrains.createGrid();
        growthGrains.clearArray();
    }

    public void refreshCanvas() {
        clearCanvas();
        for (int i = 0; i < growthGrains.getWidth(); i++) {
            for (int j = 0; j < growthGrains.getHeight(); j++) {
                if (growthGrains.getGrainState(i, j) == 1) {
                    graphicsContext.setFill(growthGrains.getGrain(i, j).getColor());
                    graphicsContext.fillRect(i * grainWidth, j * grainHeight, grainWidth, grainHeight);
                }
            }
        }
    }

    // Metoda do wizualizacji siatki wykonywana w osobnym wątku
    public void drawCanvas() {
        Platform.runLater(() -> {
            clearCanvas();
            for (int i = 0; i < growthGrains.getWidth(); i++) {
                for (int j = 0; j < growthGrains.getHeight(); j++) {
                    if (growthGrains.getGrainState(i, j) == 1) {
                        graphicsContext.setFill(growthGrains.getGrain(i, j).getColor());
                        graphicsContext.fillRect(i * grainWidth, j * grainHeight, grainWidth, grainHeight);
                    }
                }
            }

            // W zalezności od wybranego rodzaju sasiedztwa przeprowadzam rozrost
            String choice = neighboursChioceBox.getValue();
            switch (choice) {
                case "Moore": {
                    if (growthGrains.moore(periodicityCheckBox.isSelected()))
                        stopAction();
                    break;
                }
                case "Von Neuman": {
                    if (growthGrains.vonNeuman(periodicityCheckBox.isSelected()))
                        stopAction();
                    break;
                }
                case "Heksagonalne Lewe": {
                    if (growthGrains.heksagonalLeft(periodicityCheckBox.isSelected()))
                        stopAction();
                    break;
                }
                case "Heksagonalne Prawe": {
                    if (growthGrains.heksagonalRight(periodicityCheckBox.isSelected()))
                        stopAction();
                    break;
                }
                case "Heksagonalne Losowe": {
                    if (growthGrains.heksagonalRand(periodicityCheckBox.isSelected()))
                        stopAction();
                    break;
                }
                case "Pentagonalne Górne": {
                    if (growthGrains.pentagonalTop(periodicityCheckBox.isSelected()))
                        stopAction();
                    break;
                }
                case "Pentagonalne Dolne": {
                    if (growthGrains.pentagonalDown(periodicityCheckBox.isSelected()))
                        stopAction();
                    break;
                }
                case "Pentagonalne Prawe": {
                    if (growthGrains.pentagonalRight(periodicityCheckBox.isSelected()))
                        stopAction();
                    break;
                }
                case "Pentagonalne Lewe": {
                    if (growthGrains.pentagonalLeft(periodicityCheckBox.isSelected()))
                        stopAction();
                    break;
                }
                case "Pentagonalne Losowe": {
                    if (growthGrains.pentagonalRandom(periodicityCheckBox.isSelected()))
                        stopAction();
                    break;
                }
            }
        });
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
        String choice = placementChoiceBox.getValue();

        clearCanvas();
        growthGrains.clearArray();

        switch (choice) {
            case "Losowe": {
                if (growthGrains.randomGrains()) {
                    toMuchGrainsLabel.setVisible(true);
                }
                break;
            }
            case "Rownomierne": {
                growthGrains.createEventlyGrains();
                break;
            }
            case "Z promieniem": {
                growthGrains.createGridWithRadius(Integer.parseInt(radiusTextField.getText()));
                break;
            }
        }
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
        neighboursChioceBox.getItems().addAll("Moore", "Von Neuman", "Heksagonalne Lewe", "Heksagonalne Prawe",
                "Heksagonalne Losowe", "Pentagonalne Górne", "Pentagonalne Dolne", "Pentagonalne Prawe", "Pentagonalne Lewe",
                "Pentagonalne Losowe");
    }

    private void setPlacementChoiceBoxItems() {
        placementChoiceBox.getItems().addAll("Losowe", "Rownomierne", "Z promieniem");
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

    public CheckBox getPeriodicityCheckBox() {
        return periodicityCheckBox;
    }
}

