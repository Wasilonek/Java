package Grains.controller;

import Grains.model.GrainsTask;
import Grains.model.GrowthGrains;
import Grains.model.MonteCarloTask;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.io.File;
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

    @FXML
    ScrollPane scrollPane;

    @FXML
    TextField grainSizeTextField;

    @FXML
    TextField numberOfIdTextField;

    private double maxWidth, maxHeight;

    private GraphicsContext graphicsContext;

    private GrowthGrains growthGrains;

    private int grainWidth, grainHeight;

    private boolean isStartOn, isOneDrawnigThread; // zarzadzanie startowaniem i zatrzymywaniem wątku

    private GrainsTask grainsTask;

    private WritableImage writableImage;

    private int numberOfId;

    private Random random;

    private MonteCarloTask monteCarloTask;

    @FXML
    void initialize() {

        random = new Random();

        // maksymalna wielkość dla Canvas
        maxWidth = 1000;
        maxHeight = 1000;

        toMuchGrainsLabel.setVisible(false);

        sizeLabel.setVisible(false);

        grainSizeTextField.setText("5");

        // wielkość ziaren
//        grainWidth = Integer.valueOf(grainSizeTextField.getText());
//        grainHeight = Integer.valueOf(grainSizeTextField.getText());

        setNeighbourChoiceBoxItems();
        neighboursChioceBox.setValue("Heksagonalne Losowe");

        setPlacementChoiceBoxItems();
        placementChoiceBox.setValue("Losowe");

        // zmiana periodyczności na zamkniete
        periodicityCheckBox.setSelected(true);

        // pole do zmiany ilosci ziaren do losowania
        numberOfGrainsField.setText("50");

        // domyslna wartosc promienia
        radiusTextField.setText("30");

        // obiektu z głowna logika programu
        growthGrains = new GrowthGrains(this);

        grainSizeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            growthGrains.createGrid();
        });

        scrollPane.setContent(grainCanvas);

        graphicsContext = grainCanvas.getGraphicsContext2D();

        // Domyslnie wątek rysujący jest wyłacząny
        isStartOn = false;
        isOneDrawnigThread = true;

        numberOfId = 10;

        numberOfIdTextField.setText("2");

    }

    // Dodawanie ziarna do siatki za pomocą myszki
    @FXML
    public void addGrainUsingMouse() {
        //grainWidth = Integer.valueOf(grainSizeTextField.getText());
        String grainW = grainSizeTextField.getText();
        String grainH = grainSizeTextField.getText();
        try {
            if (!grainW.matches("\\d*") || !grainH.matches("\\d*")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Wrong format");
                alert.setContentText("Write only number!");

                alert.showAndWait();
                grainWidth = 700;
            } else {
                grainWidth = Integer.valueOf(grainSizeTextField.getText());
            }
        } catch (Exception ignored) {
        }
        //grainHeight = Integer.valueOf(grainSizeTextField.getText());

        try {
            if (!grainH.matches("\\d*")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Wrong format");
                alert.setContentText("Write only number!");

                alert.showAndWait();
                grainHeight = 700;
            } else {
                grainHeight = Integer.valueOf(grainSizeTextField.getText());

            }
        } catch (Exception ignored) {
        }
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

    @FXML
    public void randAgainAction() {
        int maxNumberOfCheck = 0;
        int x, y;
        for (int i = 0; i < getNumberOfGrains(); ) {
            if (maxNumberOfCheck == 1000)
                break;
            x = random.nextInt(growthGrains.getWidth());
            y = random.nextInt(growthGrains.getHeight());
            if (growthGrains.getGrainState(x, y) == 1) {
                maxNumberOfCheck++;
                continue;
            }
            growthGrains.setGrainAdd(x, y, i);
            maxNumberOfCheck = 0;
            i++;
        }
    }

    // Zmiana rozmiaru Canvasu podczas działania programu
    @FXML
    public void setSizeAction() {

        sizeLabel.setVisible(false);
        String grainW = widthField.getText();
        String grainH = heightField.getText();
        try {
            if (!grainW.matches("\\d*")) {
                wrongFormatAlertMessage();
                grainCanvas.setWidth(maxWidth);
            } else {
                grainCanvas.setWidth(Double.valueOf(widthField.getText()));
            }
        } catch (Exception ignored) {
        }

        try {
            if (!grainH.matches("\\d*")) {
                wrongFormatAlertMessage();
                grainCanvas.setHeight(maxHeight);
            } else {
                grainCanvas.setHeight(Double.valueOf(heightField.getText()));
            }
        } catch (Exception ignored) {
        }

        if (grainCanvas.getWidth() > maxWidth || grainCanvas.getHeight() > maxHeight) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Max size is 1000x1000!");

            alert.showAndWait();
        }
//        if (grainCanvas.getHeight() > maxHeight) {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("Warning Dialog");
//            alert.setContentText("Max size is 1000x1000!");
//
//            alert.showAndWait();
//        }
//
//        if (Double.valueOf(widthField.getText()) > maxWidth) {
//            grainCanvas.setWidth(maxWidth);
//            sizeLabel.setVisible(true);
//        } else {
//            grainCanvas.setWidth(Double.valueOf(widthField.getText()));
//        }
//        if (Double.valueOf(heightField.getText()) > maxHeight) {
//            grainCanvas.setHeight(maxHeight);
//            sizeLabel.setVisible(true);
//        } else {
//            grainCanvas.setHeight(Double.valueOf(heightField.getText()));
//        }

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

    public void wrongFormatAlertMessage() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Wrong format");
        alert.setContentText("Write only number!");
        alert.showAndWait();
    }

    // Metoda do wizualizacji siatki wykonywana w osobnym wątku
    public void drawCanvas() {
        Platform.runLater(() -> {
            String grainS = grainSizeTextField.getText();

            try {
                if (!grainS.matches("\\d*")) {
                    wrongFormatAlertMessage();
                    grainWidth = 2;
                    grainHeight = 2;
                } else {
                    grainWidth = Integer.valueOf(grainSizeTextField.getText());
                    grainHeight = Integer.valueOf(grainSizeTextField.getText());
                }
            } catch (Exception ignored) {
            }

            //clearCanvas();
//            for (int i = 0; i < growthGrains.getWidth(); i++) {
//                for (int j = 0; j < growthGrains.getHeight(); j++) {
//                    if (growthGrains.getGrainState(i, j) == 1) {
//                        graphicsContext.setFill(growthGrains.getGrain(i, j).getColor());
//                        graphicsContext.fillRect(i * grainWidth, j * grainHeight, grainWidth, grainHeight);
//                    }
//                }
//            }

            refreshCanvas();

            // W zalezności od wybranego rodzaju sasiedztwa przeprowadzam rozrost
            String choice = neighboursChioceBox.getValue();
            switch (choice) {
                case "Von Neuman": {
                    if (growthGrains.vonNeuman(periodicityCheckBox.isSelected()))
                        stopAction();
                    break;
                }
                case "Moore": {
                    if (growthGrains.moore(periodicityCheckBox.isSelected()))
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

        String grainS = grainSizeTextField.getText();

        try {
            if (!grainS.matches("\\d*")) {
                wrongFormatAlertMessage();
                grainWidth = 2;
                grainHeight = 2;
            } else {
                grainWidth = Integer.valueOf(grainSizeTextField.getText());
                grainHeight = Integer.valueOf(grainSizeTextField.getText());
            }
        } catch (Exception ignored) {
        }

//        grainWidth = Integer.valueOf(grainSizeTextField.getText());
//        grainHeight = Integer.valueOf(grainSizeTextField.getText());
        clearCanvas();
        growthGrains.clearArray();

        switch (choice) {
            case "Losowe": {
                if (growthGrains.randomGrains()) {
                    toMuchGrainsLabel.setVisible(true);
                } else toMuchGrainsLabel.setVisible(false);
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
        try {
            grainsTask.setStopStatus(true);
        } catch (NullPointerException e) {
            System.out.println("Rozrost nie wystartował");
        }
    }

    public void saveButtonAction() {
        writableImage = new WritableImage((int) grainCanvas.getWidth(), (int) grainCanvas.getHeight());
        grainCanvas.snapshot(null, writableImage);

        File file = new File("CanvasImage.png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
        } catch (Exception s) {
        }
    }

    @FXML
    public void setIdAction() {

        String grainS = grainSizeTextField.getText();

        try {
            if (!grainS.matches("\\d*")) {
                wrongFormatAlertMessage();
                grainWidth = 2;
                grainHeight = 2;
            } else {
                grainWidth = Integer.valueOf(grainSizeTextField.getText());
                grainHeight = Integer.valueOf(grainSizeTextField.getText());
            }
        } catch (Exception ignored) {
        }


        String numberID = numberOfIdTextField.getText();
        try {
            if (!numberID.matches("\\d*")) {
                wrongFormatAlertMessage();
                numberOfId = 10;

            } else {
                numberOfId = Integer.valueOf(numberOfIdTextField.getText());
            }
        } catch (Exception ignored) {
        }

        growthGrains.randomMonteCarloGrains(numberOfId);
        growthGrains.calculateEnergy(periodicityCheckBox.isSelected());
        refreshCanvas();
    }

    @FXML
    public void randOnClearAction() {

    }

    public void nextMonteCarloPeriod() {
        Platform.runLater(() -> {
            growthGrains.monteCarlo(periodicityCheckBox.isSelected(), numberOfId);
            refreshCanvas();
        });
    }

    @FXML
    void monteCarloAction() {

        isStartOn = true;
        if (isOneDrawnigThread) {
            monteCarloTask = new MonteCarloTask(this);
            Thread thread = new Thread(monteCarloTask);
            thread.setDaemon(true);
            thread.start();
            isOneDrawnigThread = false;
        }
    }

    @FXML
    public void StopMonteCarloAction(){
        isStartOn = false;
        isOneDrawnigThread = true;
        try {
            monteCarloTask.setStopStatus(true);
        } catch (NullPointerException e) {
            System.out.println("Rozrost nie wystartował");
        }
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
        String grainN = numberOfGrainsField.getText();
        try {
            if (!grainN.matches("\\d*")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Wrong format");
                alert.setContentText("Write only number!");

                alert.showAndWait();
                return 2;
            } else {
                return Integer.valueOf(numberOfGrainsField.getText());
            }
        } catch (Exception ignored) {
        }
        return 2;
    }
//        return Integer.valueOf(numberOfGrainsField.getText());


    public int getGrainWidth() {
        return grainWidth;
    }

    public int getGrainHeight() {
        return grainHeight;
    }

    public CheckBox getPeriodicityCheckBox() {
        return periodicityCheckBox;
    }

    public TextField getGrainSizeTextField() {
        return grainSizeTextField;
    }
}

