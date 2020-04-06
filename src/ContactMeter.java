import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ContactMeter extends Meter {

    public ContactMeter(int x, int y, Game g) {
        WIDTH = 300;
        HEIGHT = 50;
        meterLocation = 0;
        meterSpeed = 1;
        stopped = false;

        xPosition = x;
        yPosition = y;
        game = g;

        addKeyListener(this);

        //Create the color gradient for the meter
        Color[] colors = {Color.red, Color.yellow, Color.green, Color.yellow, Color.red};
        float[] fractions = {0.02f, 0.3f, 0.5f, 0.7f, 0.98f};
        color = new LinearGradientPaint(xPosition,yPosition, xPosition+WIDTH,yPosition, fractions, colors);
    }

    @Override
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

    @Override
    void draw(Graphics gOri) {
        //Create Graphics2D for color gradient
        Graphics2D g = (Graphics2D) gOri;

        //Draw the meter
        g.setPaint(color);
        g.fillRect(xPosition, yPosition, WIDTH, HEIGHT);

        //Draw the bar that is moving back and forth
        g.setColor(Color.black);
        g.setStroke(new BasicStroke(3)); //Sets thickness of line
        g.drawLine(xPosition + meterLocation, yPosition - 10, xPosition + meterLocation, yPosition + HEIGHT + 10);

    }

    @Override
    public int runMeter() {
        while (!stopped) {
            update();
            game.repaint();
            try {
                Thread.sleep(1000/200); //200 can be changed to change speed... lower it is slower meter goes
            } catch (InterruptedException ignored) {}
        }
        return 100 - (Math.abs(meterLocation - WIDTH/2) / (WIDTH/2) * 100);
    }

   @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        System.out.print(keyEvent.getKeyChar());
        if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
            stopped = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }
}
