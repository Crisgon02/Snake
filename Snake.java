package org.cis1200.Snake;

import java.awt.*;

public class Snake {

    private int[] xVals = new int[7800];
    private int[] yVals =  new int[7800];

    private int vel = 30;
    private int parts;
    private static final int SIZE = 30;
    private  static Color color;

    private int snakeId;




    public Snake(Color color, int id) {
        this.color = color;
        xVals[0] = (30 * 3);
        yVals[0] = (30 * 5);
        xVals[1] = (30 * 2);
        xVals[2] = (30);
        yVals[1] = 30 * 5;
        yVals[2] = 30 * 5;
        xVals[3] = (0);
        yVals[3] = 30 * 5;
        parts = 4;
        this.snakeId = id;
    }


    public void setPos(int x, int y, int index) {
        xVals[index] = x;
        yVals[index] = y;

    }
    public void move(Direction d, Boolean noWalls) {
        if (d != Direction.NOWHERE) {
            for (int i = parts; i > 0; i--) {
                xVals[i] = xVals[i - 1];
                yVals[i] = yVals[i - 1];

            }
            switch (d) {
                case NOWHERE:
                    break;
                case UP:
                    yVals[0] = yVals[0] - vel;
                    if (noWalls && yVals[0] < 0) {
                        yVals[0] = 510;
                    }
                    break;
                case DOWN:
                    yVals[0] = yVals[0] + vel;

                    if (noWalls && yVals[0] > 510) {
                        yVals[0] = 0;
                    }
                    break;
                case RIGHT:
                    xVals[0] = xVals[0] + vel;

                    if (noWalls && xVals[0] > 570) {
                        xVals[0] = 0;
                    }
                    break;
                case LEFT:
                    xVals[0] = xVals[0] - vel;


                    if (noWalls && xVals[0] < 0) {
                        xVals[0] = 570;
                    }
                    break;
                default:
            }
        }
    }

    public boolean hitWall() {

        return (xVals[0] >= GameCourt.COURT_WIDTH
                    || xVals[0] < 0 || yVals[0] < 0 || yVals[0] >= GameCourt.COURT_HEIGHT);

    }
    public boolean eat(Fruit obj) {

        int ax = obj.getPx();
        int ay = obj.getPy();

        return (xVals[0] == ax && yVals[0] == ay && ax == xVals[0] && ay == yVals[0]);
    }






    public void grow() {
        xVals[parts] = xVals[parts - 1];
        yVals[parts] = yVals[parts - 1];
        parts += 1;
    }

    public int getParts() {
        return parts;
    }

    public Boolean hitSelf() {
        boolean toReturn = false;
        for (int i = parts; i > 0; i--) {
            if (xVals[i] == xVals[0] && yVals[i] == yVals[0]) {
                toReturn = true;
            }
        }
        return toReturn;
    }

    public void changeLength(int newLength) {
        parts = newLength;
    }

    public int getId() {
        return snakeId;
    }

    public int[] getXVals() {
        return xVals;
    }
    public int[] getYVals() {
        return yVals;
    }

    public void draw(Graphics g) {

        g.setColor(this.color);
        for (int i = 0; i < parts; i++) {
            if (i == 0) {

                g.setColor(Color.GREEN);
                g.fillRect(xVals[i], yVals[i], SIZE, SIZE);
            } else {
                g.setColor(Color.BLUE);
                g.fillRect(xVals[i], yVals[i], SIZE, SIZE);
            }
        }
    }

}
