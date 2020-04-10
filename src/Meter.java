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
    public static boolean stopped;
    int result;

    public Meter(int x, int y, Game g) {
        WIDTH = 300;
        HEIGHT = 50;
        meterLocation = WIDTH/2;
        meterSpeed = 1;
        speed = 2;
        //TODO edit stopped
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
        done = true;
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

        if (done) {
            writeText(String.valueOf(result), g, xPosition, xPosition+WIDTH, yPosition + HEIGHT +15,  yPosition + HEIGHT +65);
        }
    }

    public void writeText(String str, Graphics2D g, int x1, int x2, int y1, int y2) {
        Font font = new Font("Impact", Font.PLAIN, 50);
        TextLayout textLayout = new TextLayout(str, font, g.getFontRenderContext());
        float y = (y1 + y2 + textLayout.getAscent()) / 2 - textLayout.getAscent()/8;
        float x = (x1 + x2 + textLayout.getVisibleAdvance()) / 2 - textLayout.getVisibleAdvance();
        g.setColor(Color.darkGray);
        textLayout.draw(g, x+3, y+3);
        g.setColor(Color.black);
        textLayout.draw(g, x, y);
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
        result = 100 - (int) (Math.abs((double) (meterLocation - WIDTH/2) / (double) (WIDTH/2) * 100));
        return result;
    }
}
