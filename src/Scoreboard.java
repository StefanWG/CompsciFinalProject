/**
 * The Scoreboard class stores all the information about the state the game is in.
 * It also is responsible for updating the game state given specific results.
 **/

import java.util.ArrayList;

public class Scoreboard {
    //tracks outs
    int outs;
    //this array is what keeps track of who is one what base
    boolean[] bases = new boolean[3];
    //these track hits and runs for both teams
    int homeRuns;
    int awayRuns;
    int awayHits;
    int homeHits;
    //counting in half innings (so 18 innings total), Each time 3 outs ar hit this advances
    int halfInning;
    //how many inning we want to play
    int maxInnings;
    //the two teams to play
    Team homeTeam;
    Team awayTeam;
    //to keep track of the number of runs in each inning
    ArrayList<Integer> homeRunsInning = new ArrayList<>();
    ArrayList<Integer> awayRunsInning = new ArrayList<>();

    //the audio file and thread that plays hit audio
    Audio hitball = new Audio("AudioFiles/hitball.wav");
    Thread hitballThread = new Thread(hitball);

    //constructor
    public Scoreboard(int maxInnings, Team homeTeam, Team awayTeam) {
        //initializing the variable using inputs from constructor or initializing to 0
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.outs = 0;
        this.homeRuns = 0;
        this.awayRuns = 0;
        //initialized to 1 since we are starting in the first half inning
        this.halfInning = 1;
        this.maxInnings = maxInnings;
        //initializing the bases to false since no one is on base and the game hasn't started.
        for (int i = 0; i < this.bases.length; i++) {
            bases[i] = false;
        }
        //initialization of complex data structures to avoid null pointers
        awayRunsInning.add(0);

        hitballThread.start();
    }

    /**this method adds runs tot he totals based on which half inning we are in
     *
     * @param numRuns
     */
    private void addRuns(int numRuns) {
        //tells us which inning we are in.
        int inning = (halfInning - 1) / 2;
        //this is the home team
        if (this.halfInning % 2 == 0) {
            //adds RBIs to this player based on how many runs he drove in
            this.homeTeam.lineup[homeTeam.lineupPos%9].RBIs += numRuns;
            //adds runs to the home team total
            this.homeRuns += numRuns;
            //finds total number of runs scored in this inning
            int runs = this.homeRunsInning.get(inning);
            this.homeRunsInning.remove(inning);
            this.homeRunsInning.add(runs + numRuns);
        }
        //this is the away team
        //essentially same code as above but all being added to away teams totals and players
        else {
            //adds RBIs to this player based on how many runs he drove in
            this.awayTeam.lineup[awayTeam.lineupPos%9].RBIs += numRuns;
            //adds runs to the home team total
            this.awayRuns += numRuns;
            //finds total number of runs scored in this inning
            int runs = this.awayRunsInning.get(inning);
            this.awayRunsInning.remove(inning);
            this.awayRunsInning.add(runs + numRuns);
        }
    }

    /** This method updates the bases and adds runs for when a single is hit
     *
     */
    private void Single() {
        //find initial state of bases
        boolean first = this.bases[0];
        boolean second = this.bases[1];
        boolean third = this.bases[2];
        //send person at third home
        if (third) {
            addRuns(1);
            this.bases[2] = false;
        }
        //send person at second to third
        if (second) {
            this.bases[1] = false;
            this.bases[2] = true;
        }
        //send person at first to second
        if (first) {
            //send them to second
            this.bases[1] = true;
            //not clearing first base because it was a single and hitter will
            //now occupy first, hence first is still occupied
        }
        this.bases[0] = true; //send person to first base
        //adding the hit to the total for thee respective teams hits
        if (halfInning % 2 == 1) {
            awayHits += 1;
        } else {
            homeHits += 1;
        }
    }

