/**
 * Created by Kamil on 2018-04-19.
 */
public class Main {
    public static void main(String[] args) {
        int choice = 250;
        switch (choice) {
            case 90:
                NinetyCellRule ninetyCellRule = new NinetyCellRule();
                ninetyCellRule.play();
            case 30: {
                ThirtyCellRule thirtyCellRule = new ThirtyCellRule();
                thirtyCellRule.play();
            }
            case 250: {
                TwoHundredFiftyCellRule twoHundredFiftyCellRule = new TwoHundredFiftyCellRule();
                twoHundredFiftyCellRule.play();
            }
        }
    }
}
