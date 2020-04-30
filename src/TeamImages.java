/**
 * All of the images that appear on the buttons in the loading screen
 * are drawn in this class. They are all referenced from a static context.
 **/

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class TeamImages {
    static BufferedImage[] teamImages = new BufferedImage[] {
            mountains(), reeds(), barves(), swordfish(), cubbies(),
            siblings(), redSocks(), americans(), astronauts(), angles()
    };

    public static BufferedImage barves() {
        BufferedImage barves = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = barves.createGraphics();
        g.setColor(new Color (0,0,102));
        g.fillRect(0, 0, barves.getWidth(), barves.getHeight());
        g.setColor(new Color(120, 0,0));
        g.fill(new RoundRectangle2D.Double(80,360, 640, 80, 80, 80)); //Stick
        g.fillPolygon(new int[]{500,700, 600}, new int[]{500,500,200}, 3); //Triangle
        g.setColor(Color.black);
        g.fill(new RoundRectangle2D.Double(90,370, 620, 60, 60, 60));
        g.setColor(new Color(120, 0,0));
        g.fill(new RoundRectangle2D.Double(100,380, 600, 40, 50, 50));

        g.setColor(Color.black);
        g.fillPolygon(new int[]{510,690, 600}, new int[]{490,490,220}, 3);
        g.setColor(new Color(120, 0,0));
        g.fillPolygon(new int[]{520,680, 600}, new int[]{480,480,240}, 3);

        g.fill(new RoundRectangle2D.Double(100,380, 600, 40, 50, 50));

        g.setColor(new Color(179, 140, 0));
        g.setStroke(new BasicStroke(5));
        g.drawArc(450, 360, 10,80, 280, 160);
        g.drawArc(470, 360, 10,80, 280, 160);
        g.drawArc(490, 360, 10,80, 280, 160);

        g.setColor(new Color(120, 0,0));
        g.setFont(new Font("TimesRoman", Font.ITALIC | Font.BOLD, 100));
        g.drawString("Barves", 170, 352);

        return barves;
    }

    public static BufferedImage cubbies() {
        BufferedImage cubbies = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = cubbies.createGraphics();
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, cubbies.getWidth(), cubbies.getHeight());
        g.setPaint(new Color(0, 184, 255));
        g.fillRect(0,225,800,350);
        g.setPaint(Color.blue.darker());
        g.fillRect(0,250,800,300);

        Display.writeText("CUBBIES", g, 0, 800, 300, 525, Color.red.darker(), "Impact");
        return cubbies;
    }

    public static BufferedImage americans() {
        BufferedImage americans = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = americans.createGraphics();
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, americans.getWidth(), americans.getHeight());
        g.setPaint(Color.red.darker());
        g.setStroke(new BasicStroke(40));
        g.drawOval(200,200,400,400);
        g.drawArc(475, -100, 1000,1000,161, 38);
        g.drawArc(-675, -100, 1000,1000,341, 38);

        Font font = new Font("TimesRoman", Font.ITALIC, 95);
        g.setFont(font);
        g.setPaint(Color.blue.darker().darker());
        g.drawString("Americans", 197, 435);
        return americans;
    }

    public static BufferedImage reeds() {
        BufferedImage reeds = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = reeds.createGraphics();
        g.setColor(Color.red.darker());
        g.fillRect(0, 0, reeds.getWidth(), reeds.getHeight());
        g.setStroke(new BasicStroke(40));
        g.setPaint(Color.BLACK);
        g.drawOval(210,310, 400, 200);
        g.setPaint(Color.WHITE);
        g.drawOval(200,300, 400, 200);
        g.setColor(Color.red.darker());
        g.fillRect(400,350, 400,100);
        g.setPaint(Color.BLACK);

        Font font = new Font("Impact", Font.PLAIN, 95);
        g.setFont(font);
        g.drawString("REEDS", 280, 455);
        g.setPaint(Color.WHITE);
        g.drawString("REEDS", 280, 445);
        return reeds;
    }

    public static BufferedImage siblings() {
        BufferedImage siblings = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = siblings.createGraphics();
        g.setColor(Color.blue.darker());
        g.fillRect(0, 0, siblings.getWidth(), siblings.getHeight());
        g.setPaint(Color.red.darker());
        g.setStroke(new BasicStroke(80));
        g.drawLine(250,200,400,450);
        g.drawLine(550,200,400,450);
        g.setPaint(Color.red.darker().darker());
        g.drawLine(120, 80, 680, 80);
        g.drawLine(120, 720, 680, 720);
        g.drawLine(120, 80, 120, 400);
        g.drawLine(680, 400, 680, 720);
        g.drawLine(120,400,680,400);
        g.setPaint(Color.red.darker());
        g.drawLine(250,200,250,600);
        g.drawLine(550,200,550,600);
        return siblings;
    }

    public static BufferedImage angles() {
        BufferedImage angles = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = angles.createGraphics();
        g.setColor(Color.red.darker().darker());
        g.fillRect(0, 0, angles.getWidth(), angles.getHeight());
        g.setPaint(Color.black);
        g.setStroke(new BasicStroke(10));
        g.drawLine(200,600,600,600);
        g.drawLine(200,200,200,600);
        g.drawArc(50,450,300,300,10,70);

        g.fillPolygon(new int[]{310,370,340}, new int[]{550,550,600},3);
        g.fillPolygon(new int[]{250,250,200}, new int[]{430,490,460},3);
        return angles;
    }

    public static BufferedImage swordfish() {
        BufferedImage swordfish = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = swordfish.createGraphics();
        g.setColor(Color.blue.darker().darker());
        g.fillRect(0, 0, swordfish.getWidth(), swordfish.getHeight());
        GradientPaint yellowBlue = new GradientPaint(200,200,Color.yellow, 200, 400, Color.blue);
        g.setPaint(yellowBlue);
        g.fill(new Ellipse2D.Double(150,360, 450,80));
        g.fillPolygon(new int[]{150, 250, 90}, new int[]{400,400,520},3);//Back Fins
        g.fillPolygon(new int[]{150, 250, 80}, new int[]{400,400,280},3);
        g.fillPolygon(new int[]{400, 520, 370}, new int[]{400,400,210},3);//Top Fins
        g.fillPolygon(new int[]{270, 320, 270}, new int[]{400,400,300},3);
        g.fillPolygon(new int[]{350, 490, 290}, new int[]{400,400,570},3);//Bottom fin
        //Eye
        g.setPaint(Color.black);
        g.fillOval(560,380,20,20);
        g.setPaint(Color.white);
        g.fillOval(564,386,6,6);
        //Gills
        g.setPaint(Color.black);
        g.setStroke(new BasicStroke(3));
        g.drawArc(400, 380, 10,40, 280, 160);
        g.drawArc(420, 380, 10,40, 280, 160);
        g.drawArc(440, 380, 10,40, 280, 160);

        return swordfish;
    }

    public static BufferedImage astronauts() {
        BufferedImage astronauts = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = astronauts.createGraphics();
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
        Font f = new Font("Boulder", Font.BOLD, 650);
        g.setFont(f);
        g.setColor(Color.BLACK);
        g.drawString("A", 190, 600);
        return astronauts;
    }

    public static BufferedImage redSocks() {
        BufferedImage redSocks = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = redSocks.createGraphics();
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
        return redSocks;
    }

    public static BufferedImage mountains() {
        BufferedImage mountains = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = mountains.createGraphics();
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
        Font f = new Font("Boulder", Font.BOLD, 300);
        g.setFont(f);
        g.setColor(new Color(102, 0, 153));
        g.drawString("C", 70, 240);
        g.drawString("M", 550, 240);
            return mountains;
    }
}