    /** This method updates the bases and adds runs for when a double is hit
     *
     */
    private void Double() {
        //find initial state of bases
        boolean first = this.bases[0];
        boolean second = this.bases[1];
        boolean third = this.bases[2];

        //Add run for if runners are on 2nd and 2rd
        if (second) addRuns(1);
        if (third) addRuns(1);
        //Clear Bases
        this.bases[1] = false;
        this.bases[2] = false;
        //send first to third
        if (first) {
            this.bases[0] = false;
            this.bases[2] = true;
        }
        //make second base occupied since a double was hit
        this.bases[1] = true;
        //adding the hit to the total for thee respective teams hits
        if (halfInning % 2 == 1) {
            awayHits += 1;
        } else {
            homeHits += 1;
        }
    }

    /** This method updates the bases and adds runs for when a triple is hit
     *
     */
    private void Triple() {
        //very similar to home run. clears all bases and adds run accordingly
        //however at the end, third base gets occupied.
        //however, no guarantee of one scoring like HR, so we take one away from the tally
        //to count for this
        HomeRun();
        //compensation for extra run from HR
        addRuns(-1);
        //make third base occupied and first and second empty
        this.bases[0] = false;
        this.bases[1] = false;
        this.bases[2] = true;
    }

    /** This method updates adds runs for when a home run is hit
     *
     */
    private void HomeRun() {
        //starts at one since at least one run scores from HR
        int numBases = 1;
        //checking how many people are on base and for each person on each base, adding to numruns
        //will add numRuns to total since all of thm score after a home run.
        for (int i = 0; i < this.bases.length; i++) {
            boolean result = this.bases[i];
            if (result) {
                numBases++;
            }
            //empty all the bases
            this.bases[i] = false;
        }
        //adding runs to the total
        addRuns(numBases);
        //adding the hit to the total for thee respective teams hits
        if (halfInning % 2 == 1) {
            awayHits += 1;
        } else {
            homeHits += 1;
        }
    }

    /** This method updates the bases and adds runs for when a walk is hit
     *
     */
    private void Walk() {
        //here runs only score if bases are loaded
        boolean first = this.bases[0];
        boolean second = this.bases[1];
        boolean third = this.bases[2];
        //if bases are loaded add a run and keep bases loaded
        if (first && second && third) {
            addRuns(1);
        }
        //if first is empty, just fill first
        if (!first) {
            this.bases[0] = true;
        }
        //otherwise first is full
        else {
            //first is full but second empty
            if (!second) {
                this.bases[1] = true;
            }
            //first and second are full
            else {
                if (!third) {
                    this.bases[2] = true;
                }
                //otherwise all three are full and this has been accounted for at the beginning
            }
        }
    }

    /**
     * this method combines the single/double/etc. methods into one method.
     */

    public void updateBases(int n, boolean human) {
        if (human) hitball.play();
        switch (n) {
            //out
            case 0:
                this.outs++;
                break;
            //single
            case 1:
                Single();
                break;
            //double
            case 2:
                Double();
                break;
            //triple
            case 3:
                Triple();
                break;
            //HR
            case 4:
                HomeRun();
                break;
            //Walk
            case 5:
                Walk();
                break;
        }
    }

    /** This method advances the inning (resets bases, adds the counter for which inning we are in)
     *
     */
    public void newInning() {
        //resets outs
        this.outs = 0;
        //makes all the bases empty by making them false
        for (int i = 0; i < this.bases.length; i++) {
            bases[i] = false;
        }
        //adds one to the half inning..progressing the game to the next inning
        this.halfInning++;
        //adds a 0 to signify the start of a new inning.
        if (halfInning % 2 == 1) {
            awayRunsInning.add(0);
        } else {
            homeRunsInning.add(0);
        }
    }

    /**to String method to print out thee scoreboard in thee terminal and also to display.
     *
     * @return
     */
    public String toString() {
        String str;
        String i = (halfInning / 2) + " ";
        if (halfInning % 2 == 1) i += "<";
        else i += ">";
        str = "Away: " + awayRuns + "Home: " + homeRuns + "Inning: " + i + "Outs: " + outs + "\n";
        str += bases[0] + " " + bases[1] + " " + bases[2] + "\n";
        return str;
    }


}