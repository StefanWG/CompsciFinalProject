import java.awt.*;

public class AtBat {
    private int contactResult;
    private int powerResult;
    private Meter contactMeter;
    private Meter powerMeter;
    private int currentInning;
    public Game g;


    public AtBat(int inning, Game g) {
        this.g = g;
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
        return (contactResult + powerResult) /34;
    }


}
