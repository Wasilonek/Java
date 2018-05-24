package Grains.model;

import Grains.controller.GrainsController;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.util.*;

/**
 * Created by Kamil on 2018-05-23.
 */
public class GrowthGrains {
    private Grain[][] grainsArray;
    private int width, height;
    private Random random;
    private GrainsController grainsController;
    private List<Color> listOfGrains;

    public GrowthGrains(GrainsController grainsController) {

        listOfGrains = new ArrayList<>();
        random = new Random();

        this.grainsController = grainsController;

        createGrid();
    }

    public void createGrid() {
        width = (int) grainsController.getGrainCanvas().getWidth() / grainsController.getGrainWidth();
        height = (int) grainsController.getGrainCanvas().getHeight() / grainsController.getGrainHeight();

        grainsArray = new Grain[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grainsArray[i][j] = new Grain();
                grainsArray[i][j].setState(0);
                grainsArray[i][j].setId(-1);
            }
        }
    }


    public void clearArray() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grainsArray[i][j].setState(0);
                grainsArray[i][j].setNextState(0);
                grainsArray[i][j].setColor(Color.WHITE);
                grainsArray[i][j].setNewColor(Color.WHITE);
                grainsArray[i][j].setNewId(-1);
                grainsArray[i][j].setId(-1);

            }
        }
    }

    public void randomGrains() {
        int x, y;

        clearArray();
        grainsController.clearCanvas();
        for (int i = 0; i < grainsController.getNumberOfGrains(); i++) {
            x = random.nextInt(width);
            y = random.nextInt(height);
            grainsArray[x][y].setState(1);
            grainsArray[x][y].setId(i);
            grainsArray[x][y].setColor(Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble()));
        }
//
//        for (int i = 0; i < width; i++) {
//            for (int j = 0; j < height; j++) {
//                if (grainsArray[i][j].getState() == 1)
//                    System.out.print("#");
//                else System.out.print(".");
//            }
//            System.out.println();
//        }
//        System.out.println();

    }
/////////////////////////////////////////////////////////////////////////////


    private void fillMap(int id, Map<Integer, Integer> grainMap) {
        if (grainMap.containsKey(id)) grainMap.put(id, grainMap.get(id) + 1);
        else grainMap.put(id, 1);
    }

    private int getIDMaxNeighbour(Map<Integer, Integer> grainMap) {
        Map.Entry<Integer, Integer> maxEntry = null;

        for (Map.Entry<Integer, Integer> entry : grainMap.entrySet())
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
                maxEntry = entry;

        int id = 0;
        if (maxEntry != null) {
            int max = maxEntry.getValue();

            List<Integer> listMax = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry : grainMap.entrySet())
                if (entry.getValue() == max)
                    listMax.add(entry.getKey());

            Random rand = new Random();
            int randWinner = rand.nextInt(listMax.size());
            id = listMax.get(randWinner);
        }

        return id;
    }

