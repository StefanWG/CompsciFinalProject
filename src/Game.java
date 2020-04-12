import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Game extends JPanel {
    public Player player;
    final int WIDTH = 800;
    final int HEIGHT = 900;
    final int MAX_INNINGS = 18;
    BufferedImage fieldDrawing = Display.drawField();
    Scoreboard scoreboard;
    AtBat atBat;

    public Game() {
        setPreferredSize(new Dimension(WIDTH,HEIGHT));

        setUpKeyBindings();
        scoreboard = new Scoreboard(MAX_INNINGS);
    }

    public void runGame() {
        //Sims half inning when
        while (scoreboard.halfInning <= scoreboard.maxInnings || scoreboard.awayRuns == scoreboard.homeRuns) {
            while (scoreboard.outs < 3) {
                atBat = new AtBat(scoreboard.halfInning, this, player);
                int result = atBat.runAtBat();
                scoreboard.updateBases(result);
                repaint();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}
            }
            scoreboard.newInning();
        }
    }

    public void pickPlayer () {
        //TODO Code for picking player Josh doing this for now
        int option = getUserInt("Please choose a player (1, 2, 3, or 4 for a random character");

        if (option == 1) {
            player = new Player(20, "Gladiators", "Sushi", 84, "Josh", 76);
        } else if (option == 2) {
            player = new Player (19, "Polar Bears", "Buffalo Wings",74, "Stefan", 86);
        } else if (option ==3 ) {
            player = new Player (19, "Rams", "Poke", 80, "Rohil", 80);
        } else if (option == 4) {
            player = new Player();
        }
    }

    static int getUserInt(String prompt) {
        java.util.Scanner scan = new java.util.Scanner(System.in);
        while (true) {
            try {
                System.out.println(prompt);
                while (true){
                    int input = scan.nextInt();
                    if (input >= 1 && input <= 4){
                        return input;
                    }
                    System.out.println("Sorry incorrect input.");
                    System.out.println(prompt);
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Sorry incorrect input.");
                scan.nextLine();     // to clear the rest of the line
            }
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(fieldDrawing,0,100,null);
        g.drawImage(Display.drawScoreboard(scoreboard),0,0,null);
        if (atBat != null) atBat.draw(g);
    }

    private void setUpKeyBindings() {
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0),"STOP");
        getActionMap().put("STOP", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (atBat != null) atBat.stop();
            }
        });

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke((char) KeyEvent.VK_0), "0");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke((char) KeyEvent.VK_1), "1");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke((char) KeyEvent.VK_2), "2");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke((char) KeyEvent.VK_3), "3");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke((char) KeyEvent.VK_4), "4");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke((char) KeyEvent.VK_5), "5");

        getActionMap().put("0", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                scoreboard.updateBases(0);
            }
        });
        getActionMap().put("1", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                scoreboard.updateBases(1);
            }
        });
        getActionMap().put("2", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                scoreboard.updateBases(2);
            }
        });
        getActionMap().put("3", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                scoreboard.updateBases(3);
            }
        });
        getActionMap().put("4", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                scoreboard.updateBases(4);
            }
        });
        getActionMap().put("5", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                scoreboard.updateBases(5);
            }
        });
    }
}
