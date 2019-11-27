public class Field {

    private boolean[][] currentGeneration;
    private boolean[][] nextGeneration;
    private int currentAlive;
    private int generationNumber;

    public Field(int width, int height) {
        currentGeneration = new boolean[height][width];
        this.generationNumber = 1;
    }

    public boolean getCell(int y, int x){
        return currentGeneration[Math.floorMod(y, getHeight())][Math.floorMod(x, getWidth())];
    }

    public void setCell(int y, int x, boolean status){
        currentGeneration[Math.floorMod(y, getHeight())][Math.floorMod(x, getWidth())] = status;
    }

    public void changeCell(int y, int x){
        setCell(y, x, !getCell(y, x));
    }//<-

    public int getWidth() {
        return currentGeneration[0].length;
    }

    public int getHeight() {
        return currentGeneration.length;
    }

    public int getCurrentAlive() {
        return currentAlive;
    }

    public void setCurrentAlive(int currentAlive) {
        this.currentAlive = currentAlive;
    }

    public void changeGeneration(){
        currentAlive = setNextGeneration();
        currentGeneration = nextGeneration;
        nextGeneration = null;
        generationNumber++;
    }

    public int getGenerationNumber() {
        return generationNumber;
    }

    private int countNeighbours(int y, int x){
        int count = 0;

        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                if(!(i == 0 && j == 0) && getCell(y + i, x + j)){
                    count++;
                }
            }
        }
        return count;
    }

    private void setNextCell(int y, int x, boolean status){
        nextGeneration[Math.floorMod(y, getHeight())][Math.floorMod(x, getWidth())] = status;
    }

    private boolean getNextStatus(int y, int x){

        int neighbours = countNeighbours(y, x);

        return (neighbours == 2 && getCell(y, x)) || neighbours == 3;
    }

    private int setNextGeneration() {

        boolean nextStatus;
        int nextAlive = 0;
        if (nextGeneration == null){
            nextGeneration = new boolean[getHeight()][getWidth()];
        }
        for(int i = 0; i < nextGeneration.length; i++){
            for(int j = 0; j < nextGeneration[i].length; j++){
                nextStatus = getNextStatus(i, j);
                setNextCell(i, j, nextStatus);
                if(nextStatus)
                    nextAlive++;
            }
        }
        return nextAlive;
    }

    public boolean[][] getCurrentGeneration() {
        return currentGeneration;
    }

}