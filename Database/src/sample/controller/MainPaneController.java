package sample.controller;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class MainPaneController {

    public void initialize() {
    }

    @FXML
    void addAction(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(this.getClass().getResource("/sample/view/AddQuestionPane.fxml"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        stage.setScene(new Scene(root));
        stage.setTitle("Add question");
        stage.show();
    }

    @FXML
    void showAction(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(this.getClass().getResource("/sample/view/ViewPane.fxml"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        stage.setScene(new Scene(root));
        stage.setTitle("Show question");
        stage.show();
    }

    @FXML
    void removeAction(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(this.getClass().getResource("/sample/view/RemovePane.fxml"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        stage.setScene(new Scene(root));
        stage.setTitle("Remove question");
        stage.show();
    }

}