public class Scoreboard {
    int outs;
    boolean[] bases;
    int homeRuns;
    int awayRuns;
    int halfInning;
    int maxInnings;

    public Scoreboard(int maxInnings){
        this.outs = 0;
        this.homeRuns = 0;
        this.awayRuns = 0;
        this.halfInning = 0;
        this.maxInnings = maxInnings;
        //creating the array of length 3
        bases = new boolean[3];
        //initializing it to 0
        for (int i = 0; i < bases.length; i++){
            bases[i] = false;
        }
    }
    public boolean addOuts(int n) {
        return false;
    }

//    addouts method
//    addOuts(int n)
//    Public - will be called from Game if the outcome of at bat is an out
//    Returns a boolean, true if there are 3 outs, false if not
//    Updates the number of outs and returns true if the inning is over (3 outs). It takes an int which is the number of outs to add.

    public void updateBases(int n) {

    }
//update bases method
//    updateBases(int n)
//    Public - will be called from Game if the outcome of at bat needs the bases to be updated
//            Void
//    Int n represents the at bat outcome. There will be a switch statement that updates the bases based on the outcome.

    public void newInning() {

    }
    //newinning method
//    newInning()
//    Public - will be called from game when inning is over
//            Void
//    This will reset outs and bases, and will add will add one to the next inning



}