////////////////////////////////////////////////////////////////////////////

    public Grain[][] grainRules() {

        Map<Integer, Integer> grainMap = new HashMap<>();
        Map<Integer, Color> colorMap = new HashMap<>();
        int id = 0;
        int idToAssign = 0;
        boolean isNeig;

        int indUp;
        int indDown;
        int indLeft;
        int indRight;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                id = -1;
                idToAssign = -1;
                grainMap.clear();
                colorMap.clear();
                if (grainsArray[i][j].getState() == 0) {
                    isNeig = false;
                    indUp = i - 1;
                    indDown = i + 1;
                    indLeft = j - 1;
                    indRight = j + 1;

                    if (i == 0)
                        indUp = width - 1;
                    if (i == (width - 1))
                        indDown = 0;
                    if (j == 0)
                        indLeft = height - 1;
                    if (j == (height - 1))
                        indRight = 0;

                    if (grainsArray[indUp][indLeft].getState() == 1) {
                        id = grainsArray[indUp][indLeft].getId();
                        fillMap(id, grainMap);
                        isNeig = true;
                        colorMap.put(id, grainsArray[indUp][indLeft].getColor());
                    }
                    if (grainsArray[indUp][j].getState() == 1) {
                        id = grainsArray[indUp][j].getId();
                        fillMap(id, grainMap);
                        isNeig = true;
                        colorMap.put(id, grainsArray[indUp][j].getColor());
                    }
                    if (grainsArray[indUp][indRight].getState() == 1) {
                        id = grainsArray[indUp][indRight].getId();
                        fillMap(id, grainMap);
                        isNeig = true;
                        colorMap.put(id, grainsArray[indUp][indRight].getColor());
                    }
                    if (grainsArray[i][indLeft].getState() == 1) {
                        id = grainsArray[i][indLeft].getId();
                        fillMap(id, grainMap);
                        isNeig = true;
                        colorMap.put(id, grainsArray[i][indLeft].getColor());
                    }
                    if (grainsArray[i][indRight].getState() == 1) {
                        id = grainsArray[i][indRight].getId();
                        fillMap(id, grainMap);
                        isNeig = true;
                        colorMap.put(id, grainsArray[i][indRight].getColor());
                    }
                    if (grainsArray[indDown][indLeft].getState() == 1) {
                        id = grainsArray[indDown][indLeft].getId();
                        fillMap(id, grainMap);
                        isNeig = true;
                        colorMap.put(id, grainsArray[indDown][indLeft].getColor());
                    }
                    if (grainsArray[indDown][j].getState() == 1) {
                        id = grainsArray[indDown][j].getId();
                        fillMap(id, grainMap);
                        isNeig = true;
                        colorMap.put(id, grainsArray[indDown][j].getColor());
                    }
                    if (grainsArray[indDown][indRight].getState() == 1) {
                        id = grainsArray[indDown][indRight].getId();
                        fillMap(id, grainMap);
                        isNeig = true;
                        colorMap.put(id, grainsArray[indDown][indRight].getColor());
                    }

                    if (isNeig) {
                        idToAssign = getIDMaxNeighbour(grainMap);
                        grainsArray[i][j].setNextState(1);
                        grainsArray[i][j].setNewId(idToAssign);
                        grainsArray[i][j].setNewColor(colorMap.get(idToAssign));
                    }
                }
            }
        }


        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grainsArray[i][j].getState() == 0) {
                    grainsArray[i][j].setState(grainsArray[i][j].getNextState());
                    grainsArray[i][j].setId(grainsArray[i][j].getNewId());
                    grainsArray[i][j].setColor(grainsArray[i][j].getNewColor());
                    //grainsArray[i][j].setColor(Color.BLACK);

                }
            }
        }

//        for (int i = 0; i < width; i++) {
//            for (int j = 0; j < height; j++) {
//                if (grainsArray[i][j].getState() == 1)
//                    System.out.print("#");
//                else System.out.print(".");
//            }
//            System.out.println();
//        }

        return grainsArray;
    }

    public Grain[][] getGrainsArray() {
        return grainsArray;
    }

    public void setGrainsArray(Grain[][] grainsArray) {
        this.grainsArray = grainsArray;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public GrainsController getGrainsController() {
        return grainsController;
    }

    public void setGrainsController(GrainsController grainsController) {
        this.grainsController = grainsController;
    }
//
//    public Grain getGrainWithId(int id) {
//        for (int i = 0; i < width; i++) {
//            for (int j = 0; j < height; j++) {
//                if (grainsArray[i][j].getId() == id)
//                    return grainsArray[i][j];
//            }
//        }
//        return null;
//    }

    public int getGrainState(int i, int j) {
        return grainsArray[i][j].getState();
    }

    public int getGrainId(int i, int j) {
        return grainsArray[i][j].getId();
    }

    public Grain getGrain(int i, int j) {
        return grainsArray[i][j];
    }

    public List<Color> getListOfGrains() {
        return listOfGrains;
    }

    public Color getColorOfGrain(int index) {
        return listOfGrains.get(index);
    }

    public void setListOfGrains(List<Color> listOfGrains) {
        this.listOfGrains = listOfGrains;
    }
}
