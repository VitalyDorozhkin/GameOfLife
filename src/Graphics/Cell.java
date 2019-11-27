package Graphics;

import java.awt.geom.Rectangle2D;

public class Cell extends Rectangle2D {

    private Point point;
    private double width;
    private double height;
    private boolean state;

    public Cell(double x, double y, double w, double h){
        point = new Point(x, y);
        width = w;
        height = h;
    }

    @Override
    public void setRect(double x, double y, double w, double h) {
        point.setLocation(x, y);
        width = w;
        height = h;
    }

    @Override
    public int outcode(double x, double y) {
        return 0;
    }

    @Override
    public Rectangle2D createIntersection(Rectangle2D r) {
        return null;
    }

    @Override
    public Rectangle2D createUnion(Rectangle2D r) {
        return null;
    }

    @Override
    public double getX() {
        return point.getX();
    }

    @Override
    public double getY() {
        return point.getY();
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public boolean isEmpty() {
        return height * width != 0;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void changeState() {
        state = !state;
    }
}
