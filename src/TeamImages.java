import java.awt.*;
import java.awt.image.BufferedImage;

public class TeamImages {
    public static BufferedImage astronauts() {
        BufferedImage character = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = character.createGraphics();
        g.setColor(Color.red);
        g.fillRect(270, 450, 70, 300);
        g.fillRect(370, 450, 70, 300);
        g.setColor(Color.blue.brighter());
        g.fillRect(230, 120, 225, 350);
        g.setColor(Color.red.darker());
        g.fillRect(300, 100, 100, 100);
        g.setColor(Color.blue);
        g.fillRect(310, 125, 25, 25);
        g.fillRect(360, 125, 25, 25);

        return character;
    }

    public static BufferedImage characterStefan() {
        BufferedImage character2 = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = character2.createGraphics();
        g.setColor(Color.blue);
        g.fillOval(250, 400, 150, 300);
        g.fillOval(400, 400, 150, 300);
        g.setStroke(new BasicStroke(15));
        g.setColor(Color.black);
        g.drawOval(250, 400, 150, 300);
        g.drawOval(400, 400, 150, 300);


        g.setColor(Color.BLUE.darker());
        g.fillOval(225, 120, 350, 400);
        g.setColor(Color.black);
        g.drawOval(225, 120, 350, 400);

        g.setColor(Color.BLUE);
        g.fillOval(325, 50, 150, 100);
        g.setColor(Color.black);
        g.drawOval(325, 50, 150, 100);

        g.setStroke(new BasicStroke(4));
        for (int i = 0; i < 4; i++) {
            g.setColor(Color.cyan);
            g.fillOval(380, 200 + i * 60, 40, 40);
            g.setColor(Color.black);
            g.drawOval(380, 200 + i * 60, 40, 40);
        }

        g.setColor(Color.green.darker());
        g.fillOval(360, 85, 30, 30);
        g.fillOval(410, 85, 30, 30);
        g.setColor(Color.black);
        g.drawOval(360, 85, 30, 30);
        g.drawOval(410, 85, 30, 30);

        return character2;
    }

    public static BufferedImage characterRohil() {
        BufferedImage character3 = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = character3.createGraphics();
        //to do make a character for rohil
        return character3;
    }
}
