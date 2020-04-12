import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Display {

    public static BufferedImage outcomeText(int outcome) {
        BufferedImage image = new BufferedImage(800,150,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2D = image.createGraphics();
        String str;
        switch (outcome) {
            case 0: str = "OUT"; break;
            case 1: str = "SINGLE"; break;
            case 2: str = "DOUBLE"; break;
            case 3: str = "TRIPLE"; break;
            case 4: str = "HOME RUN"; break;
            case 5: str = "WALK"; break;
            default: str = " "; break;
        }
        writeText(str, g2D, 0,800,0,150,Color.orange);
        return image;
    }

    public static BufferedImage drawScoreboard(Scoreboard scoreboard) {
        BufferedImage image = new BufferedImage ( 800, 160, BufferedImage.TYPE_INT_ARGB );
        final Graphics2D g = image.createGraphics ();
        Color textColor = new Color(0,100,0);
        float[] fracs = {.25f,.75f};
        Color[] colors = {new Color(0,102,0), new Color(0,179,0)};
        LinearGradientPaint grass = new LinearGradientPaint(0,0, 800,800, fracs, colors, MultipleGradientPaint.CycleMethod.REPEAT);

        g.setColor(Color.darkGray);
        g.fill(new RoundRectangle2D.Double(0,0,800,160, 50, 50));
        g.setColor(Color.BLACK);
        g.fill(new RoundRectangle2D.Double(10,60,80,40, 20, 20)); //AwayName
        g.fill(new RoundRectangle2D.Double(10,110,80,40, 20, 20)); //HomeName

        //TODO CHANGE TO TEAM NAME OR INTIALS

        writeText("AWA", g,10, 90, 60,100,textColor);
        writeText("HOM", g,10, 90, 110, 150,textColor);

        for (int i = 0; i < 9; i++) {
            g.setColor(Color.black);
            g.fill(new RoundRectangle2D.Double(100+i*50,10,40,40, 20, 20)); //Inning
            g.fill(new RoundRectangle2D.Double(100+i*50,60,40,40, 20, 20)); //AwayRuns
            g.fill(new RoundRectangle2D.Double(100+i*50,110,40,40, 20, 20)); //HomeRuns

            writeText(String.valueOf(i+1),g,100+i*50, 140+i*50, 10,50,textColor); //Innings
            try {
                Color c;
                if (i == scoreboard.halfInning/2 && scoreboard.halfInning%2 == 1) c = Color.green;
                else c = textColor;
                writeText(String.valueOf(scoreboard.awayRunsInning.get(i)),g,100+i*50, 140+i*50, 60,100,c); //AwayRuns
            } catch (IndexOutOfBoundsException e) {
                writeText(" ",g,100+i*50, 140+i*50, 60,100,textColor); //AwayRuns
            }
            try {
                Color c;
                if (i == (scoreboard.halfInning - 1)/2 && scoreboard.halfInning%2 == 0) c = Color.green;
                else c = textColor;
                writeText(String.valueOf(scoreboard.homeRunsInning.get(i)),g,100+i*50, 140+i*50, 110,150,c); //AwayRuns
            } catch (IndexOutOfBoundsException e) {
                writeText(" ",g,100+i*50, 140+i*50, 100,150,textColor); //AwayRuns
            }
        }

        g.setColor(Color.black);
        g.fill(new RoundRectangle2D.Double(550,10,40,40, 20, 20)); //Inning
        g.fill(new RoundRectangle2D.Double(550,60,40,40, 20, 20)); //A
        g.fill(new RoundRectangle2D.Double(550,110,40,40, 20, 20)); //H
        writeText("R", g, 550, 590, 10, 50, Color.green);
        writeText(String.valueOf(scoreboard.awayRuns), g, 550, 590, 60, 100, Color.green);
        writeText(String.valueOf(scoreboard.homeRuns), g, 550, 590, 110, 150, Color.green);

        g.setColor(Color.black);
        g.fill(new RoundRectangle2D.Double(600,10,40,40, 20, 20)); //Inning
        g.fill(new RoundRectangle2D.Double(600,60,40,40, 20, 20)); //A
        g.fill(new RoundRectangle2D.Double(600,110,40,40, 20, 20)); //H
        writeText("H", g, 600, 640, 10, 50, Color.green);
        writeText(String.valueOf(scoreboard.awayHits), g, 600, 640, 60, 100, Color.green);
        writeText(String.valueOf(scoreboard.homeHits), g, 600, 640, 110, 150, Color.green);



        BufferedImage fieldView = new BufferedImage(400,400,BufferedImage.TYPE_INT_ARGB);
        Graphics2D f = fieldView.createGraphics();

        f.setColor(Color.black);
        drawRect(f, new Point(200,0), new Point(400,200), new Point(200,400), new Point(0,200));
        f.setPaint(grass);
        drawRect(f, new Point(200,20), new Point(380,200), new Point(200,380), new Point(20,200));

        if (scoreboard.bases[0]) f.setColor(Color.red);
        else f.setColor(Color.white);
        drawRect(f, new Point(400,200), new Point(360,160), new Point(320,200), new Point(360,240));

        if (scoreboard.bases[1]) f.setColor(Color.red);
        else f.setColor(Color.white);
        drawRect(f, new Point(200,0), new Point(160,40), new Point(200,80), new Point(240,40));

        if (scoreboard.bases[2]) f.setColor(Color.red);
        else f.setColor(Color.white);
        drawRect(f, new Point(0,200), new Point(40,160), new Point(80,200), new Point(40,240));

        f.setColor(Color.white);
        drawRect(f, new Point(200,400), new Point(240,360), new Point(200,320), new Point(160,360));

        writeText(String.valueOf(scoreboard.outs), f, 100,300,100,300, Color.red);

        fieldView = resize(fieldView,140,140);

        g.drawImage(fieldView,650,10,null);
        image = makeTransparent(Color.lightGray, image);
        return image;

    }

    public static void writeText(String str, Graphics2D g, int x1, int x2, int y1, int y2, Color color) {
        int fontSize = (int) ((y2-y1) * 0.8);
        Font font = new Font("Impact", Font.PLAIN, fontSize);
        TextLayout textLayout = new TextLayout(str, font, g.getFontRenderContext());
        float y = (y1 + y2 + textLayout.getAscent()) / 2 - textLayout.getAscent()/8;
        float x = (x1 + x2 + textLayout.getVisibleAdvance()) / 2 - textLayout.getVisibleAdvance();
        g.setColor(color.darker());
        textLayout.draw(g, x+3, y+3);
        g.setColor(color);
        textLayout.draw(g, x, y);
    }

    public static void drawRect(Graphics g, Point p1, Point p2, Point p3, Point p4) {
        int[] tXL = {p1.x, p3.x,  p4.x};
        int[] tYL = {p1.y, p3.y,  p4.y};
        g.fillPolygon(tXL,tYL,3);

        int[] tXR = {p1.x, p2.x,  p3.x};
        int[] tYR = {p1.y, p2.y,  p3.y};
        g.fillPolygon(tXR,tYR,3);
    }

    public static BufferedImage makeTransparent(Color c, BufferedImage image) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        int transparent = new Color(0,0,0,0).getRGB();
        int original = c.getRGB();

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int current = image.getRGB(i,j);
                if (current == original) {
                    newImage.setRGB(i,j,transparent);
                } else {
                    newImage.setRGB(i,j, current);
                }
            }
        }

        return newImage;
    }

    public static BufferedImage grassTile() {
        BufferedImage tile = new BufferedImage ( 800, 800, BufferedImage.TYPE_INT_ARGB );
        final Graphics2D g = tile.createGraphics ();
        g.setPaint(new Color(0,100,0));
        g.fillRect(0,0,800,800);
        Random rand = new Random();
//        for (int i = 0; i < 800; i++) {
//            for (int j = 0; j < 800; j++) {
//                int green = rand.nextInt(75) + 40;
//                tile.setRGB(i,j, new Color(0, green, 0).getRGB());
//            }
//        }
        for (int i = 0; i < 300000;i++) {
            int green = rand.nextInt(75) + 40;
            g.setPaint(new Color (0,green,0));
            g.setStroke(new BasicStroke(rand.nextInt(2)+1));
            int x1 = rand.nextInt(1000);
            int y1 = rand.nextInt(1000);
            int x2 = x1 + rand.nextInt(4) - 2;
            int y2 = y1 - rand.nextInt(15) + 10;
            g.drawLine(x1,y1,x2, y2);
        }
        return tile;
    }

    public static BufferedImage dirt() {
        BufferedImage tile = new BufferedImage ( 800, 800, BufferedImage.TYPE_INT_ARGB );
        final Graphics2D g = tile.createGraphics ();
        Random rand = new Random();
        for (int i = 0; i < 800; i++) {
            for (int j = 0; j < 800; j++) {
                switch(rand.nextInt(5)){
                    case 0: tile.setRGB(i,j, new Color(138, 77, 0).getRGB()); break;
                    case 1: tile.setRGB(i,j, new Color(162, 92, 0).getRGB()); break;
                    case 2: tile.setRGB(i,j, new Color(189, 107, 0).getRGB()); break;
                    case 3: tile.setRGB(i,j, new Color(214, 122, 0).getRGB()); break;
                    case 4: tile.setRGB(i,j, new Color(240, 138, 0).getRGB()); break;
                }
            }
        }
        return tile;
    }
    //TODO improve drawing for homeplate and fence
    public static BufferedImage drawField() {
        BufferedImage image = new BufferedImage ( 800, 800, BufferedImage.TYPE_INT_ARGB );
        final Graphics2D g = image.createGraphics ();

        float[] fracs = {.25f,.75f};
        Color[] colors = {new Color(0,102,0), new Color(0,179,0)};
        LinearGradientPaint grass = new LinearGradientPaint(0,0, 0,800, fracs, colors, MultipleGradientPaint.CycleMethod.REPEAT);

        //Background
        g.drawImage(grassTile(),0,0,null);
        //Outside the field
        g.setPaint(Color.lightGray);
        g.setStroke(new BasicStroke(5));
        int y = 95;
        while (y > -100) {
            g.drawArc(-1400,y,3600,3600,0,360);
            y -= 5;
        }
        //OUTFIELD WALL
        g.setColor(Color.red);
        g.setStroke(new BasicStroke(5));
        g.drawArc(-1400,90,3600,3600,0,360);
        g.setColor(new Color(105,105,105));
        g.setStroke(new BasicStroke(15));
        g.drawArc(-1400,97,3600,3600,0,360);

        //HOME PLATE DIRT AND BASELINES
        Color infield = new Color(181, 107, 23);
        g.setPaint(infield);
        g.fillOval(-200, 350, 1200,800);
        g.fillPolygon(new int[]{240,0,0}, new int[]{500, 210, 500},3);
        g.fillPolygon(new int[]{560,800,800}, new int[]{500, 210, 500},3);
        //INFIELD DIRT
        g.setStroke(new BasicStroke(4));
        g.drawArc(330,116,140,10,20,140);
        g.setStroke(new BasicStroke(6));
        g.drawArc(-800,116,760+1600,1600, 90, 85);
        g.drawArc(-800,116,760+1600,1800, 90, 85);
        g.drawArc(-800,116,760+1600,2000, 90, 85);
        g.drawArc(-800,116,760+1600,2200, 90, 85);

        g.drawArc(-760,116,760+1600,1600, 5, 85);
        g.drawArc(-760,116,760+1600,1800, 5, 85);
        g.drawArc(-760,116,760+1600,2000, 5, 85);
        g.drawArc(-760,116,760+1600,2200, 5, 85);




        //MOUND
        Color mound = new Color(158, 94, 21);
        g.setPaint(mound);
        g.fillOval(320,122,160,20);
        //FOUL LINES
        g.setPaint(new Color(255, 230, 204));
        g.setStroke(new BasicStroke(5));
        g.drawLine(0,260,190,500);
        g.drawLine(800,260,610,500);
        //BATTERS BOXES
        g.drawPolygon(new int[]{820,520,470,700}, new int[]{790,790,500,500},4);
        g.drawPolygon(new int[]{-20,280,330,100}, new int[]{790,790,500,500},4);
        //HOME PLATE AND PITCHING RUBBER
        g.setColor(Color.darkGray);
        g.fillPolygon(new int[]{346,446,449,396,343}, new int[]{554,554,584,614,584}, 5);
        g.setPaint(new Color(215, 193, 142));
        g.fillPolygon(new int[]{350,450,453,400,347}, new int[]{550,550,580,610,580}, 5);
        g.fillRect(380,127,40,2);



        BufferedImage dirt = dirt();

        //MAKE DIRT
        for (int i = 0; i < 800; i++) {
            for (int j = 0; j <800; j++) {
                if (image.getRGB(i,j) == infield.getRGB()) {
                    Color c = new Color(dirt.getRGB(i,j));
                    image.setRGB(i,j,c.darker().getRGB());
                } else if (image.getRGB(i,j) == mound.getRGB()) {
                    Color c = new Color(dirt.getRGB(i,j));
                    image.setRGB(i,j,c.darker().darker().getRGB());
                }
            }
        }



        image = makeTransparent(Color.lightGray, image);
        return image;

    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}

