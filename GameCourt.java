package org.cis1200.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Paths;

/**
 * GameCourt
 *
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 */
public class GameCourt extends JPanel {

    //my Objects:
    private Apple apple;
    private Orange orange;
    private Snake snake;

    //Score and Best Score:
    private int count = 0;
    private int prevCount = 0;
    private int bestCount = 0;

    //Board to track my Objects
    private static  int[][] board = new int[18][20];

    // whether the game is running
    private boolean playing = false;

    // Score labels "
    private final JLabel status;
    private final JLabel best;

    //Game characteristics:
    private Direction dir = Direction.NOWHERE;

    private boolean starting = true;

    private boolean isPaused = false;

    private boolean gameWon = false;

    //checks if snake can wrap around walls
    private boolean wallImmunity = false;




    // Game constants
    public static final int COURT_WIDTH = 600;
    public static final int COURT_HEIGHT = 540;

    public static final int SQRWIDTH = COURT_WIDTH / 20;
    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 120;

    public GameCourt(JLabel status, JLabel best) {

        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        Timer timer = new Timer(INTERVAL, e -> tick());
        timer.start();

        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (dir != Direction.RIGHT && !starting) {
                        dir = Direction.LEFT;
                        snake.move(dir,wallImmunity);
                        starting = false;
                        isPaused = false;
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (dir != Direction.LEFT) {
                        dir = Direction.RIGHT;
                        snake.move(dir,wallImmunity);
                        starting = false;
                        isPaused = false;
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (dir != Direction.UP) {
                        dir = Direction.DOWN;
                        snake.move(dir, wallImmunity);
                        starting = false;
                        isPaused = false;
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (dir != Direction.DOWN) {
                        dir = Direction.UP;
                        snake.move(dir,wallImmunity);
                        starting = false;
                        isPaused = false;
                    }
                }
            }
        });

