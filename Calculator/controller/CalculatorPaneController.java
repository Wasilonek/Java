package BeatifulCalculator.controller;

/**
 * Created by Kamil on 2017-09-06.
 */

import BeatifulCalculator.model.Operation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class CalculatorPaneController {

    @FXML
    TextArea textArea;

    @FXML
    String operator = "";

    private double num2 = 0;
    private double num1 = 0;
    private Operation operat = new Operation();
    private boolean start = true;

    @FXML
    void numberOperation(ActionEvent event) {
        if (start) {
            textArea.setText("");
            start = false;
        }

        String text = ((Button) event.getSource()).getText();
        textArea.appendText(text);
    }

    @FXML
    void operationAction(ActionEvent event) {
        String value = ((Button) event.getSource()).getText();

        if (!"=".equals(value)) {
            if (!operator.isEmpty())
                return;
            operator = value;
            num1 = Double.parseDouble(textArea.getText());
            textArea.clear();
        } else {
            if (operator.isEmpty())
                return;

            textArea.setText((String.valueOf(operat.operation(num1, Double.parseDouble(textArea.getText()), operator))));
            operator = "";
        }
    }

    @FXML
    void clearAction() {
        num1 = 0;
        operator = "";
        start = true;
        textArea.clear();
    }


    @FXML
    void changeSign(){
            if (Double.parseDouble(textArea.getText()) != 0) {
                {
                    if (Double.parseDouble(textArea.getText()) > 0) {
                        Double tmpDouble = Double.parseDouble(textArea.getText());
                        tmpDouble = tmpDouble * (-1);
                        textArea.setText(String.valueOf(tmpDouble));
                    } else {

                    }
                }
            }
    }
}
