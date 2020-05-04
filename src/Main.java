/**
 * This class has the code that controls the JFrame. All the changes in the
 * content frame and some of the keyboard commands happen in this class.
 * The project is also beings in the main methods in this class.
 **/

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
public class Main {
    public static int WIDTH = 900; //DO NOT CHANGE
    public static int HEIGHT = 780; //DO NOT CHANGE
    public JFrame frame = new JFrame("Baseball");
    public Game game = null;
    public Loading loading = new Loading(this);
    public SeasonMode season = null;

    public static void main(String[] args) {
        System.out.println("Note: The JFrame takes a few seconds to load.");
        System.out.println("There are instructions once you pick a team under the rules tab on the bottom right.");
        System.out.println("Have fun and good luck!");
        new Main();
    }

    /**
     * Constructor, sets up JFrame.
     **/

    public Main() {
        System.setProperty("apple.laf.useScreenMenuBar", "true");

        setUpMenu(frame);

        frame.setContentPane(loading);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * Switches contentPane to SeasonMode.
     **/

    public void newSeason() {
        season = new SeasonMode(loading.team, this);
        this.frame.setContentPane(this.season);
        this.frame.pack();
        this.frame.validate();
        this.frame.repaint();
    }

    /**
     * Switches contentPane to Game and runs it.
     **/

    public void newGame() {
        game = new Game(loading.team);
        this.frame.setContentPane(this.game);
        this.frame.pack();
        this.frame.validate();
        this.frame.repaint();

        Thread t = new Thread(new Runner());
        t.start();
    }

    /**
     * Method is called when the end game command is pressed. Ends the game and changes the contentPane
     * depending on game mode.
     **/

    public void endGame() {
        Audio.stopAll();
        if (game != null) {
            game.gameOver = true;
        }
        if (season != null) {
            frame.setContentPane(season);
        } else {
            frame.setContentPane(new GameOver(this));
        }
        this.frame.pack();
        this.frame.validate();
        this.frame.repaint();
    }

    /**
     * Resets the contant pane to the loading screen so the user can pick a new team.
     **/

    public void pickNewTeam() {
        season = null;
        loading = new Loading(this);
        frame.setContentPane(loading);
        this.frame.pack();
        this.frame.validate();
        this.frame.repaint();
    }

    /**
     * Sets up the menu to be placed in the menuBar.
     **/

    public void setUpMenu(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");

        JMenuItem endGame = new JMenuItem("End Game");
        endGame.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_E, ActionEvent.META_MASK));
        endGame.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endGame();
            }
        });

        JMenuItem changeTeam = new JMenuItem("Change Team");
        changeTeam.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.META_MASK));
        changeTeam.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pickNewTeam();
            }
        });

        JMenuItem sound = new JMenuItem("Mute Sound");
        sound.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_M, ActionEvent.META_MASK));
        sound.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Audio.toggleSound();
                if (sound.getText().equals("Mute Sound")) sound.setText("Unmute Sound");
                else sound.setText("Mute Sound");
            }
        });

        file.add(endGame);
        file.add(changeTeam);
        file.add(sound);

        menuBar.add(file);
        frame.setJMenuBar(menuBar);
    }

    /**
     * Creates new thread for the game to be run on so it doesn't interfere with commands.
     **/

    class Runner implements Runnable {
        public void run() {
            game.runGame();
            endGame();
        }
    }

}