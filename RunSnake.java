package org.cis1200.Snake;

// imports necessary libraries for Java swing

import javax.swing.*;
import java.awt.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class RunSnake implements Runnable {
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for
        // local variables.

        // Top-level frame in which game components live.
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("Snake");
        frame.setLocation(500, 100);


        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Score:");
        status_panel.add(status);
        final JLabel best = new JLabel("Best Score:" + 0);
        status_panel.add(best);

        // Main playing area
        final GameCourt court = new GameCourt(status, best);
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> court.reset());
        control_panel.add(reset);

        final JButton pauseGame = new JButton("Pause");
        pauseGame.addActionListener(e -> court.pause());
        control_panel.add(pauseGame);

        final JButton instructions = new JButton("Instructions");


        instructions.addActionListener(e ->
                JOptionPane.showMessageDialog(frame, "<html>- Use Key arrows to move the snake " +
                        "<br><br> - If you touch a wall or hit yourself, you lose" +
                        " <br><br>- Eat the Red apples to get 1 Point <html>" +
                        " <br><br>- Eat the oranges to pass through walls and get 3 points"
                        +  "<br><br>- Eating an apple makes you lose " +
                        "the ability to pass through walls"
                        + "<br><br>- To save the game, First pause and then press Save <html>"
                        + "<br><br>- Win by getting 100 points or Filling " +
                        "the board up with the Snake "));
        control_panel.add(instructions);

        final JButton saveGame = new JButton("Save");
        saveGame.addActionListener(e -> court.save("saveSnake.csv", false));
        control_panel.add(saveGame);



        final JButton loadGame = new JButton("LoadGame");
        loadGame.addActionListener(e -> court.loadSave("saveSnake.csv"));
        control_panel.add(loadGame);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset();
    }
}