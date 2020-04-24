import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextLayout;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Display {

    public static BufferedImage outcomeText(int outcome) {
        BufferedImage image = new BufferedImage(650, 150, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2D = image.createGraphics();
        String str;
        switch (outcome) {
            case 0:
                str = "OUT";
                break;
            case 1:
                str = "SINGLE";
                break;
            case 2:
                str = "DOUBLE";
                break;
            case 3:
                str = "TRIPLE";
                break;
            case 4:
                str = "HOME RUN";
                break;
            case 5:
                str = "WALK";
                break;
            default:
                str = " ";
                break;
        }
        writeText(str, g2D, 0, 650, 0, 150, Color.orange, "Impact");
        return image;
    }

    public static BufferedImage drawScoreboard(Scoreboard scoreboard) {
        BufferedImage image = new BufferedImage(800, 160, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = image.createGraphics();
        Color textColor = new Color(0, 100, 0);
        Color homeColor = scoreboard.homeTeam.textColor.darker();
        Color awayColor = scoreboard.awayTeam.textColor.darker();
        String font = "Boulder";
        float[] fracs = {.25f, .75f};
        Color[] colors = {new Color(0, 102, 0), new Color(0, 179, 0)};
        LinearGradientPaint grass = new LinearGradientPaint(0, 0, 800, 800, fracs, colors, MultipleGradientPaint.CycleMethod.REPEAT);

        g.setColor(Color.darkGray);
        g.fill(new RoundRectangle2D.Double(0, 0, 800, 160, 0, 0));
        g.setColor(Color.BLACK);
        g.fill(new RoundRectangle2D.Double(10, 60, 80, 40, 20, 20)); //AwayName
        g.fill(new RoundRectangle2D.Double(10, 110, 80, 40, 20, 20)); //HomeName

        writeText(scoreboard.awayTeam.teamName.substring(0, 3).toUpperCase(), g, 10, 90, 60, 100, awayColor, font);
        writeText(scoreboard.homeTeam.teamName.substring(0, 3).toUpperCase(), g, 10, 90, 110, 150, homeColor, font);

        for (int i = 0; i < 9; i++) {
            g.setColor(Color.black);
            g.fill(new RoundRectangle2D.Double(100 + i * 50, 10, 40, 40, 20, 20)); //Inning
            g.fill(new RoundRectangle2D.Double(100 + i * 50, 60, 40, 40, 20, 20)); //AwayRuns
            g.fill(new RoundRectangle2D.Double(100 + i * 50, 110, 40, 40, 20, 20)); //HomeRuns

            writeText(String.valueOf(i + 1), g, 100 + i * 50, 140 + i * 50, 10, 50, Color.lightGray, font); //Innings
            try {
                Color c;
                if (i == scoreboard.halfInning / 2 && scoreboard.halfInning % 2 == 1) c = awayColor.brighter();
                else c = awayColor;
                writeText(String.valueOf(scoreboard.awayRunsInning.get(i)), g, 100 + i * 50, 140 + i * 50, 60, 100, c, font); //AwayRuns
            } catch (IndexOutOfBoundsException e) {
                writeText(" ", g, 100 + i * 50, 140 + i * 50, 60, 100, textColor, font); //AwayRuns
            }
            try {
                Color c;
                if (i == (scoreboard.halfInning - 1) / 2 && scoreboard.halfInning % 2 == 0) c = homeColor.brighter();
                else c = homeColor;
                writeText(String.valueOf(scoreboard.homeRunsInning.get(i)), g, 100 + i * 50, 140 + i * 50, 110, 150, c, font); //AwayRuns
            } catch (IndexOutOfBoundsException e) {
                writeText(" ", g, 100 + i * 50, 140 + i * 50, 100, 150, textColor, font); //AwayRuns
            }
        }

        g.setColor(Color.black);
        g.fill(new RoundRectangle2D.Double(550, 10, 40, 40, 20, 20)); //Inning
        g.fill(new RoundRectangle2D.Double(550, 60, 40, 40, 20, 20)); //A
        g.fill(new RoundRectangle2D.Double(550, 110, 40, 40, 20, 20)); //H
        writeText("R", g, 550, 590, 10, 50, Color.lightGray.brighter(), font);
        writeText(String.valueOf(scoreboard.awayRuns), g, 550, 590, 60, 100, awayColor.brighter(), font);
        writeText(String.valueOf(scoreboard.homeRuns), g, 550, 590, 110, 150, homeColor.brighter(), font);

        g.setColor(Color.black);
        g.fill(new RoundRectangle2D.Double(600, 10, 40, 40, 20, 20)); //Inning
        g.fill(new RoundRectangle2D.Double(600, 60, 40, 40, 20, 20)); //A
        g.fill(new RoundRectangle2D.Double(600, 110, 40, 40, 20, 20)); //H
        writeText("H", g, 600, 640, 10, 50, Color.lightGray.brighter(), font);
        writeText(String.valueOf(scoreboard.awayHits), g, 600, 640, 60, 100, awayColor.brighter(), font);
        writeText(String.valueOf(scoreboard.homeHits), g, 600, 640, 110, 150, homeColor.brighter(), font);

        BufferedImage fieldView = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
        Graphics2D f = fieldView.createGraphics();

        f.setColor(Color.black);
        drawRect(f, new Point(200, 0), new Point(400, 200), new Point(200, 400), new Point(0, 200));
        f.setPaint(grass);
        drawRect(f, new Point(200, 20), new Point(380, 200), new Point(200, 380), new Point(20, 200));

        if (scoreboard.bases[0]) f.setColor(Color.red);
        else f.setColor(Color.white);
        drawRect(f, new Point(400, 200), new Point(360, 160), new Point(320, 200), new Point(360, 240));

        if (scoreboard.bases[1]) f.setColor(Color.red);
        else f.setColor(Color.white);
        drawRect(f, new Point(200, 0), new Point(160, 40), new Point(200, 80), new Point(240, 40));

        if (scoreboard.bases[2]) f.setColor(Color.red);
        else f.setColor(Color.white);
        drawRect(f, new Point(0, 200), new Point(40, 160), new Point(80, 200), new Point(40, 240));

        f.setColor(Color.white);
        drawRect(f, new Point(200, 400), new Point(240, 360), new Point(200, 320), new Point(160, 360));

        writeText(String.valueOf(scoreboard.outs), f, 100, 300, 100, 300, Color.red, font);

        fieldView = resize(fieldView, 140, 140);

        g.drawImage(fieldView, 650, 10, null);
        image = makeTransparent(Color.lightGray, image);
        return image;

    }

    public static void writeText(String str, Graphics2D g, int x1, int x2, int y1, int y2, Color color, String f) {
        int fontSize = (int) ((y2 - y1) * 0.8);
        Font font = new Font(f, Font.PLAIN, fontSize);
        TextLayout textLayout = new TextLayout(str, font, g.getFontRenderContext());
        float y = (y1 + y2 + textLayout.getAscent()) / 2 - textLayout.getAscent() / 8;
        float x = (x1 + x2 + textLayout.getVisibleAdvance()) / 2 - textLayout.getVisibleAdvance();
//        g.setColor(color.darker());
//        textLayout.draw(g, x+3, y+3);
        g.setColor(color);
        textLayout.draw(g, x, y);
    }

    public static void drawRect(Graphics g, Point p1, Point p2, Point p3, Point p4) {
        int[] tXL = {p1.x, p3.x, p4.x};
        int[] tYL = {p1.y, p3.y, p4.y};
        g.fillPolygon(tXL, tYL, 3);

        int[] tXR = {p1.x, p2.x, p3.x};
        int[] tYR = {p1.y, p2.y, p3.y};
        g.fillPolygon(tXR, tYR, 3);
    }

    public static BufferedImage makeTransparent(Color c, BufferedImage image) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        int transparent = new Color(0, 0, 0, 0).getRGB();
        int original = c.getRGB();

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int current = image.getRGB(i, j);
                if (current == original) {
                    newImage.setRGB(i, j, transparent);
                } else {
                    newImage.setRGB(i, j, current);
                }
            }
        }

        return newImage;
    }

    public static BufferedImage grass() {
        BufferedImage tile = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = tile.createGraphics();
        g.setPaint(new Color(0, 100, 0));
        g.fillRect(0, 0, 800, 800);
        Random rand = new Random();

        for (int i = 0; i < 300000; i++) {
            int green = rand.nextInt(75) + 40;
            g.setPaint(new Color(0, green, 0));
            g.setStroke(new BasicStroke(rand.nextInt(2) + 1));
            int x1 = rand.nextInt(1000);
            int y1 = rand.nextInt(1000);
            int x2 = x1 + rand.nextInt(4) - 2;
            int y2 = y1 - rand.nextInt(15) + 10;
            g.drawLine(x1, y1, x2, y2);
        }
        return tile;
    }

    public static BufferedImage dirt() {
        BufferedImage tile = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
        Random rand = new Random();
        for (int i = 0; i < 800; i++) {
            for (int j = 0; j < 800; j++) {
                switch (rand.nextInt(5)) {
                    case 0:
                        tile.setRGB(i, j, new Color(138, 77, 0).getRGB());
                        break;
                    case 1:
                        tile.setRGB(i, j, new Color(162, 92, 0).getRGB());
                        break;
                    case 2:
                        tile.setRGB(i, j, new Color(189, 107, 0).getRGB());
                        break;
                    case 3:
                        tile.setRGB(i, j, new Color(214, 122, 0).getRGB());
                        break;
                    case 4:
                        tile.setRGB(i, j, new Color(240, 138, 0).getRGB());
                        break;
                }
            }
        }
        return tile;
    }

    public static BufferedImage fence() {
        BufferedImage image = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
        Random rand = new Random();
        for (int i = 0; i < 800; i++) {
            for (int j = 0; j < 800; j++) {
                switch (rand.nextInt(40)) {
                    case 0:
                        image.setRGB(i, j, new Color(150, 150, 150).getRGB());
                        break;
                    case 1:
                        image.setRGB(i, j, new Color(125, 125, 125).getRGB());
                        break;
                    case 2:
                        image.setRGB(i, j, new Color(115, 115, 115).getRGB());
                        break;
                    case 3:
                        image.setRGB(i, j, new Color(95, 95, 95).getRGB());
                        break;
                    case 4:
                        image.setRGB(i, j, new Color(85, 85, 85).getRGB());
                        break;
                    case 5:
                        image.setRGB(i, j, new Color(60, 60, 60).getRGB());
                        break;
                    default:
                        image.setRGB(i, j, new Color(105, 105, 105).getRGB());
                        break;
                }
            }
        }
        return image;
    }

    public static BufferedImage drawField() {
        BufferedImage image = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = image.createGraphics();

        Color fence = new Color(105, 105, 105);
        Color infield = new Color(181, 107, 23);
        Color mound = new Color(158, 94, 21);

        //Background
        g.drawImage(grass(), 0, 0, null);
        //Outside the field
        g.setPaint(Color.lightGray);
        g.setStroke(new BasicStroke(5));
        int y = 95;
        while (y > -100) {
            g.drawArc(-1400, y, 3600, 3600, 0, 360);
            y -= 5;
        }
        //OUTFIELD WALL
        g.setColor(Color.red);
        g.setStroke(new BasicStroke(5));
        g.drawArc(-1400, 90, 3600, 3600, 0, 360);
        g.setColor(fence);
        g.setStroke(new BasicStroke(15));
        g.drawArc(-1400, 97, 3600, 3600, 0, 360);

        //HOME PLATE DIRT AND BASELINES
        g.setPaint(infield);
        g.fillOval(-200, 350, 1200, 800);
        g.fillPolygon(new int[]{240, 0, 0}, new int[]{500, 210, 500}, 3);
        g.fillPolygon(new int[]{560, 800, 800}, new int[]{500, 210, 500}, 3);
        //INFIELD DIRT
        g.setStroke(new BasicStroke(4));
        g.drawArc(330, 116, 140, 10, 20, 140);
        g.setStroke(new BasicStroke(6));
        g.drawArc(-800, 116, 760 + 1600, 1600, 90, 85);
        g.drawArc(-800, 116, 760 + 1600, 1800, 90, 85);
        g.drawArc(-800, 116, 760 + 1600, 2000, 90, 85);
        g.drawArc(-800, 116, 760 + 1600, 2200, 90, 85);

        g.drawArc(-760, 116, 760 + 1600, 1600, 5, 85);
        g.drawArc(-760, 116, 760 + 1600, 1800, 5, 85);
        g.drawArc(-760, 116, 760 + 1600, 2000, 5, 85);
        g.drawArc(-760, 116, 760 + 1600, 2200, 5, 85);


        //MOUND
        g.setPaint(mound);
        g.fillOval(320, 122, 160, 20);
        //FOUL LINES
        g.setPaint(new Color(255, 230, 204));
        g.setStroke(new BasicStroke(5));
        g.drawLine(0, 260, 190, 500);
        g.drawLine(800, 260, 610, 500);
        //BATTERS BOXES
        g.drawPolygon(new int[]{820, 520, 470, 700}, new int[]{790, 790, 500, 500}, 4);
        g.drawPolygon(new int[]{-20, 280, 330, 100}, new int[]{790, 790, 500, 500}, 4);
        //HOME PLATE AND PITCHING RUBBER
        g.setColor(Color.darkGray);
        g.fillPolygon(new int[]{347, 453, 456, 400, 344}, new int[]{550, 550, 583, 613, 583}, 5);
        g.setPaint(new Color(215, 193, 142));
        g.fillPolygon(new int[]{350, 450, 453, 400, 347}, new int[]{550, 550, 580, 610, 580}, 5);
        g.fillRect(380, 127, 40, 2);

        BufferedImage dirtImage = dirt();
        BufferedImage fenceImage = fence();

        //MAKE DIRT
        for (int i = 0; i < 800; i++) {
            for (int j = 0; j < 800; j++) {
                if (image.getRGB(i, j) == infield.getRGB()) {
                    Color c = new Color(dirtImage.getRGB(i, j));
                    image.setRGB(i, j, c.darker().getRGB());
                } else if (image.getRGB(i, j) == mound.getRGB()) {
                    Color c = new Color(dirtImage.getRGB(i, j));
                    image.setRGB(i, j, c.darker().darker().getRGB());
                } else if (image.getRGB(i, j) == fence.getRGB()) {
                    image.setRGB(i, j, fenceImage.getRGB(i, j));
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

    public static BufferedImage characterJosh() {
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

    public static BufferedImage lineupCard(Team team) {
        BufferedImage image = new BufferedImage(533, 800, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = image.createGraphics();
        Color textColor = team.textColor;

        g.setColor(Color.darkGray);
        g.fill(new RoundRectangle2D.Double(0, 0, image.getWidth(), image.getHeight(), 0, 0));
        g.setColor(Color.BLACK);
        g.fill(new RoundRectangle2D.Double(50, 50, image.getWidth() - 100, 60, 20, 20)); //Name
        writeText(team.teamName.toUpperCase(), g, 50, image.getWidth() - 50, 50, 110, textColor, "Boulder");

        g.setColor(Color.black);
        g.fill(new RoundRectangle2D.Double(46, 170, 230, 50, 20, 20)); //Name
        g.fill(new RoundRectangle2D.Double(296, 170, 50, 50, 20, 20)); //Age
        g.fill(new RoundRectangle2D.Double(366, 170, 50, 50, 20, 20)); //Contact
        g.fill(new RoundRectangle2D.Double(436, 170, 50, 50, 20, 20)); //Power

        writeText("NAME", g, 46, 276, 170, 220, textColor, "Boulder"); //Name
        writeText("A", g, 296, 346, 170, 220, textColor, "Boulder"); //Age
        writeText("C", g, 366, 416, 170, 220, textColor, "Boulder"); //Contact
        writeText("P", g, 436, 486, 170, 220, textColor, "Boulder"); //Power

        //Players
        for (int i = 0; i < 9; i++) {
            g.setColor(Color.black);
            g.fill(new RoundRectangle2D.Double(46, 240 + 60 * i, 230, 50, 20, 20)); //Name
            g.fill(new RoundRectangle2D.Double(296, 240 + 60 * i, 50, 50, 20, 20)); //Age
            g.fill(new RoundRectangle2D.Double(366, 240 + 60 * i, 50, 50, 20, 20)); //Contact
            g.fill(new RoundRectangle2D.Double(436, 240 + 60 * i, 50, 50, 20, 20)); //Power

            writeText(team.lineup[i].name, g, 46, 276, 240 + 60 * i, 290 + 60 * i, textColor, "Boulder"); //Name
            writeText(String.valueOf(team.lineup[i].age), g, 296, 346, 240 + 60 * i, 290 + 60 * i, textColor, "Boulder"); //Age
            writeText(String.valueOf(team.lineup[i].contactRating), g, 366, 416, 240 + 60 * i, 290 + 60 * i, textColor, "Boulder"); //Contact
            writeText(String.valueOf(team.lineup[i].powerRating), g, 436, 486, 240 + 60 * i, 290 + 60 * i, textColor, "Boulder"); //Power

        }

        image = makeTransparent(Color.lightGray, image);
        return image;

    }

    public static JButton rulesButton(Game g) {
        BufferedImage image = new BufferedImage(125, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, 125, 100);
        writeText("Rules", graphics, 0, 125, 25, 75, Color.BLACK, "Boulder");
        image = makeTransparent(Color.white, image);


        JButton button = new JButton(new ImageIcon(image));
        button.setPreferredSize(new Dimension(125, 100));
        button.setFocusable(false);
        button.setBackground(Color.darkGray);
        button.setOpaque(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                g.rules = true;
                g.repaint();
            }
        });

        return button;
    }

    public static JButton atBatButton(Game g) {
        BufferedImage image = new BufferedImage(125, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, 125, 100);
        writeText("At Bat", graphics, 0, 125, 25, 75, Color.BLACK, "Boulder");
        image = makeTransparent(Color.white, image);

        JButton button = new JButton(new ImageIcon(image));
        button.setFocusable(false);
        button.setBackground(Color.darkGray);
        button.setOpaque(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                g.rules = false;
                g.repaint();
            }
        });

        return button;
    }

    public static BufferedImage rulesText() {
        BufferedImage image = new BufferedImage(250, 500, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = image.createGraphics();
        g.setColor(Color.darkGray);
        g.fill(new RoundRectangle2D.Double(0, 0, 250, 500, 0, 0));
        g.setColor(Color.black);

        String rules = "Press Space Bar anytime along the meter to choose an Accuracy Rating. " +
                "\n Press space bar for the second meter to choose the Power Level." +
                "\n The closer to the middle the better (Tip: Aim for the Green!!)";
        
        Font font = new Font("Boulder", Font.PLAIN, 23);
        g.setFont(font);
        ArrayList<String> splitString = new ArrayList<>(Arrays.asList(rules.split(" ")));

        String toDraw = "";
        int count = 0;
        while (splitString.size() > 0) {
            if (splitString.get(0).length() + toDraw.length() < 20) {
                toDraw += splitString.get(0) + " ";
                splitString.remove(0);
            } else {
                g.drawString(toDraw, 20, 50 + font.getSize()*count);
                toDraw = "";
                count++;
            }
        }

        g.drawString(toDraw, 20, 50 + font.getSize()*count);

        return image;
    }

    public static BufferedImage atBatOnDeck(Scoreboard scoreboard) {
        BufferedImage image = new BufferedImage(250, 500, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = image.createGraphics();
        g.setColor(Color.darkGray);
        g.fill(new RoundRectangle2D.Double(0, 0, 250, 500, 20, 20));

        for (int i = 0; i < 3; i++) {
            g.setColor(Color.black);
            g.fill(new RoundRectangle2D.Double(20, 20 + 150 * i, 210, 40, 20, 20));
            g.fill(new RoundRectangle2D.Double(60, 80 + 150 * i, 40, 40, 20, 20)); //A
            g.fill(new RoundRectangle2D.Double(150, 80 + 150 * i, 40, 40, 20, 20)); //A
            Player player;
            Color color;
            if (scoreboard.halfInning % 2 == 1) {
                player = scoreboard.awayTeam.lineup[(scoreboard.awayTeam.lineupPos + i) % 9];
                color = scoreboard.awayTeam.textColor;
            } else {
                player = scoreboard.homeTeam.lineup[(scoreboard.homeTeam.lineupPos + i) % 9];
                color = scoreboard.homeTeam.textColor;
            }
            writeText(player.name, g, 20, 230, 20 + 150 * i, 60 + 150 * i, color, "Boulder");
            writeText(String.valueOf(player.contactRating), g, 60, 100, 80 + 150 * i, 120 + 150 * i, color, "Boulder");
            writeText(String.valueOf(player.powerRating), g, 150, 190, 80 + 150 * i, 120 + 150 * i, color, "Boulder");
        }
        return image;
    }

}

//class Ball {
//    int rotation = 0;
//
//    public void update() {
//        rotation += 1;
//    }
//
//
//    public BufferedImage draw(int diameter) {
//        BufferedImage seams = new BufferedImage(200,628,BufferedImage.TYPE_INT_ARGB);
//        Graphics2D s = seams.createGraphics();
//        s.setPaint(Color.black);
//        s.fillRect(0,0,200,628);
//        s.setPaint(Color.red);
//        s.setStroke(new BasicStroke(5));
//        s.drawArc(0,0,200,200, 330,240);
//        s.drawArc(0,240,200,200,150,240);
//        s.drawArc(208-40, 120-40,280,280,150,60);
//        s.drawArc(-208-40, 120-40,280,280,330,60);
//
//        BufferedImage image = new BufferedImage(220,220,BufferedImage.TYPE_INT_ARGB);
//        Graphics2D g = image.createGraphics();
//        g.setColor(Color.black);
//        g.fillRect(0,0,220,220);
//        g.setPaint(Color.lightGray);
//        g.fillOval(0,0,220,220);
//        for (int i = 0; i < 200; i++) {
//            for (int j = 0; j < 200; j++) {
//                Color color = new Color(seams.getRGB(i,(j+rotation)%628));
//                Color current = new Color(image.getRGB(i+10,220 - j - 10));
//                if (!color.equals(Color.black) && current.equals(Color.lightGray)) image.setRGB(i+10,220 - j - 10,color.getRGB());
//            }
//        }
//        image = Display.makeTransparent(Color.black,image);
//        image = Display.resize(image, diameter,diameter);
//
//
//        //TODO modify size so it looks like coming forward
//
//        return image;
//    }
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
//}
