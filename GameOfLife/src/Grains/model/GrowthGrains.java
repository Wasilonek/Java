package Grains.model;

import Grains.controller.GrainsController;

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
    private int nextMouseId; // kolejne Id do przypisania po kliknieciu myszką

    boolean isArrayFull = true;
    int id = 0;
    int idToAssign = 0;
    boolean isNeig;
    int hasGrainHasNeigbours;

    int indUp;
    int indDown;
    int indLeft;
    int indRight;

    Map<Integer, Color> colorMap;
    Map<Integer, Integer> grainMap;

    public GrowthGrains(GrainsController grainsController) {


        listOfGrains = new ArrayList<>();
        random = new Random();

        this.grainsController = grainsController;

        createGrid();

        nextMouseId = grainsController.getNumberOfGrains();

        grainMap = new HashMap<>();
        colorMap = new HashMap<>();
        id = 0;
        idToAssign = 0;

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

    public boolean randomGrains() {
        int x, y;

        int maxGrainsNumber = width * height;
        if (grainsController.getNumberOfGrains() > maxGrainsNumber)
            return true;


        clearArray();
        grainsController.clearCanvas();
        for (int i = 0; i < grainsController.getNumberOfGrains(); ) {
            x = random.nextInt(width);
            y = random.nextInt(height);
            if (grainsArray[x][y].getState() == 1) {
                continue;
            }
            grainsArray[x][y].setState(1);
            grainsArray[x][y].setId(i);
            grainsArray[x][y].setColor(Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble()));
            i++;
        }
        return false;
    }

    public void addSingleGrain(int x, int y) {


        if (grainsArray[x][y].getState() == 0) {

            grainsArray[x][y].setState(1);
            grainsArray[x][y].setId(nextMouseId + 1);
            grainsArray[x][y].setColor(Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble()));
            nextMouseId++;
        } else if (grainsArray[x][y].getState() == 1) {
            grainsArray[x][y].setState(0);
            grainsArray[x][y].setId(-1);
            nextMouseId--;
        }
    }


    public void createEventlyGrains() {
        int x, y;
        int srodek_x = this.width / 2;
        int srodek_y = this.height / 2;
        double px, py;
        py = Math.sqrt(this.height * grainsController.getNumberOfGrains() / this.width);
        px = ((double) grainsController.getNumberOfGrains() / py);
        int x_size = (int) px;
        int y_size = (int) py;

        int x_width = this.width / x_size;
        int y_height = this.height / y_size;

        srodek_x = x_size / 2;
        srodek_y = y_size / 2;

        clearArray();
        grainsController.clearCanvas();
        for (int i = 0; i < x_size; i++) {
            for (int j = 0; j < y_size; j++) {
                int t_x = (i * 2 + 1) * x_width / 2, t_y = (j * 2 + 1) * y_height / 2;
                grainsArray[t_x][t_y].setState(1);
                grainsArray[t_x][t_y].setId(i);
                grainsArray[t_x][t_y].setColor(Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble()));
            }
        }
    }


    public void createGridWithRadius(int radius) {
        List<Point> wsio = new ArrayList<>();
        for (int i = 0; i < this.width; i++)
            for (int j = 0; j < this.height; j++)
                wsio.add(new Point(i, j));

        for (int i = 0; i < grainsController.getNumberOfGrains(); i++) {//i
            if (wsio.size() < 1) {
                System.out.println("wyczerpano zasob punktow!");
                return;
            }
            Point p = wsio.get(random.nextInt(wsio.size()));

            grainsArray[p.x][p.y].setState(1);
            grainsArray[p.x][p.y].setId(i);
            grainsArray[p.x][p.y].setColor(Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble()));

            Iterator<Point> wsio_i = wsio.iterator();
            while (wsio_i.hasNext()) {//next
                Point v = wsio_i.next();
                if (p.inR(v, radius))
                    wsio_i.remove();
            }//next
        }//i
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

    // Periodyczność / Zamkniete ustawia indeksy krawedzi
    public void setEdge(boolean edge, int i, int j) {

        isArrayFull = false;
        isNeig = false;
        indUp = i - 1;
        indDown = i + 1;
        indLeft = j - 1;
        indRight = j + 1;

        if (edge) {
            if (i == 0)
                indUp = width - 1;
            if (i == (width - 1))
                indDown = 0;
            if (j == 0)
                indLeft = height - 1;
            if (j == (height - 1))
                indRight = 0;
        } else {
            if (i == 0)
                indUp = -1;
            if (i == (width - 1))
                indDown = -1;
            if (j == 0)
                indLeft = -1;
            if (j == (height - 1))
                indRight = -1;
        }
    }

    // zamiana stanów z kolejnej epoki
    public void copyArray() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grainsArray[i][j].getState() == 0) {
                    grainsArray[i][j].setState(grainsArray[i][j].getNextState());
                }
            }
        }
    }


    public boolean checkLeftUpperNeigbour(int indUp, int indLeft) {
        if (!(indUp == -1) || !(indLeft == -1)) {
            if (grainsArray[indUp][indLeft].getState() == 1) {
                id = grainsArray[indUp][indLeft].getId();
                fillMap(id, grainMap);
                colorMap.put(id, grainsArray[indUp][indLeft].getColor());
                return true;
            }
        }
        return false;
    }

    public boolean checkMiddleUpperNeigbour(int indUp, int j) {
        if (!(indUp == -1)) {
            if (grainsArray[indUp][j].getState() == 1) {
                id = grainsArray[indUp][j].getId();
                fillMap(id, grainMap);
                colorMap.put(id, grainsArray[indUp][j].getColor());
                return true;
            }
        }
        return false;
    }

    public boolean checkRightUpperNeigbour(int indUp, int indRight) {
        if (!(indUp == -1) || !(indRight == -1)) {
            if (grainsArray[indUp][indRight].getState() == 1) {
                id = grainsArray[indUp][indRight].getId();
                fillMap(id, grainMap);
                colorMap.put(id, grainsArray[indUp][indRight].getColor());
                return true;
            }
        }
        return false;
    }

    public boolean checkLeftNeigbour(int i, int indLeft) {
        if (!(indLeft == -1)) {
            if (grainsArray[i][indLeft].getState() == 1) {
                id = grainsArray[i][indLeft].getId();
                fillMap(id, grainMap);
                colorMap.put(id, grainsArray[i][indLeft].getColor());
                return true;
            }
        }
        return false;
    }

    public boolean checkRightNeigbour(int i, int indRight) {
        if (!(indRight == -1)) {
            if (grainsArray[i][indRight].getState() == 1) {
                id = grainsArray[i][indRight].getId();
                fillMap(id, grainMap);
                colorMap.put(id, grainsArray[i][indRight].getColor());
                return true;
            }
        }
        return false;
    }

    public boolean checkLeftBottomNeigbour(int indDown, int indLeft) {
        if (!(indDown == -1) || !(indLeft == -1)) {
            if (grainsArray[indDown][indLeft].getState() == 1) {
                id = grainsArray[indDown][indLeft].getId();
                fillMap(id, grainMap);
                colorMap.put(id, grainsArray[indDown][indLeft].getColor());
                return true;
            }
        }
        return false;
    }

    public boolean checkMiddleBottomNeigbour(int indDown, int j) {
        if (!(indDown == -1)) {
            if (grainsArray[indDown][j].getState() == 1) {
                id = grainsArray[indDown][j].getId();
                fillMap(id, grainMap);
                colorMap.put(id, grainsArray[indDown][j].getColor());
                return true;
            }
        }
        return false;

    }

    public boolean checkRightBottomNeigbour(int indDown, int indRight) {
        if (!(indRight == -1) || !(indDown == -1)) {
            if (grainsArray[indDown][indRight].getState() == 1) {
                id = grainsArray[indDown][indRight].getId();
                fillMap(id, grainMap);
                colorMap.put(id, grainsArray[indDown][indRight].getColor());
                return true;
            }
        }
        return false;
    }

    public boolean vonNeuman(boolean isClose) {
        isArrayFull = true;
        id = 0;
        idToAssign = 0;
        hasGrainHasNeigbours = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grainMap.clear();
                colorMap.clear();
                hasGrainHasNeigbours = 0;
                if (grainsArray[i][j].getState() == 0) {

                    setEdge(isClose, i, j);


                    if (checkLeftUpperNeigbour(indUp, indLeft)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkMiddleUpperNeigbour(indUp, j)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkRightUpperNeigbour(indUp, indRight)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkLeftNeigbour(i, indLeft)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkRightNeigbour(i, indRight)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkLeftBottomNeigbour(indDown, indLeft)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkMiddleBottomNeigbour(indDown, j)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkRightBottomNeigbour(indDown, indRight)) {
                        hasGrainHasNeigbours++;
                    }

                    if (hasGrainHasNeigbours > 0) {
                        idToAssign = getIDMaxNeighbour(grainMap);
                        grainsArray[i][j].setNextState(1);
                        grainsArray[i][j].setColor(colorMap.get(idToAssign));
                        grainsArray[i][j].setId(idToAssign);
                    }
                }
            }

        }

        copyArray();

        return isArrayFull;
    }

    public boolean moore(boolean isClose) {

        isArrayFull = true;
        id = 0;
        idToAssign = 0;
        hasGrainHasNeigbours = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grainMap.clear();
                colorMap.clear();
                hasGrainHasNeigbours = 0;
                if (grainsArray[i][j].getState() == 0) {

                    setEdge(isClose, i, j);

                    if (checkMiddleUpperNeigbour(indUp, j))
                        hasGrainHasNeigbours++;

                    if (checkLeftNeigbour(i, indLeft))
                        hasGrainHasNeigbours++;

                    if (checkRightNeigbour(i, indRight))
                        hasGrainHasNeigbours++;

                    if (checkMiddleBottomNeigbour(indDown, j))
                        hasGrainHasNeigbours++;

                    if (hasGrainHasNeigbours > 0) {
                        idToAssign = getIDMaxNeighbour(grainMap);
                        grainsArray[i][j].setNextState(1);
                        grainsArray[i][j].setColor(colorMap.get(idToAssign));
                        grainsArray[i][j].setId(idToAssign);
                    }
                }
            }
        }

        copyArray();

        return isArrayFull;

    }

    public boolean heksagonalLeft(boolean isClose) {

        isArrayFull = true;
        id = 0;
        idToAssign = 0;
        hasGrainHasNeigbours = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grainMap.clear();
                colorMap.clear();
                hasGrainHasNeigbours = 0;
                if (grainsArray[i][j].getState() == 0) {

                    setEdge(isClose, i, j);


                    if (checkLeftUpperNeigbour(indUp, indLeft)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkMiddleUpperNeigbour(indUp, j)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkLeftNeigbour(i, indLeft)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkRightNeigbour(i, indRight)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkMiddleBottomNeigbour(indDown, j)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkRightBottomNeigbour(indDown, indRight)) {
                        hasGrainHasNeigbours++;
                    }

                    if (hasGrainHasNeigbours > 0) {
                        idToAssign = getIDMaxNeighbour(grainMap);
                        grainsArray[i][j].setNextState(1);
                        grainsArray[i][j].setColor(colorMap.get(idToAssign));
                        grainsArray[i][j].setId(idToAssign);
                    }
                }
            }

        }
        copyArray();

        return isArrayFull;
    }

    public boolean heksagonalRight(boolean isClose) {
        isArrayFull = true;
        id = 0;
        idToAssign = 0;
        hasGrainHasNeigbours = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grainMap.clear();
                colorMap.clear();
                hasGrainHasNeigbours = 0;
                if (grainsArray[i][j].getState() == 0) {

                    setEdge(isClose, i, j);


                    if (checkMiddleUpperNeigbour(indUp, j)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkRightUpperNeigbour(indUp, indRight)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkLeftNeigbour(i, indLeft)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkRightNeigbour(i, indRight)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkLeftBottomNeigbour(indDown, indLeft)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkMiddleBottomNeigbour(indDown, j)) {
                        hasGrainHasNeigbours++;
                    }

                    if (hasGrainHasNeigbours > 0) {
                        idToAssign = getIDMaxNeighbour(grainMap);
                        grainsArray[i][j].setNextState(1);
                        grainsArray[i][j].setColor(colorMap.get(idToAssign));
                        grainsArray[i][j].setId(idToAssign);
                    }
                }
            }

        }
        copyArray();
        return isArrayFull;
    }

    public boolean heksagonalRand(boolean isClose) {
        int choice = random.nextInt(2);
        if (choice == 0) {
            return heksagonalLeft(isClose);
        } else {
            return heksagonalRight(isClose);
        }
    }

    // Ustawienie jak dla lewego
    public boolean pentagonalTop(boolean isClose) {
        isArrayFull = true;
        id = 0;
        idToAssign = 0;
        hasGrainHasNeigbours = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grainMap.clear();
                colorMap.clear();
                hasGrainHasNeigbours = 0;
                if (grainsArray[i][j].getState() == 0) {
                    setEdge(isClose, i, j);


                    if (checkMiddleUpperNeigbour(indUp, j)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkRightUpperNeigbour(indUp, indRight)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkRightNeigbour(i, indRight)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkMiddleBottomNeigbour(indDown, j)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkRightBottomNeigbour(indDown, indRight)) {
                        hasGrainHasNeigbours++;
                    }
                    if (hasGrainHasNeigbours > 0) {
                        idToAssign = getIDMaxNeighbour(grainMap);
                        grainsArray[i][j].setNextState(1);
                        grainsArray[i][j].setColor(colorMap.get(idToAssign));
                        grainsArray[i][j].setId(idToAssign);
                    }


                }
            }
        }
        copyArray();
        return isArrayFull;
    }

    // Ustawienie jak dla prawego
    public boolean pentagonalDown(boolean isClose) {
        isArrayFull = true;
        id = 0;
        idToAssign = 0;
        hasGrainHasNeigbours = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grainMap.clear();
                colorMap.clear();
                hasGrainHasNeigbours = 0;
                if (grainsArray[i][j].getState() == 0) {
                    setEdge(isClose, i, j);

                    if (checkLeftUpperNeigbour(indUp, indLeft)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkMiddleUpperNeigbour(indUp, j)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkLeftNeigbour(i, indLeft)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkLeftBottomNeigbour(indDown, indLeft)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkMiddleBottomNeigbour(indDown, j)) {
                        hasGrainHasNeigbours++;
                    }

                    if (hasGrainHasNeigbours > 0) {
                        idToAssign = getIDMaxNeighbour(grainMap);
                        grainsArray[i][j].setNextState(1);
                        grainsArray[i][j].setColor(colorMap.get(idToAssign));
                        grainsArray[i][j].setId(idToAssign);
                    }
                }
            }

        }
        copyArray();
        return isArrayFull;
    }

    // Ustawienie jak dla górnego
    public boolean pentagonalLeft(boolean isClose) {
        isArrayFull = true;
        id = 0;
        idToAssign = 0;
        hasGrainHasNeigbours = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grainMap.clear();
                colorMap.clear();
                hasGrainHasNeigbours = 0;
                if (grainsArray[i][j].getState() == 0) {
                    setEdge(isClose, i, j);


                    if (checkLeftNeigbour(i, indLeft)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkRightNeigbour(i, indRight)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkLeftBottomNeigbour(indDown, indLeft)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkMiddleBottomNeigbour(indDown, j)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkRightBottomNeigbour(indDown, indRight)) {
                        hasGrainHasNeigbours++;
                    }

                    if (hasGrainHasNeigbours > 0) {
                        idToAssign = getIDMaxNeighbour(grainMap);
                        grainsArray[i][j].setNextState(1);
                        grainsArray[i][j].setColor(colorMap.get(idToAssign));
                        grainsArray[i][j].setId(idToAssign);
                    }
                }
            }

        }
        copyArray();
        return isArrayFull;
    }

    // Ustawienie jak dla dolnego
    public boolean pentagonalRight(boolean isClose) {
        isArrayFull = true;
        id = 0;
        idToAssign = 0;
        hasGrainHasNeigbours = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grainMap.clear();
                colorMap.clear();
                hasGrainHasNeigbours = 0;
                if (grainsArray[i][j].getState() == 0) {

                    setEdge(isClose, i, j);


                    if (checkLeftUpperNeigbour(indUp, indLeft)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkMiddleUpperNeigbour(indUp, j)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkRightUpperNeigbour(indUp, indRight)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkLeftNeigbour(i, indLeft)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkRightNeigbour(i, indRight)) {
                        hasGrainHasNeigbours++;
                    }

                    if (hasGrainHasNeigbours > 0) {
                        idToAssign = getIDMaxNeighbour(grainMap);
                        grainsArray[i][j].setNextState(1);
                        grainsArray[i][j].setColor(colorMap.get(idToAssign));
                        grainsArray[i][j].setId(idToAssign);
                    }
                }
            }
        }
        copyArray();
        return isArrayFull;
    }

    public boolean pentagonalRandom(boolean isClose) {
        int choice = random.nextInt(4);

        boolean isFull = false;

        switch (choice) {
            case 1: {
                isFull = pentagonalTop(isClose);
                break;
            }
            case 2: {
                isFull = pentagonalRight(isClose);
                break;
            }
            case 3: {
                isFull = pentagonalLeft(isClose);
                break;
            }
            case 4: {
                isFull = pentagonalDown(isClose);
                break;
            }
        }
        return isFull;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getGrainState(int i, int j) {
        return grainsArray[i][j].getState();
    }

    public Grain getGrain(int i, int j) {
        return grainsArray[i][j];
    }
}