class Ball {
    int rotation = 0;

    public void update() {
        rotation += 1;
    }


    public BufferedImage draw(int diameter) {
        BufferedImage seams = new BufferedImage(200,628,BufferedImage.TYPE_INT_ARGB);
        Graphics2D s = seams.createGraphics();
        s.setPaint(Color.black);
        s.fillRect(0,0,200,628);
        s.setPaint(Color.red);
        s.setStroke(new BasicStroke(5));
        s.drawArc(0,0,200,200, 330,240);
        s.drawArc(0,240,200,200,150,240);
        s.drawArc(208-40, 120-40,280,280,150,60);
        s.drawArc(-208-40, 120-40,280,280,330,60);

        BufferedImage image = new BufferedImage(220,220,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.black);
        g.fillRect(0,0,220,220);
        g.setPaint(Color.lightGray);
        g.fillOval(0,0,220,220);
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                Color color = new Color(seams.getRGB(i,(j+rotation)%628));
                Color current = new Color(image.getRGB(i+10,220 - j - 10));
                if (!color.equals(Color.black) && current.equals(Color.lightGray)) image.setRGB(i+10,220 - j - 10,color.getRGB());
            }
        }
        image = Display.makeTransparent(Color.black,image);
        image = Display.resize(image, diameter,diameter);


        //TODO modify size so it looks like coming forward

        return image;
    }
//    Code for running ball
//    public void runBall() {
//        DIAMETER = 20;
//        int count = 1;
//        yPosition = 124;
//        xPosition = 400;
//        while (true) {
//            DIAMETER += (double) 1/ (double) count;
//            yPosition += .5;
//            count++;
//            ball.update();
//            repaint();
//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException ignored) {}
//        }
//    }
//    Code for drawing ball and shadow
//    BufferedImage ballImage = ball.draw((int) DIAMETER);
//    int realX = xPosition - ballImage.getWidth()/2;
//    int realY = (int) yPosition - ballImage.getHeight()/2;
//
//    g.drawImage(ballImage,realX, realY,null);
//    g.setColor(new Color(0,0,0, 80));
//    g.fillOval(realX-80, realY+40, ballImage.getWidth(),ballImage.getHeight());
}
