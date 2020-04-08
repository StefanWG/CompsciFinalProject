import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

abstract class Meter extends JComponent implements KeyListener {
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

    abstract void update();
    abstract void draw(Graphics gOri);
    abstract int runMeter();
}
