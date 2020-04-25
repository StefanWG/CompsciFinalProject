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
        if (teams.size() / 2 == 1) {
            teams.add(new SeasonTeam("Rosters/emptyTeam.txt", false));
        }
    }

    private void createSchedule(ArrayList<SeasonTeam> teams) {
        int[] arr = new int[teams.size()];
        for (int i = 0; i < teams.size(); i++) {
            arr[i] = i;
        }
        int size = arr.length;

        for (int j = 0; j < size - 1; j++) {
            //TODO arrr[i] for all
            new SeasonGame(this, teams.get(arr[0]), teams.get(1), main);
            new SeasonGame(this, teams.get(1), teams.get(0), main);
            for (int i = 2; i < size / 2 + 1; i++) {
                new SeasonGame(this, teams.get(i), teams.get(size+1-i), main);
                new SeasonGame(this, teams.get(size+1-i), teams.get(i), main);
            }
            int temp = arr[1];
            for (int i = 1; i < size-1; i++) {
                arr[i] = arr[i+1];
            }
            arr[size-1] = temp;
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
        awayTeam.schedule.add(this);
        homeTeam.schedule.add(this);
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

