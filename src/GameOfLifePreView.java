import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameOfLifePreView extends JFrame {

    private JTextField width = new JTextField(5);
    private JTextField height = new JTextField(5);
    private JTextField seed = new JTextField(8);
    private JTextField generationNumber = new JTextField(8);
    private JButton btnRun = new JButton("RUN!");
    private JButton btnNewFigure = new JButton("NEW FIGURE!");

    private Color bcolor = Color.BLACK;
    private Color fcolor = Color.GREEN;

    GameOfLifePreView(){

        this.setTitle("Game Of Life");

        JPanel panel = new JPanel();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600, 200);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(bcolor);
        width.setText("60");
        height.setText("60");
        seed.setText(String.valueOf((int)System.currentTimeMillis()));
        generationNumber.setText("1");
        width.setBackground(bcolor);
        width.setForeground(fcolor);
        height.setBackground(bcolor);
        height.setForeground(fcolor);
        seed.setBackground(bcolor);
        seed.setForeground(fcolor);
        generationNumber.setBackground(bcolor);
        generationNumber.setForeground(fcolor);
        btnRun.setBackground(bcolor);
        btnRun.setForeground(fcolor);
        btnNewFigure.setBackground(bcolor);
        btnNewFigure.setForeground(fcolor);




        panel.setBackground(bcolor);
        panel.add(width);
        panel.add(height);
        panel.add(seed);
        panel.add(generationNumber);
        panel.add(btnRun);
        panel.add(btnNewFigure);

        this.add(panel);
    }

    public void addBtnRunListener(ActionListener listener) {
        btnRun.addActionListener(listener);
    }

    public void addBtnNewFigureListener(ActionListener listener) {
        btnNewFigure.addActionListener(listener);
    }

    public int getWidthValue(){
        return Integer.parseInt(width.getText());
    }

    public int getHeightValue(){
        return Integer.parseInt(height.getText());
    }

    public int getSeedValue(){
        return Integer.parseInt(seed.getText());
    }


    public int getGenerationNumberValue(){
        return Integer.parseInt(generationNumber.getText());
    }
}
