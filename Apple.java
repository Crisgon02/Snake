package org.cis1200.Snake;

import java.awt.*;

/**
 * A basic game object starting in the upper left corner of the game court. It
 * is displayed as a circle of a specified color.
 */
public class Apple extends Fruit {
    public static final int SIZE = 30;
    public static final int INITIALX = 450;
    public static final int INITIALY = 270;
 

    public static final int VALUE = 1;

    final private Color color;

    public Apple(int courtWidth, int courtHeight, Color color, int val) {
        super(INITIALX, INITIALY, SIZE, SIZE, courtWidth, courtHeight, val);
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
        //drawApple(g);
        g.fillOval(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
    }
}