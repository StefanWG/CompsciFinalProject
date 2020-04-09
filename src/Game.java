import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.font.TextLayout;
import java.awt.geom.Arc2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class Game extends JPanel {
    public Player player;
    final int WIDTH = 1000;
    final int HEIGHT = 1000;
    final int MAX_INNINGS = 18;
    BufferedImage fieldDrawing = drawField();
    Scoreboard scoreboard;
    AtBat atBat;

    public Game() {
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setUpKeyBindings();
    }

    public void runGame() {
        scoreboard = new Scoreboard(MAX_INNINGS);
        //Sims half inning when
        while (scoreboard.halfInning <= scoreboard.maxInnings || scoreboard.awayRuns == scoreboard.homeRuns) {
            while (scoreboard.outs < 3) {
                atBat = new AtBat(scoreboard.halfInning, this);
                int result = atBat.runAtBat();
                scoreboard.updateBases(result);
            }
            scoreboard.newInning();
        }
    }

    public void pickPlayer() {
        //TODO Code for picking player Josh doing this for now
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(drawScoreboard(),50,400,null);
        g.drawImage(fieldDrawing,500,300,null);
        if (atBat != null) atBat.draw(g);
    }

    public BufferedImage drawField() {
        int xOri = 250;
        int yOri = 500;

        BufferedImage image = new BufferedImage ( 500, 550, BufferedImage.TYPE_INT_ARGB );
        final Graphics2D g = image.createGraphics ();

        float[] fracs = {.25f,.75f};
        Color[] colors = {new Color(0,102,0), new Color(0,179,0)};
        LinearGradientPaint grass = new LinearGradientPaint(0,0, 800,800, fracs, colors, MultipleGradientPaint.CycleMethod.REPEAT);
        Color infield = new Color(153,77,0);
        //Background
        g.setColor(Color.lightGray);
        g.fillRect(0,0,800,800);
        //Warning Track
        g.setColor(infield);
        g.fill(new Arc2D.Float(xOri-230, yOri-190-230, 460, 460, 0, 360, Arc2D.PIE));
        //Outfield Grass
        g.setPaint(grass);
        g.fill(new Arc2D.Float(xOri-215, yOri-190-215, 430, 430, 0, 360, Arc2D.PIE));
        //Wall
        g.setColor(Color.darkGray);
        g.setStroke(new BasicStroke(3));
        g.draw(new Arc2D.Float(xOri-230, yOri-190-230, 460, 460, 0, 360, Arc2D.OPEN));
        g.setStroke(new BasicStroke(1));
        //Infield Dirt
        g.setColor(infield);
        g.fill(new Arc2D.Float(xOri-95, yOri-60-95, 190, 190, 0, 360, Arc2D.PIE));
        drawRect(g, new Point(xOri, yOri), new Point(xOri+64, yOri-64), new Point(xOri, yOri-128), new Point(xOri-64, yOri-64));
        //Infield Grass
        g.setPaint(grass);
        drawRect(g, new Point(xOri, yOri-6), new Point(xOri+64-6, yOri-64), new Point(xOri, yOri-128+6), new Point(xOri-64+6, yOri-64));
        //Clear sides
        g.setColor(Color.lightGray);
        int[] tXL = {xOri, xOri-225-10, xOri-225, xOri+225, xOri+225+10};
        int[] tYL = {yOri+10, yOri-225, yOri+50,yOri+50, yOri-225};
        g.fillPolygon(tXL,tYL,5);
        //Pitchers Mound and base areas
        g.setColor(infield);
        g.fill(new Arc2D.Float(xOri-9, yOri-60-9, 18, 18, 0, 360, Arc2D.PIE)); // Mound
        g.fill(new Arc2D.Float(xOri+64-13, yOri-64-13, 26, 26, 135, 90, Arc2D.PIE)); //1st Base
        g.fill(new Arc2D.Float(xOri-13, yOri-128-13, 26, 26, 225, 90, Arc2D.PIE)); // 2nd Base
        g.fill(new Arc2D.Float(xOri-64-13, yOri-64-13, 26, 26, 315, 90, Arc2D.PIE)); // 3rd Base
        g.fill(new Arc2D.Float(xOri-13, yOri-13, 25, 26, 0, 360, Arc2D.PIE)); //Home Plate
        //Foul Lines
        g.setColor(Color.white);
        g.setStroke(new BasicStroke(1));
        g.drawLine(xOri, yOri, xOri-225, yOri-225);
        g.drawLine(xOri, yOri, xOri+225, yOri-225);
        //Bases
        drawRect(g, new Point(xOri+64, yOri-64), new Point(xOri+64-3, yOri-64-3), new Point(xOri+64-6, yOri-64), new Point(xOri+64-3, yOri-64+3));
        drawRect(g, new Point(xOri, yOri-128), new Point(xOri-3, yOri-128+3), new Point(xOri, yOri-128+6), new Point(xOri+3, yOri-128+3));
        drawRect(g, new Point(xOri-64, yOri-64), new Point(xOri-64+3, yOri-64+3), new Point(xOri-64+6, yOri-64), new Point(xOri-64+3, yOri-64-3));
        g.fillRect(xOri-2,yOri-4,5,2);
        int[] tXR = {xOri, xOri+2, xOri-2};
        int[] tYR = {yOri, yOri-2, yOri-2};
        g.fillPolygon(tXR,tYR,3);
        g.fillRect(xOri-2,yOri-60-1,4,2);

        image = makeTransparent(Color.lightGray, image);
        return image;

    }

    public BufferedImage drawScoreboard() {
        BufferedImage image = new BufferedImage ( 800, 800, BufferedImage.TYPE_INT_ARGB );
        final Graphics2D g = image.createGraphics ();

        g.setColor(Color.darkGray);
        g.fill(new RoundRectangle2D.Double(0,0,400,360, 50, 50));
        //g.fillRect(200,200, 400,300);
        g.setColor(Color.BLACK);
        g.fill(new RoundRectangle2D.Double(25,25,75,75, 20, 20)); //Away
        g.fill(new RoundRectangle2D.Double(300,25,75,75, 20, 20)); //Home
        g.fill(new RoundRectangle2D.Double(150,25,100,100, 20, 20)); //Inning
        g.fill(new RoundRectangle2D.Double(25,200,75,75, 20, 20)); //Outs
        g.fill(new RoundRectangle2D.Double(300,200,75,75, 20, 20)); //AtBat

        writeText("Away", g,25, 100, 135);
        writeText("Home", g,300, 375, 135);
        writeText("Inning", g,150, 250, 160);
        writeText("Outs", g,25, 100, 310);
        writeText("At Bat", g,300, 375, 310);


        writeText(String.valueOf(scoreboard.awayRuns), g, 25, 100, 25, 100);
        writeText(String.valueOf(scoreboard.homeRuns), g, 300, 375, 25, 100);
        writeText(String.valueOf(scoreboard.halfInning/2), g, 150, 250, 25, 125);
        writeText(String.valueOf(scoreboard.outs), g, 25, 100, 200, 275);
        if (scoreboard.halfInning%2 == 1) writeText("A", g, 300, 375, 200, 275);
        else writeText("H", g, 300, 375, 200, 275);

        float[] fracs = {.25f,.75f};
        Color[] colors = {new Color(0,102,0), new Color(0,179,0)};
        LinearGradientPaint grass = new LinearGradientPaint(0,0, 400,400, fracs, colors, MultipleGradientPaint.CycleMethod.REPEAT);
        g.setColor(Color.black);
        drawRect(g, new Point(200,195), new Point(265,260), new Point(200,325), new Point(135,260));
        g.setPaint(grass);
        drawRect(g, new Point(200,200), new Point(260,260), new Point(200,320), new Point(140,260));

        if (scoreboard.bases[0]) g.setColor(Color.red);
        else g.setColor(Color.white);
        drawRect(g, new Point(260,260), new Point(250,250), new Point(240,260), new Point(250,270));

        if (scoreboard.bases[1]) g.setColor(Color.red);
        else g.setColor(Color.white);
        drawRect(g, new Point(200,200), new Point(210,210), new Point(200,220), new Point(190,210));

        if (scoreboard.bases[2]) g.setColor(Color.red);
        else g.setColor(Color.white);
        drawRect(g, new Point(140,260), new Point(150,250), new Point(160,260), new Point(150,270));

        g.setColor(Color.white);
        drawRect(g, new Point(200,320), new Point(210,310), new Point(200,300), new Point(190,310));

        image = makeTransparent(Color.lightGray, image);
        return image;

    }

    public void writeText(String str, Graphics2D g, int x1, int x2, int y) {
        Font font = new Font("Impact", Font.PLAIN, 30);
        TextLayout textLayout = new TextLayout(str, font, g.getFontRenderContext());
        float x = (x1 + x2 + textLayout.getVisibleAdvance()) / 2 - textLayout.getVisibleAdvance();
        g.setColor(new Color (0,40,0));
        textLayout.draw(g, x + 3, y + 3);

        g.setColor(new Color(0,100,0));
        textLayout.draw(g, x, y);
    }

    public void writeText(String str, Graphics2D g, int x1, int x2, int y1, int y2) {
        Font font = new Font("Impact", Font.PLAIN, 50);
        TextLayout textLayout = new TextLayout(str, font, g.getFontRenderContext());
        float y = (y1 + y2 + textLayout.getAscent()) / 2 - textLayout.getAscent()/8;
        float x = (x1 + x2 + textLayout.getVisibleAdvance()) / 2 - textLayout.getVisibleAdvance();
        g.setColor(new Color (0,40,0));
        textLayout.draw(g, x+3, y+3);
        g.setColor(new Color(0,100,0));
        textLayout.draw(g, x, y);
    }

    public void drawRect(Graphics g, Point p1, Point p2, Point p3, Point p4) {
        int[] tXL = {p1.x, p3.x,  p4.x};
        int[] tYL = {p1.y, p3.y,  p4.y};
        g.fillPolygon(tXL,tYL,3);

        int[] tXR = {p1.x, p2.x,  p3.x};
        int[] tYR = {p1.y, p2.y,  p3.y};
        g.fillPolygon(tXR,tYR,3);
    }

    public BufferedImage makeTransparent(Color c, BufferedImage image) {
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

    private void setUpKeyBindings() {
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0),"STOP");
        getActionMap().put("STOP", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (atBat != null) atBat.stop();
            }
        });

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke((char) KeyEvent.VK_0), "0");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke((char) KeyEvent.VK_1), "1");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke((char) KeyEvent.VK_2), "2");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke((char) KeyEvent.VK_3), "3");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke((char) KeyEvent.VK_4), "4");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke((char) KeyEvent.VK_5), "5");

        getActionMap().put("0", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                scoreboard.updateBases(0);
            }
        });
        getActionMap().put("1", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                scoreboard.updateBases(1);
            }
        });
        getActionMap().put("2", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                scoreboard.updateBases(2);
            }
        });
        getActionMap().put("3", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                scoreboard.updateBases(3);
            }
        });
        getActionMap().put("4", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                scoreboard.updateBases(4);
            }
        });
        getActionMap().put("5", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                scoreboard.updateBases(5);
            }
        });
    }
}
