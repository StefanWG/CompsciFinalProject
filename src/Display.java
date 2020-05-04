/**
 * This class contains only static methods. All the drawing except the meter
 * and the team images are done in this class, and then referenced when necessary
 * for organizational purposes.
 **/

import javax.swing.*;
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;

public class Display {


 /** passed in the number from the atBat result and displays the type of hit based on that number*/
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
/** draws a scoreboard from the passed in arguments from the scoreboard class */
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
            int n = i;
            if ((scoreboard.halfInning - 1) / 2 >= 9) {
                n += (scoreboard.halfInning - 1) / 2 - 8;
            }

            writeText(String.valueOf(n + 1), g, 100 + i * 50, 140 + i * 50, 10, 50, Color.lightGray, font); //Innings
            try {
                Color c;
                if (i == scoreboard.halfInning / 2 && scoreboard.halfInning % 2 == 1) c = awayColor.brighter();
                else c = awayColor;
                writeText(String.valueOf(scoreboard.awayRunsInning.get(n)), g, 100 + i * 50, 140 + i * 50, 60, 100, c, font); //AwayRuns
            } catch (IndexOutOfBoundsException e) {
                writeText(" ", g, 100 + i * 50, 140 + i * 50, 60, 100, textColor, font); //AwayRuns
            }
            try {
                Color c;
                if (i == (scoreboard.halfInning - 1) / 2 && scoreboard.halfInning % 2 == 0) c = homeColor.brighter();
                else c = homeColor;
                writeText(String.valueOf(scoreboard.homeRunsInning.get(n)), g, 100 + i * 50, 140 + i * 50, 110, 150, c, font); //AwayRuns
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
/** centers the passed in string using the passed in x and y values */
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

   /** makes different backgrounds transparent so that different buffered images do not stick out: a more cohesive graphic */
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


    /** uses randomization to adds texture using different colors*/
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

 /** takes a random number which then changes the color of each pixel: adds texture to the fence*/
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

        /** Background */
        g.drawImage(grass(), 0, 0, null);
        //Outside the field
        g.setPaint(Color.lightGray);
        g.setStroke(new BasicStroke(5));
        int y = 95;
        while (y > -100) {
            g.drawArc(-1400, y, 3600, 3600, 0, 360);
            y -= 5;
        }
        /** OUTFIELD WALL */
        g.setColor(Color.red);
        g.setStroke(new BasicStroke(5));
        g.drawArc(-1400, 90, 3600, 3600, 0, 360);
        g.setColor(fence);
        g.setStroke(new BasicStroke(15));
        g.drawArc(-1400, 97, 3600, 3600, 0, 360);

        /** HOME PLATE DIRT AND BASELINES */
        g.setPaint(infield);
        g.fillOval(-200, 350, 1200, 800);
        g.fillPolygon(new int[]{240, 0, 0}, new int[]{500, 210, 500}, 3);
        g.fillPolygon(new int[]{560, 800, 800}, new int[]{500, 210, 500}, 3);

        /** INFIELD DIRT */
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


        /** MOUND */
        g.setPaint(mound);
        g.fillOval(320, 122, 160, 20);

        /** FOUL LINES */
        g.setPaint(new Color(255, 230, 204));
        g.setStroke(new BasicStroke(5));
        g.drawLine(0, 260, 190, 500);
        g.drawLine(800, 260, 610, 500);

        /** BATTERS BOXES */
        g.drawPolygon(new int[]{820, 520, 470, 700}, new int[]{790, 790, 500, 500}, 4);
        g.drawPolygon(new int[]{-20, 280, 330, 100}, new int[]{790, 790, 500, 500}, 4);

        /** HOME PLATE AND PITCHING RUBBER */
        g.setColor(Color.darkGray);
        g.fillPolygon(new int[]{347, 453, 456, 400, 344}, new int[]{550, 550, 583, 613, 583}, 5);
        g.setPaint(new Color(215, 193, 142));
        g.fillPolygon(new int[]{350, 450, 453, 400, 347}, new int[]{550, 550, 580, 610, 580}, 5);
        g.fillRect(380, 127, 40, 2);

        BufferedImage dirtImage = dirt();
        BufferedImage fenceImage = fence();

        /** MAKE DIRT */
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
/** This methods resizes the buffered images to fit within the different sections displayed: You pass in a new height and new width */
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }


/** This displays the rosters for a specific team and their stats*/
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
/** This creates a clickable button that is titled Rules   */
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

