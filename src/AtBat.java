import java.awt.*;
import java.awt.event.KeyAdapter;

public class AtBat  {
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
        //TODO imporve positioning of meters
        contactMeter = new Meter(30,650, this.g); //Change location
        powerMeter = new Meter(470,650, this.g); //Change location
    }

    public int runAtBat() {
        if (currentInning % 2 == 0) {
            //User At Bat
            contactResult = contactMeter.runMeter();
            powerResult = powerMeter.runMeter();
        } else {
            contactResult = (int) (Math.random() * 51) + 50;
            powerResult = (int) (Math.random() * 51) + 50;
        }
        return determineOutcome();
    }

    public void draw(Graphics g) {
        if (contactMeter != null) contactMeter.draw(g);
        if (powerMeter != null) powerMeter.draw(g);
    }

    public void stop() {
        if (contactMeter.isRunning()) {
            contactMeter.stop();
        } else {
            powerMeter.stop();
        }
    }

    private int determineOutcome() {
        //this gives us a number between 0 and 100
        int result = findResult();
        //Manipulate result to 0 -4 ;
        //0 - out
        //1- single
        //2 - double
        //3- triple
        //4 - HR
        if (result ==100) return 4;
        else if (result>97) return 3;
        else if (result > 92) return 2;
        else if (result > 80) return 1;
        else return 0;
    }

    private int findResult() {
        //this is where we determine how we want contact and power to be handled
        //turn this into one number (0-100) to use in getResult (that returns 0 to 4 outcome)
        //contactResult and powerResult are (50-150).
        contactResult = calculateConRes();
        powerResult = calculatePowRes();
        int result;
        double contactWeight = .7;
        double powerWeight = .3;
        //this will be in between 50 and 150
        double preresult = (contactResult*contactWeight) + (powerResult*powerWeight);
        //to turn it into 0-100, we can divide it by max number (150) to find how close
        //user was to doing best they could
        result = (int)((preresult/125) * 100);
        if (result>100){
            result = 100;
        }
        //Use weights and results to come up with new result, 0-100
        return result;
    }

    private int calculateConRes(){
        //Using the rating and meter result it will return an update contact result
        int meterResult = contactResult;
        int contactRating = player.contactRating;
        //for a contact rating which is between 0 and 100
        //the average is 50
        //this gives us how far it is from the mean (positive or negative)
        double multiplier =  1 + ((double) (contactRating-50)/100.0);
        contactResult = (int) (meterResult * multiplier);
        //this returns a value between 50 and 150
        return contactResult;
    }

    private int calculatePowRes(){
        //Using the rating and meter result it will return an update power result
        int meterResult = powerResult;
        int powerRating = player.powerRating;
        //find multiplier by finding how under or above average (50) the powerResult is
        double multiplier = 1 + ((double) (powerRating-50)/100.0);
        powerResult = (int)(meterResult * multiplier);
        //this returns a value between 50 and 150
        return powerResult;
    }

}
