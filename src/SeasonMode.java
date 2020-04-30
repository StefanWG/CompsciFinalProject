/**
 * This file contains three classes: SeasonMode, SeasonTeam, and SeasonGame, all of which
 * are used during season mode.
 *
 * SeasonMode is the home page when a user chooses to play a season. They can see the
 * standings, schedule and opt to sim/play the next game.
 *
 * SeasonTeam is an extension of team specific for seasons. It contains a few extra variables
 * including their schedule, wins, and losses.
 *
 * SeasonGame stores all the information for each individual game, including the two teams
 * and the results. Both teams that are playing have a reference to the game.
 **/

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

//TODO handle ending in the middle of game -- SIM the rest
//TODO season ends?

public class SeasonMode extends JPanel {
    SeasonTeam team;
    Main main;
    ArrayList<SeasonTeam> teams = new ArrayList<>();
    JLabel standingsLabel = new JLabel();
    ArrayList<JLabel> labels = new ArrayList<>();
    int gameNumber = 0;


 //  Creates the graphics for the season mode
 // Creates a grid which is the schedule for the team for that season
    public SeasonMode(Team user, Main main) {
        setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT));

        this.main = main;
        this.team = new SeasonTeam(user);
        createTeams();
        createSchedule(teams);

        JPanel schedulePanel = new JPanel();
        schedulePanel.setLayout(new GridLayout(0, 3, 4, 4));
        schedulePanel.setBackground(Color.gray);
        schedulePanel.setOpaque(true);

        JScrollPane scrollPane = new JScrollPane(schedulePanel);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(8,10));
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.black;
                this.trackColor = Color.darkGray;
            }
        });

        for (SeasonGame s : this.team.schedule) {
            BufferedImage image = Display.resize(Display.scheduleBox(s), 156, 156);
            JLabel label = new JLabel(new ImageIcon(image));
            labels.add(label);
            schedulePanel.add(label);
        }


 //Graphics for the standings, includes the clickable buttons for simulation or next game
        sortStandings();
        JScrollPane standingsPane = new JScrollPane(standingsLabel);
        standingsPane.getVerticalScrollBar().setPreferredSize(new Dimension(8,10));
        standingsPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.black;
                this.trackColor = Color.darkGray;
            }
        });

        standingsPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        standingsLabel.setIcon(new ImageIcon(Display.seasonModeStandings(teams)));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, standingsPane,scrollPane);
        splitPane.setDividerLocation(405);
        splitPane.setDividerSize(0);
        splitPane.setPreferredSize(new Dimension(Main.WIDTH,Main.HEIGHT-80));
        this.add(splitPane);

        JButton playGame = new JButton("Play Next Game");
        playGame.addActionListener(e -> team.schedule.get(gameNumber).playSeasonGame(true));

        JButton simGame = new JButton("Sim Next Game");
        simGame.addActionListener(e -> team.schedule.get(gameNumber).playSeasonGame(false));

        JSplitPane buttonSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, playGame, simGame);
        buttonSplitPane.setPreferredSize(new Dimension(Main.WIDTH,72));
        buttonSplitPane.setDividerLocation(450);
        buttonSplitPane.setDividerSize(0);

        this.add(buttonSplitPane);
    }

    public void simGames() {
        for (SeasonTeam t : teams) {
            if (!t.schedule.get(gameNumber).played) t.schedule.get(gameNumber).playSeasonGame(false);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        gameNumber++;
    }


 //Method that sorts the standings of the all the different teams (first to last place)
    public void sortStandings() {
        for (int j = 0; j < teams.size() - 1; j++) {
            for (int i = teams.size() - 1; i > 0; i--) {
                if (teams.get(i).wins > teams.get(i - 1).wins) Collections.swap(teams, i, i - 1);
            }
        }
    }

    private void createTeams() {
        File rosters = new File("Rosters");
        File[] list = rosters.listFiles();
        teams.add(team);
        if (list != null) {
            for (File f : list) {
                SeasonTeam t = new SeasonTeam(String.valueOf(f), false);
                if (!t.teamName.equals(team.teamName)) teams.add(t);
            }
        }
    }

    private void createSchedule(ArrayList<SeasonTeam> teams) {
        int[] arr = new int[teams.size()];
        for (int i = 0; i < teams.size(); i++) {
            arr[i] = i;
        }
        int size = arr.length;

        for (int j = 0; j < size - 1; j++) {
            new SeasonGame(this, teams.get(arr[0]), teams.get(arr[1]), main, j + 1);
            new SeasonGame(this, teams.get(arr[1]), teams.get(arr[0]), main, j + 1);
            for (int i = 2; i < size / 2 + 1; i++) {
                new SeasonGame(this, teams.get(arr[i]), teams.get(arr[size + 1 - i]), main, j + 1);
                new SeasonGame(this, teams.get(arr[size + 1 - i]), teams.get(arr[i]), main, j + 1);
            }
            int temp = arr[1];
            for (int i = 1; i < size - 1; i++) {
                arr[i] = arr[i + 1];
            }
            arr[size - 1] = temp;
        }
    }
}

