import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class TeamImages {
    public static BufferedImage astronauts() {
        BufferedImage character = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = character.createGraphics();
        g.setColor(Color.BLUE.darker());
        g.fillRect(0, 0, 1600, 1600);
        g.setColor(Color.ORANGE);
        g.setStroke(new BasicStroke(10));
        g.drawOval(0, 0, 800, 800);
        g.fillOval(200, 175, 450, 450);
        g.setStroke(new BasicStroke(50));
        g.drawLine(425, 200, 425, 50);
        g.drawLine(425, 200, 425, 750);
        g.drawLine(425, 400, 50, 400);
        g.drawLine(425, 400, 750, 400);
        g.drawLine(400, 400, 150, 150);
        g.drawLine(400, 400, 650, 150);
        g.drawLine(400, 400, 150, 650);
        g.drawLine(400, 400, 650, 650);
        Font f = new Font("Boulder", 1, 650);
        g.setFont(f);
        g.setColor(Color.BLACK);
        g.drawString("A", 190, 600);
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

    public static BufferedImage redSocks() {
        BufferedImage character3 = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = character3.createGraphics();
        g.setColor(Color.RED);
        g.fillOval(100, 20, 300, 780);
        g.fillRect(150, 20, 200, 100);
        g.fillOval(145, 550, 650, 250);
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(15));
        for (int i = 0; i < 160; i = i + 30) {
            g.drawLine(170 + i, 20, 170 + i, 120);
        }
        g.fillOval(185, 680, 150, 100);
        g.fillOval(680, 610, 100, 130);
        return character3;
    }

    public static BufferedImage Mountains() {
        BufferedImage character3 = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = character3.createGraphics();
        Random rand = new Random();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1600, 1600);
        g.setColor(Color.GRAY);
        for (int i = 0; i < 800; i += 10){
            int width = rand.nextInt(170);
            int height = rand.nextInt(170);
            g.fillOval(i, 650-height, width, height);
        }
        for(int x = 100; x<700; x+=10){
            int width = rand.nextInt(170);
            int height = rand.nextInt(170);
            g.fillOval(x, 550-height, width, height);
        }
        for(int z = 200; z<600; z+=10){
            int width = rand.nextInt(170);
            int height = rand.nextInt(170);
            g.fillOval(z, 450-height, width, height);
        }
        for(int y = 300; y<500; y+=10){
            int width = rand.nextInt(170);
            int height = rand.nextInt(170);
            g.fillOval(y, 350-height, width, height);
        }
        for(int x = 390; x<410; x+=1){
            int width = rand.nextInt(170);
            int height = rand.nextInt(170);
            g.fillOval(x, 250-height, width, height);
        }
        g.setColor(Color.GREEN.darker());
        g.fillRect(0, 600, 1000, 400);
        Font f = new Font("Boulder", 1, 300);
        g.setFont(f);
        g.setColor(new Color(102, 0, 153));
        g.drawString("C", 70, 240);
        g.drawString("M", 550, 240);
            return character3;
    }
}
