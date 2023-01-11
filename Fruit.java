package org.cis1200.Snake;

import java.awt.*;

/**
 * An object in the game.
 *
 * Game objects exist in the game court. They have a position, velocity, size
 * and bounds. Their velocity controls how they move; their position should
 * always be within their bounds.
 */
public abstract class Fruit {

    private int px;
    private int py;

    private final int width;
    private final int height;

    /*
     * Upper bounds of the area in which the object can be positioned. Maximum
     * permissible x, y positions for the upper-left hand corner of the object.
     */
    private final int maxX;
    private final int maxY;

    private int objkey;


    /**
     * Constructor
     */
    public Fruit(
           int px, int py, int width, int height, int courtwidth,
            int courtheight, int key
    ) {

        this.px = px;
        this.py = py;
        this.width = width;
        this.height = height;

        // take the width and height into account when setting the bounds for
        // the upper left corner of the object.
        this.maxX = courtwidth - width;
        this.maxY = courtheight - height;
        objkey = key;
    }

    // **********************************************************************************
    // * GETTERS
    // **********************************************************************************
    public int getPx() {
        return this.px;
    }

    public int getPy() {
        return this.py;
    }


    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getKeyVal() {
        return objkey;
    }

    // **************************************************************************
    // * SETTERS
    // **************************************************************************
    public void setPx(int px) {
        this.px = px;
        clip();

    }

    public void setPy(int py) {
        this.py = py;
        //clip();
    }


    // **************************************************************************
    // * UPDATES AND OTHER METHODS
    // **************************************************************************


    private void clip() {
        this.px = Math.min(Math.max(this.px, 0), this.maxX);
        this.py = Math.min(Math.max(this.py, 0), this.maxY);
    }


    public abstract void draw(Graphics g);
}