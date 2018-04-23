package UniversalMethod.Model;

/**
 * Created by Kamil on 2018-04-23.
 */
public class Main {
    public static void main(String[] args) {
        GameRules gameRules = new GameRules();
        gameRules.getBit();
        gameRules.draw();
        for (int i = 0; i < 15; i++) {
            gameRules.generateNextPopulation();
            gameRules.draw();
        }
    }
}
