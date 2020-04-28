import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
//TODO WOrk on commands
//TODO make everything public or private, make things private that can be private
public class Main {
    public static int WIDTH = 900; //DO NOT CHANGE
    public static int HEIGHT = 780; //DO NOT CHANGE
    public JFrame frame = new JFrame("Baseball");
    public Game game = null;
    public Loading loading = new Loading(this);
    public SeasonMode season = null;

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        System.setProperty("apple.laf.useScreenMenuBar", "true");

        setUpMenu(frame);

        frame.setContentPane(loading);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void newSeason() {
        season = new SeasonMode(loading.team, this);
        this.frame.setContentPane(this.season);
        this.frame.pack();
        this.frame.validate();
        this.frame.repaint();
    }

    public void newGame() {
        game = new Game(loading.team);
        this.frame.setContentPane(this.game);
        this.frame.pack();
        this.frame.validate();
        this.frame.repaint();

        Thread t = new Thread(new Runner());
        t.start();
    }

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

    public void pickNewTeam() {
        loading = new Loading(this);
        frame.setContentPane(loading);
        this.frame.pack();
        this.frame.validate();
        this.frame.repaint();
    }

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

        JMenuItem changePlayer = new JMenuItem("Change Player");
        changePlayer.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.META_MASK));
        changePlayer.addActionListener(new AbstractAction() {
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
        file.add(changePlayer);
        file.add(sound);

        menuBar.add(file);
        frame.setJMenuBar(menuBar);
    }

    class Runner implements Runnable {
        public void run() {
            game.runGame();
            endGame();
        }
    }

}