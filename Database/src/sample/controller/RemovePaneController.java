package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.model.Main;
import sample.model.ViewQuestionModel;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kamil on 2017-09-17.
 */
public class RemovePaneController {


    @FXML
    private ComboBox<ViewQuestionModel> comboBox;


    @FXML
    private TextField IdTextField;

    private List<ViewQuestionModel> viewQuestionModelList;
    private List<String> list;


    public void initialize() {
        list = new ArrayList<>();
        viewQuestionModelList = new ArrayList<>();
        String query = "SELECT * FROM questions ";
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = Main.connection.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                viewQuestionModelList.add(new ViewQuestionModel(rs.getInt(1), rs.getString(2)));
                list.add(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        System.out.println(viewQuestionModelList);
        comboBox.getItems().addAll(viewQuestionModelList);
    }

    @FXML
    void removeAction(ActionEvent event) {
        String startNumber = IdTextField.getText();
        String query = "DELETE FROM questions WHERE ID = " + startNumber;
        Statement statement = null;
        try {
            statement = Main.connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
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
        stage.setTitle("Database");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
}
