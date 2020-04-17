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

    Audio charge = new Audio("charge.wav");
    Thread chargeThread = new Thread(charge);

    public Game(Player player) {
        this.player = player;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        setUpKeyBindings();
        scoreboard = new Scoreboard(MAX_INNINGS);

        chargeThread.start();
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
                if (scoreboard.halfInning % 2 == 0 && scoreboard.outs == 2) charge.play();
                else charge.stop();

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