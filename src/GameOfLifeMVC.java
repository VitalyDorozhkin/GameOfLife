import java.io.*;
import java.net.URISyntaxException;

public class GameOfLifeMVC {

    public static void main(String[] args) throws URISyntaxException {
        GameOfLifePreView preView = new GameOfLifePreView();
        GameOfLifeController controller = new GameOfLifeController(preView);
        preView.setVisible(true);
        //init();


    }


    public static void init(){



        boolean[][][] figures = new boolean[][][]{
                {
                        {true},
                        {true},
                        {true}
                },
                {
                        {true,true,true}
                },
                {
                        {false,true,false},
                        {false,false,true},
                        {true,true,true}
                },
                {
                        {true,true},
                        {true,true}
                },
                {
                        {true,true},
                        {true,true},
                        {true,true},
                        {true,true},
                        {true,true},
                        {true,true},
                        {true,true}
                },
                {
                        {true},
                        {true},
                        {true},
                        {true},
                        {true},
                        {true}
                },
                {
                        {true,true},
                        {true,true},
                        {true,true},
                        {true,true},
                        {true,true},
                        {true,true},
                        {true,true}
                },
                {
                        {true},
                        {true},
                        {true},
                        {true},
                        {true},
                        {true},
                        {true}
                },
                {
                        {true,true,true,true,true,true,true},
                        {true,true,true,true,true,true,true}
                },
                {
                        {true,true,true,true,true,true},
                        {true,true,true,true,true,true}
                },
                {
                        {true,true,true,true,true,true,true}
                },
                {
                        {true,true,true,true,true,true}
                }
        };

        try {
            File dir = new File(new File("").getAbsolutePath() + "/figures");
            if(!dir.exists()){
                dir.mkdir();
            }
            for(int i = 0; i < figures.length; i++){
                FileOutputStream fileOut = new FileOutputStream(dir + File.separator + "figure" + (i + 1) + ".figure");
                ObjectOutputStream outStream = new ObjectOutputStream(fileOut);
                outStream.writeObject(figures[i]);
                outStream.close();
                fileOut.close();
            }

        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
