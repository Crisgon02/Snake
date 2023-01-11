package org.cis1200.Snake;

import java.awt.*;


public class Orange extends Fruit {
    private static final int SIZE = 30;
    private static final int INITIAL_X = 0;
    private static final int INITIALY = 0;

    private static final int VALUE = 2;



    final private Color color;

    public Orange(int courtWidth, int courtHeight, Color color, int val) {
        super(INITIAL_X, INITIALY, SIZE, SIZE, courtWidth, courtHeight, val);
        this.color = color;
    }

    public int[] getIndex() {
        int[] toReturn = new int[2];
        toReturn[0] = this.getPy() / SIZE;
        toReturn[1] = this.getPx() / SIZE;
        return toReturn;
    }



    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillOval(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
    }
}
