/**
 * This class controls the game when it is in progress. It displays the current state
 * of the game, as well as advances through the at bats. The spacebar control occurs
 * in this class.
 **/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class Game extends JPanel {
    public Team awayTeam;
    public Team homeTeam;
    final int MAX_INNINGS = 18;
    boolean rules = false;
    boolean gameOver = false;
    BufferedImage fieldDrawing = Display.drawField();
    BufferedImage resultText = Display.outcomeText(7);
    Scoreboard scoreboard;
    AtBat atBat;

    Audio charge = new Audio("AudioFiles/charge.wav");
    Thread chargeThread = new Thread(charge);

    /**
     * Constructors
     **/

    public Game(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT));

        setUpKeyBindings();
        scoreboard = new Scoreboard(MAX_INNINGS, homeTeam, awayTeam);

        chargeThread.start();
        setLayout(null);
        JButton rulesButton = Display.rulesButton(this);
        add(rulesButton);
        rulesButton.setBounds(650, 680, 125, 100);
        JButton atBatButton = Display.atBatButton(this);
        add(atBatButton);
        atBatButton.setBounds(775, 680, 125, 100);
    }

    public Game(Team team) {
        homeTeam = team;
        String[] rosters = new File("Rosters").list();
        if (rosters != null) {
            do {
                String rosterPath = "Rosters/" + rosters[(int) (Math.random() * rosters.length)];
                awayTeam = new Team(rosterPath, false);
            } while (awayTeam.teamName.equals(homeTeam.teamName));
        }

        setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT));

        setUpKeyBindings();
        scoreboard = new Scoreboard(MAX_INNINGS, homeTeam, awayTeam);

        chargeThread.start();
        setLayout(null);
        JButton rulesButton = Display.rulesButton(this);
        add(rulesButton);
        rulesButton.setBounds(650, 680, 125, 100);
        JButton atBatButton = Display.atBatButton(this);
        add(atBatButton);
        atBatButton.setBounds(775, 680, 125, 100);
    }

    /**
     * Checks if the game is over.
     **/

    public boolean gameOver() {
        if (scoreboard.halfInning < scoreboard.maxInnings) return false; //Top 9th or earlier
        else if (scoreboard.halfInning % 2 == 1 && scoreboard.homeRuns == scoreboard.awayRuns)
            return false; //Top of any extra inning and game isn't tied
        else
            return scoreboard.halfInning % 2 != 0 || scoreboard.homeRuns > scoreboard.awayRuns; //Bottom of inning and home team isn't winning
    }

    /**
     * This is the code for the game if the player is playing the game.
     **/

    public void runGame() {
        homeTeam.resetPlayerStats();
        awayTeam.resetPlayerStats();
        boolean human = homeTeam.humanPlayer || awayTeam.humanPlayer;
        //Sims half inning when
        while (!gameOver() && !gameOver) {
            repaint();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            while (scoreboard.outs < 3 && !gameOver() && !gameOver) {
                Team team;
                if (scoreboard.halfInning % 2 == 1) team = awayTeam;
                else team = homeTeam;

                if (team.humanPlayer && scoreboard.outs == 2) charge.play();
                else charge.stop();

                atBat = new AtBat(this, team);
                int result = atBat.runAtBat(team.humanPlayer);
                updateBoxScore(result);
                scoreboard.updateBases(result, human);

                team.lineupPos++;

                resultText = Display.outcomeText(result);
                repaint();
                try {
                    Thread.sleep(700);
                } catch (InterruptedException ignored) {
                }
                resultText = Display.outcomeText(7); //Empty text
                repaint();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ignored) {
                }

            }
            scoreboard.newInning();
        }
        if (!gameOver()) {
            simGame();
        }
    }

    /**
     * This is the code for the game if it is being simulated.
     **/

    public void simGame() {
        //Sims half inning when
        while (!gameOver()) {
            while (scoreboard.outs < 3 && !gameOver()) {
                Team team;
                if (scoreboard.halfInning % 2 == 1) team = awayTeam;
                else team = homeTeam;

                atBat = new AtBat(this, team);
                int result = atBat.runAtBat(false);
                updateBoxScore(result);
                scoreboard.updateBases(result, false);

                team.lineupPos++;
            }
            scoreboard.newInning();
        }
    }


    /**
     * Paints the JPanel by drawing the desired buffered images.
     **/
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, 1000, 1000);
        g.drawImage(Display.resize(fieldDrawing, 650, 650), 0, 130, null);
        g.drawImage(Display.resize(Display.drawScoreboard(scoreboard), 900, 180), 0, 0, null);
        g.drawImage(resultText, 0, 275, null);
        if (atBat != null) atBat.draw(g);
        if (rules) g.drawImage(Display.rulesText(), 650, 180, null);
        else g.drawImage(Display.atBatOnDeck(scoreboard), 650, 180, null);
    }

    /**
     * Sets up the keybindings for this class (only Spacebar for playing the game)
     **/

    private void setUpKeyBindings() {
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "STOP");
        getActionMap().put("STOP", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (atBat != null) atBat.stop();
            }
        });
    }

    /**
     * Given an outcome, this method updates the boxscore.
     **/

    private void updateBoxScore(int result) {
        Team team;
        if (scoreboard.halfInning % 2 == 1) team = awayTeam;
        else team = homeTeam;
        if (result == 1) team.lineup[team.lineupPos % 9].singles++;
        else if (result == 2) team.lineup[team.lineupPos % 9].doubles++;
        else if (result == 3) team.lineup[team.lineupPos % 9].triples++;
        else if (result == 4) team.lineup[team.lineupPos % 9].HRs++;
        team.lineup[team.lineupPos % 9].atBats++;
    }
}