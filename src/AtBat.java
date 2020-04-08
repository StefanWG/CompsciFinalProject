import java.awt.*;

public class AtBat {
    private int contactResult;
    private int powerResult;
    private ContactMeter contactMeter;
    //private PowerMeter powerMeter;
    private ContactMeter powerMeter;
    private int currentInning;
    public Game g;


    public AtBat(int inning, Game g) {
        this.g = g;
        contactResult = -1;
        powerResult = -1;
        currentInning = inning;
        contactMeter = new ContactMeter(0,0, this.g); //Change location
        powerMeter = new ContactMeter(0,100, this.g); //Change location
    }

    public int runAtBat() {
        if (currentInning % 2 == 0) {
            //User At Bat
            contactResult = contactMeter.runMeter();
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
        if (!contactMeter.isRunning()) {
            contactMeter.setStopped(true);
            powerMeter.runMeter();
        } else {
            powerMeter.setStopped(true);
        }
    }

    private int determineOutcome() {
        //TODO Forumla for determining outcome
        int result = contactResult / 17;
        return result;
    }


}
