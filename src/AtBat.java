import java.awt.*;

public class AtBat {
    private int contactResult;
    private int powerResult;
    private Meter contactMeter;
    private Meter powerMeter;
    private int currentInning;
    public Game g;
    public Player player;


    public AtBat(int inning, Game g, Player p) {
        this.g = g;
        player = p;
        contactResult = -1;
        powerResult = -1;
        currentInning = inning;
        contactMeter = new Meter(0,0, this.g); //Change location
        powerMeter = new Meter(0,100, this.g); //Change location
    }

    public int runAtBat() {
        if (currentInning % 2 == 0) {
            //User At Bat
            contactResult = contactMeter.runMeter();
            powerResult = powerMeter.runMeter();
        } else {
            //TODO Computer At Bat
            contactResult = (int) (Math.random() * 101);
            powerResult = (int) (Math.random() * 101);
        }

        return determineOutcome();
    }

    public void draw(Graphics g) {
        if (contactMeter != null) contactMeter.draw(g);
        if (powerMeter != null) powerMeter.draw(g);
    }

    public void stop() {
        if (contactMeter.isRunning()) {
            contactMeter.setStopped(true);
        } else {
            powerMeter.setStopped(true);
        }
    }

    private int determineOutcome() {
        //TODO Forumla for determining outcome
        int result = findResult();
        //Manipulate result to 0 -4 ;
        if (result > 98) return 4;
        else if (result >97) return 3;
        else if (result > 90) return 2;
        else if (result > 70) return 1;
        else return 0;
    }
    
    private int findResult() {
        //this is where we determine how we want conbatct and powr to be handld
        //turn this into one number to use in get Result (that returns 0 to 4 outcome)
        //return from 0-100
        contactResult = calculateConRes();
        powerResult = calculatePowRes();
        
        int result = 0;
        double contactWeight = .5;
        double powerWeight = .5;

        //Use weights and results to come up with new result, 0-100

        return result;
    }
    
    private int calculateConRes(){
        //Using the rating and meter result it will return an update contact result
        return contactResult;
    }
    
    private int calculatePowRes(){
        //Using the rating and meter result it will return an update power result
        return powerResult;
    }

}
