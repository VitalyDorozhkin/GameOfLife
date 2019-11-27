import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameOfLifeController {
    private GameOfLifePreView preView;
    private addFigureView addFigureView;
    private GameOfLifeMainView mainView;
    private FieldModel theModel;
    private AtomicBoolean isPaused;
    private AtomicBoolean drawLines;
    private int timeout;
    private Thread mainThread;


    public GameOfLifeController(GameOfLifePreView preView) {
        this.preView = preView;

        this.preView.addBtnRunListener(new BtnRunListener());
        this.preView.addBtnNewFigureListener(new BtnNewFigureListener());

    }

    class BtnRunListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {

            try{
                int width = preView.getWidthValue();
                int height = preView.getHeightValue();
                int seed = preView.getSeedValue();
                int generationNumber = preView.getGenerationNumberValue();


                isPaused = new AtomicBoolean(true);
                drawLines = new AtomicBoolean(true);

                theModel = new FieldModel(seed, width, height, generationNumber);
                mainView = new GameOfLifeMainView(theModel.getField());

                mainView.addPauseListener(new PauseListener());
                mainView.addLinesListener(new LinesListener());
                mainView.addSliderListener(new SliderListener());
                mainView.addRestartListener(new RestartListener());
                mainView.addStepListener(new StepListener());
                mainView.addBackListener(new BackListener());
                mainView.addGraphicsPanelListener(new GraphicsPanelListener());
                mainView.addWindowListener(new MainWindowsListener());
                mainView.addRotateListener(new RotateListener());
                mainView.addReverseListener(new ReverseListener());
                mainView.addInsertListener(new InsertListener());


                File folder = new File("./figures");
                String[] files = folder.list((folder1, name) -> name.endsWith(".figure"));
                if(files != null){
                    for ( String fileName : files ) {
                        mainView.addFigure(fileName, new RadioListener());
                    }
                }

                preView.setVisible(false);
                mainView.setVisible(true);
                timeout = 205;
                mainThread = new Thread(() -> {
                    try{
                        while(!Thread.interrupted()){
                            if(!isPaused.get()){
                                Thread.sleep(timeout);
                                if(!isPaused.get()) {
                                    theModel.getField().changeGeneration();
                                    mainView.getGraphicsPanel().update(theModel.getField().getCurrentGeneration(), drawLines.get());
                                    mainView.setGenerationNumber(theModel.getField().getGenerationNumber());
                                    mainView.setCurrentAlive(theModel.getField().getCurrentAlive());
                                }
                            } else {
                                Thread.yield();
                            }
                        }
                    } catch (Exception exception){
                        System.out.println(exception.getMessage());
                    }
                });

            } catch (Exception exception){
                System.out.println("Enter positive integer numbers!" + exception.getMessage());
            }
        }

    }



    class PauseListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            mainView.setIconPause(isPaused.getAndSet(!isPaused.get()));
            if(mainThread.getState() == Thread.State.NEW || mainThread.getState() == Thread.State.TERMINATED){
                mainThread.start();
            }
        }
    }

    class LinesListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            mainView.setIconLines(drawLines.getAndSet(!drawLines.get()));
            mainView.getGraphicsPanel().update(theModel.getField().getCurrentGeneration(), drawLines.get());
        }
    }

    class SliderListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            timeout = ((100 - mainView.getSliderValue()) * 4 + 5);
        }
    }

    class RestartListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            System.out.println("-1");
            mainView.setIconPause(false);
            System.out.println("0");
            if(!isPaused.get()){
                isPaused.set(true);
                if (mainThread.getState() != Thread.State.TIMED_WAITING){
                    System.out.println("0.5");
                    Thread.yield();
                }
            }
            System.out.println("1");
            theModel.reset();
            mainView.getGraphicsPanel().update(theModel.getField().getCurrentGeneration(), drawLines.get());
            System.out.println("2");
        }
    }

    class StepListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if(!isPaused.get()){
                while(mainThread.getState() != Thread.State.TIMED_WAITING){
                    Thread.yield();
                }
            }
            theModel.getField().changeGeneration();
            mainView.getGraphicsPanel().update(theModel.getField().getCurrentGeneration(), drawLines.get());
        }
    }

    class BackListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if(mainThread != null){
                mainThread.interrupt();
            }
            if(mainView != null){
                mainView.setVisible(false);
            }
            if(addFigureView != null){
                addFigureView.setVisible(false);
            }
            preView.setVisible(true);
        }
    }

    class RadioListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent event) {
            theModel.setFigure(((JRadioButton)event.getSource()).getText());
            mainView.updateFigure(theModel.getFigureWithBorder(), true);
        }
    }

    class RotateListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent event) {
            theModel.setFigureRotation( mainView.getAngle((JButton)event.getSource()));
            mainView.updateFigure(theModel.getFigureWithBorder(), true);
        }
    }

    class ReverseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            theModel.reverseFigure();
            mainView.updateFigure(theModel.getFigureWithBorder(), true);
        }
    }

    class InsertListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {

            int x;
            int y;
            try{
                x = Integer.parseInt(mainView.getXCor());
                y = Integer.parseInt(mainView.getYCor());
            }catch (NumberFormatException e){
                System.out.println(e.getMessage());
                return ;
            }
            if(!isPaused.get()){
                while(mainThread.getState() != Thread.State.TIMED_WAITING){
                    Thread.yield();
                }
            }
            if(Arrays.deepEquals(theModel.getFigure(), new boolean[][]{{true}})){
                theModel.getField().changeCell(y, x);
            }else{
                theModel.insert(theModel.getFigure(), y, x, mainView.getKillEnvValue());
            }
            mainView.getGraphicsPanel().update(theModel.getField().getCurrentGeneration(), drawLines.get());
        }
    }

    class GraphicsPanelListener implements MouseListener, MouseMotionListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            double side = mainView.getGraphicsPanel().getSide();
            if(!isPaused.get()){
                while(mainThread.getState() != Thread.State.TIMED_WAITING){
                    Thread.yield();
                }
            }
            if(Arrays.deepEquals(theModel.getFigure(), new boolean[][]{{true}})){
                theModel.getField().changeCell((int)(y / side), (int)(x / side));
            }else{
                theModel.insert(theModel.getFigure(), (int)(y / side), (int)(x / side), mainView.getKillEnvValue());
            }
            mainView.getGraphicsPanel().update(theModel.getField().getCurrentGeneration(), drawLines.get());
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            double side = mainView.getGraphicsPanel().getSide();
            mainView.setCors((int)(x / side), (int)(y / side));
        }
    }

    class MainWindowsListener implements WindowListener {

        @Override
        public void windowOpened(WindowEvent e) {
            new Thread(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                mainView.updateFigure(theModel.getFigureWithBorder(), true);
            }).start();
        }

        @Override
        public void windowClosing(WindowEvent e) {

        }

        @Override
        public void windowClosed(WindowEvent e) {

        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }


    class BtnNewFigureListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {

            try{
                int width = preView.getWidthValue();
                int height = preView.getHeightValue();

                theModel = new FieldModel(0, width, height, 1);
                addFigureView = new addFigureView(theModel.getField());

                addFigureView.addSaveListener(new SaveListener());
                addFigureView.addGraphicsPanelListener(new GraphicsPanelListenerAdd());
                addFigureView.addBackListener(new BackListener());
                preView.setVisible(false);
                addFigureView.setVisible(true);

            } catch (Exception exception){
                System.out.println("Enter positive integer numbers!" + exception.getMessage());
            }
        }
    }

    class SaveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = addFigureView.getName();
            try {
                File dir = new File("./figures");
                if(!dir.exists()){
                    dir.mkdir();
                }
                FileOutputStream fileOut = new FileOutputStream(dir + File.separator + name + ".figure");
                ObjectOutputStream outStream = new ObjectOutputStream(fileOut);
                outStream.writeObject(theModel.getCutFigure());
                outStream.close();
                fileOut.close();

            } catch (IOException i) {
                i.printStackTrace();
            }
        }
    }

    class GraphicsPanelListenerAdd implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            double side = addFigureView.getGraphicsPanel().getSide();

            theModel.getField().changeCell((int)(y / side), (int)(x / side));
            addFigureView.getGraphicsPanel().update(theModel.getField().getCurrentGeneration(), true);
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}

