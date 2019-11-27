import javax.swing.*;
import java.awt.*;
import Graphics.*;
public class GraphicsPanel extends JPanel{
    private boolean[][] field;
    private double side;
    private double paddingLeft;
    private double paddingTop;
    private boolean drawLines = true;

    private Color borderColor = Color.DARK_GRAY;
    private Color cellColor = Color.GREEN;
    private Color emptyColor = Color.BLACK;

    public GraphicsPanel(boolean[][] field) {
        this.field = field;
    }


    public void setSize(){

        if ((double)this.getWidth() / (double)field[0].length < (double)this.getHeight() / (double)field.length){
            side = (double)this.getWidth() / (double)field[0].length;
            paddingTop = (this.getHeight() - (double)field.length * side) / 2.0;
        } else {
            side = (double)this.getHeight() / (double)field.length;
            paddingLeft = (this.getWidth() - (double)field[0].length * side);
        }
        setBounds(getBounds().x + (int)paddingLeft, getBounds().y + (int)paddingTop, (int)(side * field[0].length), (int)(side * field.length));

        paddingTop = 0;
        paddingLeft = 0;
        setBorder(BorderFactory.createLineBorder(borderColor, 1));
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(emptyColor);
        g2.fill(new Cell(paddingLeft, paddingTop, this.getWidth(), this.getHeight()));
        if(side == 0) {
            setSize();
        }
        g2.setColor(cellColor);
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if(field[i][j]){
                    g2.fill(new Cell(paddingLeft + j * side, paddingTop + i * side, side, side));
                }
            }
        }
        g2.setColor(borderColor);
        if(drawLines){
            for (int i = 1; i < field[0].length; i++) {
                g2.draw(new Line(paddingLeft + i * side, paddingTop + 0, paddingLeft + i * side, paddingTop + side * field.length));
            }

            for (int i = 1; i < field.length; i++) {
                g2.draw(new Line(paddingLeft + 0, paddingTop + i * side, paddingLeft + side * field[0].length, paddingTop + i * side));
            }
        }
    }

    public double getSide(){
        return side;
    }

    public void update(boolean[][] field, boolean drawLines) {
        this.field = field;
        this.drawLines = drawLines;
        repaint();
    }

    public void update() {
        setSize();
        repaint();
    }
}