class SeasonTeam extends Team {
    ArrayList<SeasonGame> schedule = new ArrayList<>();
    int wins = 0;
    int losses = 0;

    public SeasonTeam(String filePath, boolean humanPlayer) {
        super(filePath, humanPlayer);
    }

    public SeasonTeam(Team team) {
        super(team.filePath, team.humanPlayer);
    }
}

class SeasonGame {
    SeasonMode season;
    boolean played = false;
    int homeRuns = 0;
    int awayRuns = 0;
    SeasonTeam awayTeam;
    SeasonTeam homeTeam;
    Main main;
    Game game;
    int gameNumber;
    Thread play = new Thread(new Play());
    Thread sim = new Thread(new Sim());

    public SeasonGame(SeasonMode season, SeasonTeam homeTeam, SeasonTeam awayTeam, Main main, int gameNumber) {
        this.season = season;
        this.homeTeam = awayTeam;
        this.awayTeam = homeTeam;
        this.main = main;
        this.gameNumber = gameNumber;
        awayTeam.schedule.add(this);
        homeTeam.schedule.add(this);
    }

    public void endGame() {
        if (homeRuns > awayRuns) {
            homeTeam.wins++;
            awayTeam.losses++;
        } else {
            homeTeam.losses++;
            awayTeam.wins++;
        }
        int count = 0;
        for (JLabel label : season.labels) {
            BufferedImage image = Display.resize(Display.scheduleBox(season.team.schedule.get(count)), 156, 156);
            label.setIcon(new ImageIcon(image));
            count++;
        }
        season.sortStandings();
        season.standingsLabel.setIcon(new ImageIcon(Display.seasonModeStandings(season.teams)));

        main.frame.setContentPane(season);
        main.frame.pack();
        main.frame.validate();
        main.frame.repaint();
        season.repaint();
        if (homeTeam.teamName.equals(season.team.teamName) || awayTeam.teamName.equals(season.team.teamName)) {
            season.simGames();
        }
    }

    public void playSeasonGame(boolean playGame) {
        if (played) return;
        played = true;
        game = new Game(homeTeam, awayTeam);
        if (playGame) {
            main.game = game;
            play.start();
        } else {
            sim.start();
        }
    }

    @Override
    public String toString() {
        return awayTeam.teamName + " vs " + homeTeam.teamName;
    }

    class Play implements Runnable {
        public void run() {
            main.frame.setContentPane(game);
            main.frame.pack();
            main.frame.validate();
            main.frame.repaint();

            game.runGame();
            homeRuns = game.scoreboard.homeRuns;
            awayRuns = game.scoreboard.awayRuns;
            endGame();
        }
    }

    class Sim implements Runnable {
        public void run() {
            game.simGame();
            homeRuns = game.scoreboard.homeRuns;
            awayRuns = game.scoreboard.awayRuns;
            endGame();
        }
    }
}

