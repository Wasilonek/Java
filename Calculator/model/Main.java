package BeatifulCalculator.model;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.applet.Applet;

/**
 * Created by Kamil on 2017-09-06.
 */
public class Main extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/BeatifulCalculator/view/CalculatorPane.fxml"));
        AnchorPane anchorPane = loader.load();

        primaryStage.setScene(new Scene(anchorPane));
        primaryStage.setTitle("Beatiful Calculator");

        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
