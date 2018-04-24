package UniversalMethod.Model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Created by Kamil on 2018-04-23.
 */
public class Main extends Application {
    public static void main(String[] args) {

        launch(args);

//        GameRules gameRules = new GameRules();
//        gameRules.getBit();
//        gameRules.draw();
//        for (int i = 0; i < 50; i++) {
//            gameRules.generateNextPopulation();
//            gameRules.draw();
//        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(this.getClass().getResource("/UniversalMethod/View/MainView.fxml"));
        Pane pane = fxmlLoader.load();
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Life Game");
        primaryStage.show();
        primaryStage.setResizable(false);
    }
}
