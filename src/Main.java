import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Main {
    JFrame frame = new JFrame("Baseball");
    Game game;
    Loading loading = new Loading(this);

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
        game.gameOver = true;
        frame.setContentPane(new GameOver(this));
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

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.META_MASK));
        newGame.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });

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



        file.add(newGame);
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