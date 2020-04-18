import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Loading extends JPanel {
    public Player player;
    public Team team;
    final int WIDTH = 800;
    final int HEIGHT = 850;
    BufferedImage image1 = Display.characterJosh();
    BufferedImage image2 = Display.characterStefan();
    BufferedImage image3 = Display.characterRohil();
//    Player josh = new Player(20, "Gladiators", 84, "Josh", 76);
//    Player stefan = new Player (19, "Polar Bears",74, "Stefan", 86);
//    Player rohil = new Player (19, "Rams", 80, "Rohil", 80);
    BufferedImage[] imagesArray = {image1, image2, image3};
    JTextArea textArea = new JTextArea("Select a player");
    Team[] teams  = new Team[] {new Team(),new Team(),new Team()};
//    Player[] players = {josh, stefan, rohil};

    public Loading(Main main) {
        setPreferredSize(new Dimension(WIDTH,HEIGHT));

        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(10,1));
        JButton advance = new JButton("Advance");
        advance.setPreferredSize(new Dimension(100,100));
        advance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (team != null) main.newGame();
                else textArea.setText("You must select a team to advance");

            }
        });

        JPanel panel2 = new JPanel();
        panel2.setOpaque(true);
        panel2.setBackground(Color.blue);

        JScrollPane scrollPane = new JScrollPane(panel1);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        for (int i = 0; i < 3; i++) {
            panel1.add(new Button(imagesArray[i], this, i));
        }

        for (int i = 0; i < 7; i++) {
            BufferedImage imageIcon;
            if (i % 2 == 0)  imageIcon = Display.dirt();
            else imageIcon = Display.grass();
            panel1.add(new Button(imageIcon, this, i));
        }


        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, textArea);
        splitPane.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
        splitPane.setDividerLocation(WIDTH/3);
        splitPane.setDividerSize(0);


        JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane, advance);
        splitPane2.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
        splitPane2.setDividerLocation(WIDTH - WIDTH/6);
        splitPane2.setDividerSize(0);

        this.add(splitPane2);
    }
}