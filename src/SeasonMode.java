import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

//TODO end/ pause game what happens?
public class SeasonMode extends JPanel {
    SeasonTeam team;
    int gamesPlayed = 0;
    int wins = 0;
    int loses = 0;
    Main main;
    ArrayList<SeasonTeam> teams = new ArrayList<>();
    ArrayList<JLabel> labels = new ArrayList<>();

    public SeasonMode(Team team, Main main) {
        setPreferredSize(new Dimension(900, 780));

        this.main = main;
        this.team = new SeasonTeam(team);
        createTeams();
        createSchedule(teams);

        JPanel schedulePanel = new JPanel();
        schedulePanel.setLayout(new GridLayout(0, 5, 5, 5));

        JScrollPane scrollPane = new JScrollPane(schedulePanel);

        for (SeasonGame s : this.team.schedule) {
            if (!s.awayTeam.teamName.equals("Empty Team") && !s.homeTeam.teamName.equals("Empty Team")) {
                JLabel label = new JLabel(new ImageIcon(Display.scheduleBox(s)));
                labels.add(label);
                schedulePanel.add(label);
            }
        }

        this.add(scrollPane);

        JButton playGame = new JButton("Play Next Game");
        playGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSeasonGame();
            }
        });
        this.add(playGame);
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
        while (!((Math.log(teams.size()) / Math.log(2)) % 1 == 0)) {
            teams.add(new SeasonTeam("Rosters/emptyTeam.txt", false));
        }
    }

    private void createSchedule(ArrayList<SeasonTeam> teams) {
        if (teams.size() == 2) {
            teams.get(0).schedule.add(new SeasonGame(this, teams.get(0), teams.get(1), main));
            teams.get(1).schedule.add(new SeasonGame(this, teams.get(0), teams.get(1), main));
            teams.get(0).schedule.add(new SeasonGame(this, teams.get(1), teams.get(0), main));
            teams.get(1).schedule.add(new SeasonGame(this, teams.get(1), teams.get(0), main));
        } else {
            ArrayList<SeasonTeam> teams1 = new ArrayList<>();
            ArrayList<SeasonTeam> teams2 = new ArrayList<>();
            for (int i = 0; i < teams.size() / 2; i++) {
                teams1.add(teams.get(i));
                teams2.add(teams.get(i + teams.size() / 2));
            }

            for (SeasonTeam seasonTeam1 : teams1) {
                for (SeasonTeam seasonTeam2 : teams2) {
                    seasonTeam1.schedule.add(new SeasonGame(this, seasonTeam1, seasonTeam2, main));
                    seasonTeam2.schedule.add(new SeasonGame(this, seasonTeam1, seasonTeam2, main));
                    seasonTeam1.schedule.add(new SeasonGame(this, seasonTeam2, seasonTeam1, main));
                    seasonTeam2.schedule.add(new SeasonGame(this, seasonTeam2, seasonTeam1, main));
                }
            }
            createSchedule(teams1);
            createSchedule(teams2);
        }
    }

    public void playSeasonGame() {
        team.schedule.get(gamesPlayed).playSeasonGame();
        if (team.schedule.get(gamesPlayed).homeRuns > team.schedule.get(gamesPlayed).awayRuns) wins++;
        else loses++;
        gamesPlayed++;
    }

    public static void main(String[] args) {
        Main main = new Main();
        SeasonMode seasonMode = new SeasonMode(new Team("Rosters/HoustonAstronauts.txt", true), main);
        main.frame.setContentPane(seasonMode);
        main.frame.pack();
        main.frame.validate();
        main.frame.repaint();
    }
}

class SeasonTeam extends Team {
    ArrayList<SeasonGame> schedule = new ArrayList<>();

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
    String score;
    SeasonTeam awayTeam;
    SeasonTeam homeTeam;
    Main main;
    Game game;

    public SeasonGame(SeasonMode season, SeasonTeam homeTeam, SeasonTeam awayTeam, Main main) {
        this.season = season;
        this.homeTeam = awayTeam;
        this.awayTeam = homeTeam;
        this.main = main;
    }

    public void endGame() {
        int count = 0;
        for (JLabel label : season.labels) {
            label.setIcon(new ImageIcon(Display.scheduleBox(season.team.schedule.get(count))));
            count++;
        }

        main.frame.setContentPane(season);
        main.frame.pack();
        main.frame.validate();
        main.frame.repaint();
        season.repaint();
    }

    public void playSeasonGame() {
        System.out.print("h1");
        game = new Game(homeTeam, awayTeam);
        main.frame.setContentPane(game);
        main.frame.pack();
        main.frame.validate();
        main.frame.repaint();
        System.out.print("h1");

        Thread t = new Thread(new Runner());
        t.start();
    }

    @Override
    public String toString() {
        return awayTeam.teamName + " vs " + homeTeam.teamName;
    }

    class Runner implements Runnable {
        public void run() {
            game.runGame();
            homeRuns = game.scoreboard.homeRuns;
            awayRuns = game.scoreboard.awayRuns;
            score = homeRuns + " - " + awayRuns;
            played = true;
            endGame();
        }
    }
}

