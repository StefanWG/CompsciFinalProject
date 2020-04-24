import java.io.File;
import java.util.ArrayList;
//TODO end/ pause game what happens?
//TODO sim other games in season
public class SeasonMode {
    Team team;
    int gamesPlayed = 0;
    int wins = 0;
    int loses = 0;
    Main main;
    ArrayList<SeasonGame> schedule = new ArrayList<>();

    public SeasonMode(Team team, Main main) {
        this.main = main;
        this.team = team;
        readInSchedule();
        System.out.println(schedule);
    }

    private void readInSchedule() {
        File rosters = new File("Rosters");
        File[] list = rosters.listFiles();
        if (list != null) {
            for (int i = 0; i < 2; i++) {
                for (File f : list) {
                    Team t = new Team(String.valueOf(f));
                    if (!t.teamName.equals(team.teamName)) {
                        SeasonGame sG = new SeasonGame(this,schedule.size() + 1, t, main);
                        schedule.add(sG);
                    }
                }
            }
        }

    }

    public void playSeasonGame() {
        schedule.get(gamesPlayed).playSeasonGame();
        gamesPlayed++;
    }

    public static void main(String[] args) {
        Main main = new Main();
        SeasonMode seasonMode = new SeasonMode(new Team("Rosters/HoustonAstronauts.txt"), main);
    }
}

class SeasonGame {
    SeasonMode season;
    boolean played = false;
    int gameNumber;
    int homeRuns = 0;
    int awayRuns = 0;
    String score;
    Team team;
    Main main;

    public SeasonGame(SeasonMode season, int gameNumber, Team team, Main main) {
        this.season = season;
        this.gameNumber = gameNumber;
        this.team = team;
        this.main = main;
    }

    public void playSeasonGame() {
        Game seasonGame = new Game(season.team, team);
        main.frame.setContentPane(seasonGame);
        main.frame.pack();
        main.frame.validate();
        main.frame.repaint();

        seasonGame.runGame();
        homeRuns = seasonGame.scoreboard.homeRuns;
        awayRuns = seasonGame.scoreboard.awayRuns;
        score = homeRuns + " - " + awayRuns;
        played = true;
    }
}
