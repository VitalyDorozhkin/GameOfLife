import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Random;

public class FieldModel {
    private Field field;
    private int seed;
    private int generationNumber;

    private boolean[][] figure = new boolean[][]{{true}};


    public FieldModel(int seed, int width, int height) {
        this.seed = seed;
        this.generationNumber = 1;
        initField(width, height);
    }

    public FieldModel(int seed, int width, int height, int generationNumber) {
        this(seed, width, height);
        this.generationNumber = generationNumber;
        rollTo(this.generationNumber);
    }

    public FieldModel(String Id) {
        String[] parsed = Id.split(".");

        this.seed = Integer.parseInt(parsed[0]);

        generationNumber = 1;
        initField(Integer.parseInt(parsed[1]), Integer.parseInt(parsed[2]));
        if(parsed.length == 4){
            rollTo(Integer.parseInt(parsed[3]));
        }
    }


    public int getGenerationNumber() {
        return generationNumber;
    }

    public void setGenerationNumber(int generationNumber) {
        this.generationNumber = generationNumber;
    }



    public void reset(){
        initField(field.getWidth(), field.getHeight());
        rollTo(generationNumber);
    }


    private void initField(int width, int height) {
        field = new Field(width, height);
        int currentAlive = 0;
        if(seed == 2){
            currentAlive = initSquares();
        }else{
            currentAlive = initRandom();
        }

        field.setCurrentAlive(currentAlive);
    }

    private int initSquares(){
        int currentAlive = 0;
        for(int i = 0; i < field.getHeight(); i++){
            for(int j = 0; i % 3 != 2 && j < field.getWidth(); j++){
                field.setCell(i, j, j % 3 != 2);
                currentAlive += field.getCell(i, j) ? 1 : 0;
            }
        }
       return currentAlive;
    }

    private int initRandom(){

        Random random = new Random(seed);
        int currentAlive = 0;
        for(int i = 0; i < field.getHeight(); i++){
            for(int j = 0; j < field.getWidth(); j++){
                field.setCell(i, j, (seed == 0 || seed == 1) ? seed == 1 : random.nextBoolean());
                currentAlive += field.getCell(i, j) ? 1 : 0;
            }
        }
        return currentAlive;
    }

    private void rollTo(int generationNumber) {
        while(field.getGenerationNumber() < generationNumber) {
            field.changeGeneration();
        }
    }


    public Field getField() {
        return field;
    }

    public String getId(){
        return seed + "." + field.getWidth() + "." + field.getHeight() + "." + field.getGenerationNumber();
    }


    public void insert(boolean[][] element, int y, int x, boolean killEnvironment){
        for (int i = 0; i < element.length; i++) {
            for (int j = 0; j < element[i].length; j++) {
                if(killEnvironment || element[i][j]){
                    field.setCell(y + i, x + j, element[i][j]);
                }
            }
        }
    }

    public void setFigure(String figureName){
        try {
            FileInputStream fileIn = new FileInputStream("figures" + File.separator + figureName + ".figure");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            figure = (boolean[][]) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Figure class not found");
            c.printStackTrace();
        }
    }

    public void setFigureRotation(int angle){
            boolean [][] rotMat = new boolean[figure[0].length][figure.length];

            for (int rw = 0; rw < figure.length; rw++) {
                for (int cl = 0; cl < figure[0].length; cl++) {
                    rotMat[figure[0].length - 1 - cl][rw] = figure[angle < 0 ? figure.length - rw - 1 : rw][angle < 0 ? figure[0].length - cl - 1 : cl];
                }
            }

            figure = rotMat;
    }

    public void reverseFigure(){
        boolean tmp;
        for(int i = 0; i < figure.length ; i++){
            for(int j = 0;  j < figure[i].length / 2; j++){
                tmp = figure[i][j];
                figure[i][j] = figure[i][figure[i].length - j - 1];
                figure[i][figure[i].length - j - 1] = tmp;
            }
        }
    }

    public boolean[][] getFigure() {
        return figure;
    }

    public boolean[][] getFigureWithBorder(){
        int side = Math.max(figure.length, figure[0].length);
        int height = side + 2;
        int width = side + 2;
        if(figure.length < figure[0].length){
            height -= figure.length % 2 - figure[0].length % 2;
        } else {
            width -= figure[0].length % 2 - figure.length % 2;
        }
        boolean[][] res = new boolean[height][width];
        int y = 0;
        int x = 0;

        for (int i = 0; i < figure.length; i++) {
            for (int j = 0; j < figure[i].length; j++) {
                res[i + (height - figure.length) / 2][j + (width - figure[0].length) / 2] = figure[i][j];
            }
        }

        return res;
    }

    public boolean[][] getCutFigure(){
        boolean[][] newFigure = field.getCurrentGeneration();
        int minX = newFigure[0].length;
        int minY = newFigure.length;
        int maxX = 0;
        int maxY = 0;
        boolean[][] res;
        for (int i = 0; i < newFigure.length; i++) {
            for (int j = 0; j < newFigure[i].length; j++) {
                if(newFigure[i][j]){
                    minY = Math.min(i, minY);
                    minX = Math.min(j, minX);
                    maxY = Math.max(i, maxY);
                    maxX = Math.max(j, maxX);
                }
            }
        }
        res = new boolean[maxY - minY + 1][maxX - minX + 1];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[i].length; j++) {
                res[i][j] = newFigure[i + minY][j + minX];
            }
        }
        return res;
    }





}