        button.addActionListener(e -> {
            g.rules = true;
            g.repaint();
        });

        return button;
    }


    /** this method creates the clickable button which allows the user to then see the buffered image of the next people at bat */
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

        button.addActionListener(e -> {
            g.rules = false;
            g.repaint();
        });

        return button;
    }

    /** This is a method which essentially prints the rules */
    public static BufferedImage rulesText() {
        BufferedImage image = new BufferedImage(250, 500, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = image.createGraphics();
        g.setColor(Color.darkGray);
        g.fill(new RoundRectangle2D.Double(0, 0, 250, 500, 0, 0));
        g.setColor(Color.black);

        String rules = "Press Space Bar anytime along the meter to choose an Accuracy Rating. " +
                "Press space bar for the second meter to choose the Power Level. " +
                "The closer to the middle the better (Tip: Aim for the Green!!) " + "Press Command+ M/E/C to mute, end the game, " +
                "or pick a new team.";

        Font font = new Font("Boulder", Font.PLAIN, 23);
        g.setFont(font);
        ArrayList<String> splitString = new ArrayList<>(Arrays.asList(rules.split(" ")));

        StringBuilder toDraw = new StringBuilder();
        int count = 0;
        while (splitString.size() > 0) {
            if (splitString.get(0).length() + toDraw.length() < 20) {
                toDraw.append(splitString.get(0)).append(" ");
                splitString.remove(0);
            } else {
                g.drawString(toDraw.toString(), 20, 50 + font.getSize() * count);
                toDraw = new StringBuilder();
                count++;
            }
        }

        g.drawString(toDraw.toString(), 20, 50 + font.getSize() * count);

        return image;
    }

    /** this is a method which is called to print the team members (a buffered image) that are next to bat */

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

 /** it displays the stats of the game when the game ends*/
    public static BufferedImage boxScore(Team team) {
        BufferedImage image = new BufferedImage(450, 700, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = image.createGraphics();
        Color textColor = team.textColor.darker();

        g.setColor(Color.darkGray);
        g.fill(new RoundRectangle2D.Double(0, 0, image.getWidth(), image.getHeight(), 0, 0));
        g.setColor(Color.BLACK);
        g.fill(new RoundRectangle2D.Double(50, 30, image.getWidth() - 100, 60, 20, 20)); //Name
        writeText(team.teamName.toUpperCase(), g, 50, image.getWidth() - 50, 23, 90, textColor, "Boulder");

        g.setColor(Color.black);
        g.fill(new RoundRectangle2D.Double(15, 120, 200, 40, 20, 20)); //Name
        g.fill(new RoundRectangle2D.Double(230, 120, 40, 40, 20, 20)); //AB
        g.fill(new RoundRectangle2D.Double(285, 120, 40, 40, 20, 20)); //H
        g.fill(new RoundRectangle2D.Double(340, 120, 40, 40, 20, 20)); //RBI
        g.fill(new RoundRectangle2D.Double(395, 120, 40, 40, 20, 20)); //HR

        writeText("NAME", g, 15, 215, 120, 160, textColor, "Boulder"); //Name
        writeText("AB", g, 230, 270, 120, 160, textColor, "Boulder"); //AB
        writeText("H", g, 285, 325, 120, 160, textColor, "Boulder"); //H
        writeText("RBI", g, 340, 380, 120, 160, textColor, "Boulder"); //RBI
        writeText("HR", g, 395, 435, 120, 160, textColor, "Boulder"); //HR


        //Players
        int totalHits = 0;
        int RBIs = 0;
        int HRs = 0;
        int AtBats = 0;
        for (int i = 0; i < 9; i++) {
            Player p = team.lineup[i];
            int hits = p.singles + p.doubles + p.triples + p.HRs;

            RBIs += p.RBIs;
            HRs += p.HRs;
            totalHits += hits;
            AtBats += p.atBats;

            g.setColor(Color.black);
            g.fill(new RoundRectangle2D.Double(15, 190 + i * 50, 200, 40, 20, 20)); //Name
            g.fill(new RoundRectangle2D.Double(230, 190 + i * 50, 40, 40, 20, 20)); //AB
            g.fill(new RoundRectangle2D.Double(285, 190 + i * 50, 40, 40, 20, 20)); //H
            g.fill(new RoundRectangle2D.Double(340, 190 + i * 50, 40, 40, 20, 20)); //RBI
            g.fill(new RoundRectangle2D.Double(395, 190 + i * 50, 40, 40, 20, 20)); //HR

            writeText(p.name, g, 15, 215, 190 + i * 50, 230 + i * 50, textColor, "Boulder"); //Name
            writeText(String.valueOf(p.atBats), g, 230, 270, 190 + i * 50, 230 + i * 50, textColor, "Boulder"); //AB
            writeText(String.valueOf(hits), g, 285, 325, 190 + i * 50, 230 + i * 50, textColor, "Boulder"); //H
            writeText(String.valueOf(p.RBIs), g, 340, 380, 190 + i * 50, 230 + i * 50, textColor, "Boulder"); //RBI
            writeText(String.valueOf(p.HRs), g, 395, 435, 190 + i * 50, 230 + i * 50, textColor, "Boulder"); //HR
        }

        g.setColor(Color.black);
        g.fill(new RoundRectangle2D.Double(15, 640, 200, 40, 20, 20)); //Name
        g.fill(new RoundRectangle2D.Double(230, 640, 40, 40, 20, 20)); //AB
        g.fill(new RoundRectangle2D.Double(285, 640, 40, 40, 20, 20)); //H
        g.fill(new RoundRectangle2D.Double(340, 640, 40, 40, 20, 20)); //RBI
        g.fill(new RoundRectangle2D.Double(395, 640, 40, 40, 20, 20)); //HR

        writeText("Totals", g, 15, 215, 640, 680, textColor.brighter(), "Boulder"); //Name
        writeText(String.valueOf(AtBats), g, 230, 270, 640, 680, textColor.brighter(), "Boulder"); //AB
        writeText(String.valueOf(totalHits), g, 285, 325, 640, 680, textColor.brighter(), "Boulder"); //H
        writeText(String.valueOf(RBIs), g, 340, 380, 640, 680, textColor.brighter(), "Boulder"); //RBI
        writeText(String.valueOf(HRs), g, 395, 435, 640, 680, textColor.brighter(), "Boulder"); //HR

        image = makeTransparent(Color.lightGray, image);
        return image;

    }
/** In the season mode it is each box for the games that can be played or simulated. It projects the outcome W or L  */
    public static BufferedImage scheduleBox(SeasonGame s) {
        BufferedImage image = new BufferedImage(175, 175, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = image.createGraphics();

        g.setColor(Color.darkGray);
        g.fill(new RoundRectangle2D.Double(0, 0, image.getWidth(), image.getHeight(), 20, 20));
        g.setColor(Color.BLACK);
        g.fill(new RoundRectangle2D.Double(5, 20, image.getWidth() - 55, 20, 20, 20)); //AwayName
        g.fill(new RoundRectangle2D.Double(5, 50, image.getWidth() - 55, 20, 20, 20)); //HomeName
        g.fill(new RoundRectangle2D.Double(130, 20, 40, 20, 20, 20)); //AwayScore
        g.fill(new RoundRectangle2D.Double(130, 50, 40, 20, 20, 20)); //HomeScore

        writeText(s.awayTeam.teamName.toUpperCase(), g, 5, image.getWidth() - 50, 20, 40, s.awayTeam.textColor, "Boulder"); //AwayName
        writeText(s.homeTeam.teamName.toUpperCase(), g, 5, image.getWidth() - 50, 50, 70, s.homeTeam.textColor, "Boulder"); //HomeName

        if (s.played) {
            writeText(String.valueOf(s.awayRuns), g, 130, 170, 20, 40, s.awayTeam.textColor, "Boulder"); //AwayScore
            writeText(String.valueOf(s.homeRuns), g, 130, 170, 50, 70, s.homeTeam.textColor, "Boulder"); //HomeScore
        } else {
            g.setColor(Color.black);
            g.fillRect(50, 115, 75, 10);
        }
        if (s.played && s.homeRuns > s.awayRuns) {
            if (s.season.team.teamName.equals(s.homeTeam.teamName))
                writeText("W", g, 50, image.getWidth() - 50, 80, 160, Color.green.darker(), "Impact");
            else writeText("L", g, 50, image.getWidth() - 50, 80, 160, Color.red.darker(), "Impact");
        } else if (s.played && s.awayRuns > s.homeRuns) {
            if (s.season.team.teamName.equals(s.awayTeam.teamName))
                writeText("W", g, 50, image.getWidth() - 50, 80, 160, Color.green.darker(), "Impact");
            else writeText("L", g, 50, image.getWidth() - 50, 80, 160, Color.red.darker(), "Impact");
        }

        return image;

    }
/** This displays the current standings given a specific state in the season */

    public static BufferedImage seasonModeStandings(ArrayList<SeasonTeam> teams, SeasonMode season) {
        BufferedImage image = new BufferedImage(395, 230 + teams.size() * 50, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = image.createGraphics();

        g.setColor(Color.darkGray);
        g.fill(new RoundRectangle2D.Double(0, 0, image.getWidth(), image.getHeight(), 0, 0));
        g.setColor(Color.BLACK);
        g.fill(new RoundRectangle2D.Double(50, 50, image.getWidth() - 100, 60, 20, 20)); //Name
        writeText("Standings", g, 50, image.getWidth() - 50, 50, 110, Color.lightGray, "Boulder");

        g.setColor(Color.black);
        g.fill(new RoundRectangle2D.Double(15, 150, 200, 40, 20, 20)); //Name
        g.fill(new RoundRectangle2D.Double(230, 150, 40, 40, 20, 20)); //Age
        g.fill(new RoundRectangle2D.Double(285, 150, 40, 40, 20, 20)); //Contact
        g.fill(new RoundRectangle2D.Double(340, 150, 40, 40, 20, 20)); //Power

        writeText("NAME", g, 15, 215, 150, 190, Color.lightGray, "Boulder"); //Name
        writeText("W", g, 230, 270, 150, 190, Color.lightGray, "Boulder"); //Age
        writeText("L", g, 285, 325, 150, 190, Color.lightGray, "Boulder"); //Contact
        writeText("GB", g, 340, 380, 150, 190, Color.lightGray, "Boulder"); //Power

        int i = 0;
        for (SeasonTeam team : teams) {
            if (team.teamName.equals(season.team.teamName)) g.setColor(Color.lightGray);
            else g.setColor(Color.black);
            g.fill(new RoundRectangle2D.Double(15, 215 + 50 * i, 200, 40, 20, 20)); //Name
            g.fill(new RoundRectangle2D.Double(230, 215 + 50 * i, 40, 40, 20, 20)); //Wins
            g.fill(new RoundRectangle2D.Double(285, 215 + 50 * i, 40, 40, 20, 20)); //Losses
            g.fill(new RoundRectangle2D.Double(340, 215 + 50 * i, 40, 40, 20, 20)); //GB
            int gamesBack = teams.get(0).wins - team.wins;
            writeText(team.teamName, g, 15, 200, 215 + 50 * i, 255 + 50 * i, team.textColor, "Boulder"); //Name
            writeText(String.valueOf(team.wins), g, 230, 270, 215 + 50 * i, 255 + 50 * i, team.textColor, "Boulder"); //Wins
            writeText(String.valueOf(team.losses), g, 285, 325, 215 + 50 * i, 255 + 50 * i, team.textColor, "Boulder"); //losses
            writeText(String.valueOf(gamesBack), g, 340, 380, 215 + 50 * i, 255 + 50 * i, team.textColor, "Boulder"); //GB
            i++;
        }

        return image;

    }

}