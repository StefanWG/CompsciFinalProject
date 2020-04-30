/**
 * This class is used for when a user is at bat. It has a bar that is running back
 * and forth across the meter, and when stopped, determines the relative location
 * so that the at bat result can be calculated. It is called from the AtBat class.
 **/

import java.awt.*;
import java.awt.font.TextLayout;

public class Meter {
    Game game;
    int WIDTH;
    int HEIGHT;
    LinearGradientPaint color;
    int meterLocation;
    int meterSpeed;
    int speed;
    int xPosition;
    int yPosition;
    boolean running;
    boolean done;
    int result;
    String title;

    public Meter(int x, int y, Game g, int rating, String t) {
        WIDTH = 300;
        HEIGHT = 50;
        meterLocation = WIDTH/2;
        meterSpeed = 1;
        speed = 2;
        running = false;

        xPosition = x;
        yPosition = y;
        game = g;
        title = t;

        /** Create the color gradient for the meter */
        float single = (float) 11250/(float) (rating + 50) / 200;
        single = single - single * .2f;
        if (single >= 0.5) single = .49f;

        Color[] colors = {Color.red, Color.yellow, Color.green, Color.yellow, Color.red};
        float[] fractions = {0.02f, single, 0.5f, 1-single, 0.98f};
        color = new LinearGradientPaint(xPosition,yPosition, xPosition+WIDTH,yPosition, fractions, colors);
    }
/** This method is called when the space bar is pressed*/
    public void stop() {
        if (running) {
            running = false;
            done = true;
        }
    }

    public boolean isRunning() {
        return running;
    }
/** This is the code that moves the black bar within the meter graphic allowing it to move back and forth*/
    void update() {
        meterLocation += meterSpeed;
        if (meterLocation > WIDTH) {
            meterLocation = WIDTH;
            meterSpeed = -meterSpeed;
        } else if (meterLocation < 0) {
            meterLocation = 0;
            meterSpeed = -meterSpeed;
        }
    }

    void draw(Graphics gOri) {
        /** Create Graphics2D for color gradient */
        Graphics2D g = (Graphics2D) gOri;

        /** Draw the meter */
        g.setPaint(color);
        g.fillRect(xPosition, yPosition, WIDTH, HEIGHT);

        /** Draw the bar that is moving back and forth */
        g.setColor(Color.black);
        g.setStroke(new BasicStroke(3)); //Sets thickness of line
        g.drawLine(xPosition + meterLocation, yPosition, xPosition + meterLocation, yPosition + HEIGHT);
        writeText(title, g, xPosition-20, xPosition-70, yPosition,  yPosition + HEIGHT);

        if (done) { writeText(String.valueOf(result), g, xPosition+WIDTH+20, xPosition+WIDTH+70, yPosition,  yPosition + HEIGHT); }
    }


   /** This centers the text given the passed in x and y values and the string*/
    public static void writeText(String str, Graphics2D g, int x1, int x2, int y1, int y2) {
        Font font = new Font("Impact", Font.PLAIN, 50);
        TextLayout textLayout = new TextLayout(str, font, g.getFontRenderContext());
        float y = (y1 + y2 + textLayout.getAscent()) / 2 - textLayout.getAscent()/8;
        float x = (x1 + x2 + textLayout.getVisibleAdvance()) / 2 - textLayout.getVisibleAdvance();
        g.setColor(Color.darkGray);
        textLayout.draw(g, x+3, y+3);
        g.setColor(Color.black);
        textLayout.draw(g, x, y);
    }

/** Runs the meter and return the result of the user input */
/** The thread.sleep is the speed of the meter and can be adjusted */
    public int runMeter() {
        running = true;
        while (running) {
            update();
            game.repaint();
            try {
                Thread.sleep(1000/(WIDTH*speed));
            } catch (InterruptedException ignored) {}
        }
        result = 100 - (int) (Math.abs((double) (meterLocation - WIDTH/2) / (double) (WIDTH/2) * 100));
        return result;
    }
}
