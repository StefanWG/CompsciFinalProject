import java.net.URL;
import java.util.ArrayList;

public class Scoreboard {
    int outs;
    boolean[] bases = new boolean[3];
    int homeRuns;
    int awayRuns;
    int awayHits;
    int homeHits;
    int halfInning;
    int maxInnings;
    ArrayList<Integer> homeRunsInning = new ArrayList<>();
    ArrayList<Integer> awayRunsInning = new ArrayList<>();

    Audio hitball = new Audio("file:" + System.getProperty("user.dir") + "/" + "SoundFiles/hitball.wav");
    Thread hitballThread = new Thread(hitball);

    public Scoreboard(int maxInnings) {
        this.outs = 0;
        this.homeRuns = 0;
        this.awayRuns = 0;
        this.halfInning = 1;
        this.maxInnings = maxInnings;
        for (int i = 0; i < this.bases.length; i++) {
            bases[i] = false;
        }
        awayRunsInning.add(0);

        hitballThread.start();
    }


    private void addRuns(int numRuns) {
        int inning = (halfInning - 1) / 2;
        //this is the home team
        if (this.halfInning % 2 == 0) {
            this.homeRuns += numRuns;
            int runs = this.homeRunsInning.get(inning);
            this.homeRunsInning.remove(inning);
            this.homeRunsInning.add(runs + numRuns);
        }
        //this is the away team
        else {
            this.awayRuns += numRuns;
            int runs = this.awayRunsInning.get(inning);
            this.awayRunsInning.remove(inning);
            this.awayRunsInning.add(runs + numRuns);
        }
    }

    private void Single() {
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
        if (halfInning % 2 == 1) {
            awayHits += 1;
        } else {
            homeHits += 1;
        }
    }

    private void Double() {
        boolean first = this.bases[0];
        boolean second = this.bases[1];
        boolean third = this.bases[2];
        //clear second and third and add 1 or 2 runs.
        //if either second or third is occupied
        if (third || second) {
            //if both are occupied
            if (third && second) {
                addRuns(2);
                this.bases[1] = false;
                this.bases[2] = false;
            }
            //this means only one of the 2 bases to clear are occupied
            else {
                addRuns(1);
                this.bases[1] = false;
                this.bases[2] = false;
            }
        }
        //send first to third
        if (first) {
            this.bases[0] = false;
            this.bases[2] = true;
        }
        //make second base occupied since a double was hit
        this.bases[1] = true;

        if (halfInning % 2 == 1) {
            awayHits += 1;
        } else {
            homeHits += 1;
        }
    }

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

    private void HomeRun() {
        //starts at one since at least one run scores from HR
        int numBases = 1;
        for (int i = 0; i < this.bases.length; i++) {
            boolean result = this.bases[i];
            if (result) {
                numBases++;
            }
            //empty all the bases
            this.bases[i] = false;
        }
        addRuns(numBases);
        if (halfInning % 2 == 1) {
            awayHits += 1;
        } else {
            homeHits += 1;
        }
    }

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


    public void updateBases(int n) {
        hitball.play();
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
        //System.out.println(this);
    }
//update bases method
//    updateBases(int n)
//    Public - will be called from Game if the outcome of at bat needs the bases to be updated
//            Void
//    Int n represents the at bat outcome. There will be a switch statement that updates the bases based on the outcome.

    public void newInning() {
        //resets outs
        this.outs = 0;
        //makes all the bases empty by making them false
        for (int i = 0; i < this.bases.length; i++) {
            bases[i] = false;
        }
        //adds one to the half inning..progressing the game to the next inning
        this.halfInning++;
        if (halfInning % 2 == 1) {
            awayRunsInning.add(0);
        } else {
            homeRunsInning.add(0);
        }
    }

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