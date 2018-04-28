package controller;


import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;

import model.DrawerTask;
import model.Game;

import javax.swing.event.ChangeListener;
import java.util.List;


public class MainController {

    @FXML
    Button nextButton;

    @FXML
    Canvas canvas;

    @FXML
    ChoiceBox<String> structureChoiceBox;

    private boolean stopStatus = false;

    private GraphicsContext graphicsContext;

    private Game game;

    private int cellWidth, cellHeight, numberOfSteps;

    private DrawerTask drawerTask;


    public MainController() {
        game = new Game();
        numberOfSteps = 5;
        cellWidth = 10;
        cellHeight = 10;


    }

    @FXML
    void initialize() {
        graphicsContext = canvas.getGraphicsContext2D();

        setStructureChoiceBoxItems();

        //structureChoiceBox.setValue("Kamil");
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

    private void setStructureChoiceBoxItems() {
        structureChoiceBox.setValue("Unchanging structure");
        structureChoiceBox.getItems().addAll("Unchanging structure", "Oscillators");
    }

    private void getStructureChoiceBoxValue() {

//        structureChoiceBox.getSelectionModel()
//                .selectedItemProperty()
//                .addListener( (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
//                    final String choice = newValue;
//                    switch (choice){
//                        case "Unchanging structure":{
//                            game.clearCellArray();
//                            game.setUnchangingStructure();
//                        }
//                    }
//        } );
        String choice = structureChoiceBox.getValue();
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
        }
    }

//    public void unchangingStructureButtonAction() {
//
//    }

//    public void oscillatorsButtonAction() {
//        game.clearCellArray();
//        game.setOscillatorsStructure();
//    }

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
        getStructureChoiceBoxValue();
        draw();
    }

    public void nextFiveStepsButtonAction() {
        for (int i = 0; i < 5; i++) {
            game.gameRules();
        }
        draw();

    }


    public void StopButtonAction() {
        drawerTask.setStopStatus(true);
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
        drawerTask = new DrawerTask(graphicsContext, numberOfSteps, this);
        new Thread(drawerTask).start();
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
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void clearButtonAction() {
        game.clearCellArray();
        clearCanvas();
    }
}
