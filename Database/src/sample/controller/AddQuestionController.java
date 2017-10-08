package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.model.Main;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Kamil on 2017-09-17.
 */
public class AddQuestionController {

    @FXML
    private TextField bAnswerTextField;

    @FXML
    private CheckBox bCheckBox;

    @FXML
    private TextField cAnswerTextField;

    @FXML
    private TextField aAnswerTextField;

    @FXML
    private TextField questionTextField;

    @FXML
    private CheckBox aCheckBox;

    @FXML
    private CheckBox dCheckBox;

    @FXML
    private TextField dAnswerTextField;

    @FXML
    private CheckBox cCheckBox;

    @FXML
    private Label informLabel;

    @FXML
    private Button addBtn;


    public void initialize() {
    }


    @FXML
    void backAction(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/view/MainPane.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Datebase");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void addAction(ActionEvent event) {

        String answer = null;
        if (aCheckBox.isSelected())
            answer = "A";
        if (bCheckBox.isSelected())
            answer = "B";
        if (cCheckBox.isSelected())
            answer = "C";
        if (dCheckBox.isSelected())
            answer = "D";

        String query = "INSERT INTO questions VALUES(NULL," +
                "\"" + questionTextField.getText() + "\"," +
                "\"" + aAnswerTextField.getText() + "\"," +
                "\"" + bAnswerTextField.getText() + "\"," +
                "\"" + cAnswerTextField.getText() + "\"," +
                "\"" + dAnswerTextField.getText() + "\"," +
                "\"" + answer + "\"" + ")";


        Statement statement = null;
        try {
            statement = Main.connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }


}
