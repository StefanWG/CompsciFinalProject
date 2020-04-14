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
    BufferedImage resultText = Display.outcomeText(7);
    Scoreboard scoreboard;
    AtBat atBat;

    public Game() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        setUpKeyBindings();
        scoreboard = new Scoreboard(MAX_INNINGS);
    }

    public void newGame() {
        //TODO newGame() method
    }

    public void pauseGame() {
        //TODO pauseGame() method
    }

    public void endGame() {
        //TODO endGame() method
    }

    public boolean gameOver() {
        if (scoreboard.halfInning < scoreboard.maxInnings) return false; //Top 9th or earlier
        else if (scoreboard.halfInning%2 == 1 && scoreboard.homeRuns == scoreboard.awayRuns) return false; //Top of any extra inning and game isn't tied
        else if (scoreboard.halfInning%2 == 0 && scoreboard.homeRuns <= scoreboard.awayRuns) return false; //Bottom of inning and home team isn't winning
        else return true;
    }

    public void runGame() {
        //TODO END THE GAME
        //Sims half inning when
        while (!gameOver()) {
            repaint();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }

            while (scoreboard.outs < 3  && !gameOver()) {
                atBat = new AtBat(scoreboard.halfInning, this, player);
                int result = atBat.runAtBat();
                scoreboard.updateBases(result);
                resultText = Display.outcomeText(result);
                repaint();
                try {
                    Thread.sleep(700);
                } catch (InterruptedException ignored) {
                }
                resultText = Display.outcomeText(7);
                repaint();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ignored) {
                }
            }
            scoreboard.newInning();
        }
    }

    public void pickPlayer() {
        Player josh = new Player(20, "Gladiators", "Sushi", 84, "Josh", 76);
        Player stefan = new Player(19, "Polar Bears", "Buffalo Wings", 74, "Stefan", 86);
        Player rohil = new Player(19, "Rams", "Poke", 80, "Rohil", 80);
        System.out.println(josh);
        System.out.println(stefan);
        System.out.println(rohil);

        int option = getUserInt("Please choose a player (1, 2, 3, or 4 for a random character)");


        if (option == 1) {
            player = josh;
        } else if (option == 2) {
            player = stefan;
        } else if (option == 3) {
            player = rohil;
        } else if (option == 4) {
            player = new Player();
            System.out.println(player);
        }
    }

    static int getUserInt(String prompt) {
        java.util.Scanner scan = new java.util.Scanner(System.in);
        while (true) {
            try {
                System.out.println(prompt);
                while (true) {
                    int input = scan.nextInt();
                    if (input >= 1 && input <= 4) {
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
        g.drawImage(fieldDrawing, 0, 100, null);
        g.drawImage(Display.drawScoreboard(scoreboard), 0, 0, null);
        g.drawImage(resultText, 0, 275, null);
        if (atBat != null) atBat.draw(g);
    }

    private void setUpKeyBindings() {
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "STOP");
        getActionMap().put("STOP", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (atBat != null) atBat.stop();
            }
        });
    }
}