        this.best = best;
        this.status = status;
    }

    public int[][] getBoard() {
        return board;
    }

    public void resetSnakeBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 2) {
                    board[i][j] = 0;
                }

            }
        }
    }

    //resets the whole board to 0's
    public void newBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = 0;
            }
        }
    }

    public void removeObj(Fruit fruit) {
        int x = fruit.getKeyVal();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == x) {
                    board[i][j] = 0;
                }
            }
        }
    }

    public int getRandomNum(int max) {
        int num = (int)(Math.random() * (max));
        return num;
    }

    public void insert(Fruit fruit) {
        int x = fruit.getKeyVal();
        while (true) {
            int row = getRandomNum(board.length);
            int column = getRandomNum(board[0].length);
            if (board[row][column] == 0) {
                board[row][column] = x;
                fruit.setPx(column * SQRWIDTH);
                fruit.setPy(row * SQRWIDTH);
                break;
            }
        }
    }



    public void fillBoard(Snake s) {
        int[] x = s.getXVals();
        int[] y = s.getYVals();
        for (int i = 0; i < s.getParts(); i++) {
            if (y[i] >= 540 || y[i] < 0 || x[i] >= 600 || x[i] < 0) {
                return;
            }
            int row = (y[i] / SQRWIDTH);
            int column = x[i] / SQRWIDTH;
            board[row][column] = 2;
        }
    }



    //resets all initial components of the game
    public void reset() {
        newBoard();
        dir = Direction.NOWHERE;
        apple = new Apple(COURT_WIDTH, COURT_HEIGHT, Color.RED, 1);
        orange = new Orange(COURT_WIDTH, COURT_HEIGHT, Color.ORANGE, 3);
        snake = new Snake(Color.BLUE, 2);
        insert(orange);
        wallImmunity = false;
        board[9][15] = 1;
        fillBoard(snake);
        playing = true;
        starting = true;
        status.setText("Score: " + 0);
        count = 0;

        requestFocusInWindow();
    }

    public int getCount() {
        return count;
    }

    public void pause() {
        dir = Direction.NOWHERE;
        isPaused = true;
        requestFocusInWindow();
    }

    //used for debugging
    public void printGrid() {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                count++;
                System.out.print(board[i][j] + " ");
                if (count % 20 == 0) {
                    System.out.println("\n");
                }
            }
        }
    }

    public void save(String filePath, boolean append) {
        if (!isPaused) {
            return;
        }
        int[] allX = snake.getXVals();
        int[] allY = snake.getYVals();
        int aX = apple.getPx();
        int aY = apple.getPy();
        int oX = orange.getPx();
        int oY = orange.getPy();
        int sParts = snake.getParts();

        try {
            File file = Paths.get(filePath).toFile();
            if (!file.exists()) {
                return;
            }
            FileWriter writer = new FileWriter(file, append);
            BufferedWriter bwriter = new BufferedWriter(writer);
            for (int i = 0; i < sParts; i++) {
                bwriter.write("snake" + "," +  allX[i] + "," + allY[i] + "\n");
            }

            bwriter.write("Apple" + "," + aX + "," + aY + "\n");
            bwriter.write("Orange" + "," +  oX + "," + oY + "\n");
            bwriter.write("Points" + "," + count + "," + bestCount);
            bwriter.flush();
            bwriter.close();

        } catch (IOException e) {
            String  message = "Can't Save Game";
            JOptionPane random = new JOptionPane("Can't save Game");
            random.showMessageDialog(random, message);
        }
    }

    public void loadSave(String filePath) {
        try {
            FileReader stuff = new FileReader(filePath);
            BufferedReader bReader = new BufferedReader(stuff);
            int i = 0;
            while (true) {
                String x =  bReader.readLine();
                if (x == null) {
                    break;
                }
                System.out.println(x);
                String[] data = x.split(",");
                if (data[0].equals("snake")) {
                    snake.setPos(Integer.parseInt(data[1]),Integer.parseInt(data[2]), i);
                    i += 1;
                    snake.changeLength(i);
                } else if (data[0].equals("Apple")) {
                    apple.setPx(Integer.parseInt(data[1]));
                    apple.setPy(Integer.parseInt(data[2]));
                } else if (data[0].equals("Orange")) {
                    orange.setPx(Integer.parseInt(data[1]));
                    orange.setPy(Integer.parseInt(data[2]));
                } else {
                    count = Integer.parseInt(data[1]);
                    bestCount = Integer.parseInt(data[2]);
                }
            }
            bReader.close();

            //fill board with position of snake and fruit
            newBoard();
            resetSnakeBoard();
            int[] a = apple.getIndex();
            int[] o = orange.getIndex();
            board[a[0]][a[1]] = apple.getKeyVal();
            board[o[0]][o[1]] = orange.getKeyVal();

        } catch (FileNotFoundException e) {
            String  message = "File Not Found ";
            JOptionPane random = new JOptionPane("File Not Found ");
            random.showMessageDialog(random, message);
            throw new RuntimeException(e);
        } catch (IOException e) {
            String  message = "IO Exception";
            JOptionPane random = new JOptionPane("IO Exception ");
            random.showMessageDialog(random, message);
            throw new RuntimeException(e);
        }
    }
    public boolean checkforWin() {
        int num = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == snake.getId()) {
                    num += 1;
                }
            }
        }

        int[] allX = snake.getXVals();
        int[] allY  = snake.getYVals();
        return (num == board.length * board[0].length &&
                allX[0] == allX[359] && allY[0] == allY[359]);
    }

    public boolean gameWon() {
        if (count >= 100 || checkforWin()) {
            return true;
        }
        return false;
    }
    void tick() {
        if (playing) {
            snake.move(dir, wallImmunity);
            resetSnakeBoard();
            fillBoard(snake);

            if (snake.hitWall() || snake.hitSelf()) {
                dir = Direction.NOWHERE;
                playing = false;
                prevCount = count;
                count = 0;
            }
            if (snake.eat(apple)) {
                wallImmunity = false;
                removeObj(apple);
                resetSnakeBoard();
                fillBoard(snake);
                insert(apple);
                snake.grow();
                count += 1;
            }

            if (snake.eat(orange)) {
                wallImmunity = true;
                count += 3;
                snake.grow();
                removeObj(orange);
                resetSnakeBoard();
                fillBoard(snake);
                insert(orange);

            }
            status.setText("Score: " + count);
            if (count >= bestCount) {
                bestCount = count;
                best.setText("Best Score:" + bestCount);
            } else{
                best.setText("Best Score: " + bestCount);
            }
            repaint();
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for (int i = 0; i < board[0].length; i++) {
            g.drawLine(SQRWIDTH * (1 + i), 0, SQRWIDTH * (1 + i), COURT_HEIGHT);
        }
        for (int i = 0; i < board.length; i++) {
            g.drawLine(0, SQRWIDTH * (1 + i), COURT_WIDTH, SQRWIDTH * (1 + i));
        }

        snake.draw(g);
        apple.draw(g);
        orange.draw(g);

        if (!playing) {
            g.setColor(Color.BLACK);
            g.fillRect(150,55, 280,50);
            g.fillRect(150, 155, 300, 50);
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free", Font.BOLD, 50));

            g.drawString("YOU LOST!", 150,100);
            g.drawString("SCORE: " + prevCount,155, 200);
        }

        if (gameWon()) {
            dir = Direction.NOWHERE;
            g.setColor(Color.BLACK);
            g.fillRect(150, 155, 270, 50);
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Ink Free", Font.BOLD, 50));
            g.drawString("YOU WON!", 155,200);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }

}