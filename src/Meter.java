import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

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
    public static boolean stopped;

    public Meter(int x, int y, Game g) {
        WIDTH = 300;
        HEIGHT = 50;
        meterLocation = WIDTH/2;
        meterSpeed = 1;
        speed = 2;
        stopped = false;
        running = false;

        xPosition = x;
        yPosition = y;
        game = g;

        //Create the color gradient for the meter
        Color[] colors = {Color.red, Color.yellow, Color.green, Color.yellow, Color.red};
        float[] fractions = {0.02f, 0.3f, 0.5f, 0.7f, 0.98f};
        color = new LinearGradientPaint(xPosition,yPosition, xPosition+WIDTH,yPosition, fractions, colors);
    }

    public void setStopped(boolean s) {
        stopped = s;
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

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
        //Create Graphics2D for color gradient
        Graphics2D g = (Graphics2D) gOri;

        //Draw the meter
        g.setPaint(color);
        g.fillRect(xPosition, yPosition, WIDTH, HEIGHT);

        //Draw the bar that is moving back and forth
        g.setColor(Color.black);
        g.setStroke(new BasicStroke(3)); //Sets thickness of line
        g.drawLine(xPosition + meterLocation, yPosition, xPosition + meterLocation, yPosition + HEIGHT);

    }

    public int runMeter() {
        running = true;
        while (running) {
            update();
            game.repaint();
            try {
                Thread.sleep(1000/(WIDTH*speed)); //200 can be changed to change speed... lower it is slower meter goes
            } catch (InterruptedException ignored) {}
        }
        return 100 - (Math.abs(meterLocation - WIDTH/2) / (WIDTH/2) * 100);
    }
}
