import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Main {
    JFrame frame = new JFrame("Baseball");
    Game game = new Game();

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        System.setProperty("apple.laf.useScreenMenuBar", "true");

        // frame.setContentPane(LoadingClass);
        //do whatever to get player,when finished
        frame.setContentPane(game);
        setUpMenu(frame);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        Thread t = new Thread(new Runner());
        t.start();
    }

    public void setUpMenu(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, InputEvent.META_MASK));
        newGame.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.newGame();
                System.out.println("New Game");
            }
        });

        JMenuItem changePlayer = new JMenuItem("Change Player");
        changePlayer.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_C, InputEvent.META_MASK));
        changePlayer.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Change Player");
                //TODO do we want this as an option?
            }
        });

        JMenuItem pauseGame = new JMenuItem("Pause Game");
        pauseGame.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_P, InputEvent.META_MASK));
        pauseGame.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pause Game");
                game.pauseGame();
            }
        });


        file.add(newGame);
        file.add(changePlayer);
        file.add(pauseGame);

        menuBar.add(file);
        frame.setJMenuBar(menuBar);

    }

    class Runner implements Runnable {
        public void run() {
            game.pickPlayer();
            game.runGame();
            game.endGame();
        }
    }
}


