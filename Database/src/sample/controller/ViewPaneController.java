package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
public class ViewPaneController {


    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private Label bAnswerLabel;

    @FXML
    private Label cAnswerLabel;

    @FXML
    private Label aAnswerLabel;

    @FXML
    private Label dAnswerLabel;

    @FXML
    private Label questionLabel;

    private ObservableList<String> observableList;
    private List<String> questionList;
    private List<ViewQuestionModel> viewQuestionModelList;

    public void initialize() {

        questionList = new ArrayList<>();
        viewQuestionModelList = new ArrayList<>();

        String query = "SELECT * FROM questions ";
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = Main.connection.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                viewQuestionModelList.add(new ViewQuestionModel(rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6)));
                questionList.add(rs.getString(2));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        observableList = FXCollections.observableArrayList(questionList);
        comboBox.getItems().addAll(observableList);
        System.out.println(observableList);
        System.out.println(viewQuestionModelList);


    }

    @FXML
    void showQuestion(ActionEvent event) {
        String question = comboBox.getSelectionModel().getSelectedItem();
        int index = comboBox.getSelectionModel().getSelectedIndex();
        questionLabel.setText(question);
        aAnswerLabel.setText(viewQuestionModelList.get(index).getOdpA());
        bAnswerLabel.setText(viewQuestionModelList.get(index).getOdpB());
        cAnswerLabel.setText(viewQuestionModelList.get(index).getOdpC());
        dAnswerLabel.setText(viewQuestionModelList.get(index).getOdpD());


    }

    @FXML
    void backToMenuAction(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/view/MainPane.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("The best quiz");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }


}
