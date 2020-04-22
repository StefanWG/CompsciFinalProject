import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Game extends JPanel  {
    public Team awayTeam;
    public Team homeTeam;
    final int WIDTH = 900;
    final int HEIGHT = 780;
    final int MAX_INNINGS = 18;
    boolean rules = false;
    BufferedImage fieldDrawing = Display.drawField();
    BufferedImage resultText = Display.outcomeText(7);
    Scoreboard scoreboard;
    AtBat atBat;

    Audio charge = new Audio("charge.wav");
    Thread chargeThread = new Thread(charge);

    public Game(Team team) {
        homeTeam = team;
        do {
            awayTeam = Loading.teams[(int) (Math.random()*Loading.teams.length)];
        } while (awayTeam.teamName.equals(homeTeam.teamName));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        setUpKeyBindings();
        scoreboard = new Scoreboard(MAX_INNINGS, homeTeam, awayTeam);

        chargeThread.start();
        setLayout(null);
        JButton rulesButton = Display.rulesButton(this);
        add(rulesButton);
        rulesButton.setBounds(650,680, 125, 100);
        JButton atBatButton = Display.atBatButton(this);
        add(atBatButton);
        atBatButton.setBounds(775,680, 125, 100);
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
                Team team;
                if (scoreboard.halfInning%2 == 1) team = awayTeam;
                else team = homeTeam;

                if (scoreboard.halfInning % 2 == 0 && scoreboard.outs == 2) charge.play();
                else charge.stop();
                int order = team.lineupPos % 9;
                atBat = new AtBat(scoreboard.halfInning, this, team.lineup[order]);
                int result = atBat.runAtBat();
                //add one to a players atbat since they just batted
                team.lineup[order].atBats++;
                //based on type of hit, add one to tally of given player that just batted.
                UpdateBoxScore(result, order);
                //advance the order so that next atBat, the next batter is shown
                team.lineupPos++;

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
        g.setColor(Color.lightGray);
        g.fillRect(0,0, 1000,1000);
        g.drawImage(Display.resize(fieldDrawing,650,650), 0, 130, null);
        g.drawImage(Display.resize(Display.drawScoreboard(scoreboard), 900, 180), 0, 0, null);
        g.drawImage(resultText, 0, 275, null);
        if (atBat != null) atBat.draw(g);
        if (rules) g.drawImage(Display.rulesText(), 650,180,null);
        else g.drawImage(Display.atBatOnDeck(scoreboard), 650,180,null);
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

    private void UpdateBoxScore(int result, int order){
        Team team;
        if (scoreboard.halfInning%2 == 1) team = awayTeam;
        else team = homeTeam;
        if (result==1){
            //single
            team.lineup[order].Singles++;
        }
        else if (result == 2){
            //double
            team.lineup[order].Doubles++;
        }
        else if (result==3){
            //triple
            team.lineup[order].Triples++;
        }
        else if (result ==4){
            //HR
            team.lineup[order].HRs++;
        }
    }
}