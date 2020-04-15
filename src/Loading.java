import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Loading extends JPanel {
    public Player player;
    final int WIDTH = 800;
    final int HEIGHT = 850;
    BufferedImage image1 = Display.characterJosh();
    BufferedImage image2 = Display.characterRohil();
    BufferedImage image3 = Display.characterStefan();
    Player josh = new Player(20, "Gladiators", "Sushi", 84, "Josh", 90);
    Player stefan = new Player (19, "Polar Bears", "Buffalo Wings",74, "Stefan", 60);
    Player rohil = new Player (19, "Rams", "Poke", 80, "Rohil", 80);
    BufferedImage[] imagesArray = {image1, image2, image3};
    
    Player[] players = {josh, stefan, rohil};

    public Loading(Main main) {
        JTextArea textArea = new JTextArea("Select a player");
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(10,1));
        JButton advance = new JButton("Advance");
        advance.setPreferredSize(new Dimension(100,100));
        advance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player != null) main.newGame();
                else textArea.setText("You must select a player to advance");

            }
        });

        JPanel panel2 = new JPanel();
        panel2.setOpaque(true);
        panel2.setBackground(Color.blue);

        for (int i = 0; i < 3; i++) {

            //put images in this for loops
            ImageIcon imageIcon = new ImageIcon(Display.resize(imagesArray[i],WIDTH/4,WIDTH/4));
            JButton button = new JButton(imageIcon);
            button.setPreferredSize(new Dimension(WIDTH/4,WIDTH/4));
            int finalI = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    player = players[finalI];
                    textArea.setText("You have selected: \n" + players[finalI].toString());
                }
            });
            panel1.add(button);


        }

        for (int i = 0; i < 7; i++) {
            ImageIcon imageIcon;
            if (i % 2 == 0)  imageIcon = new ImageIcon(Display.dirt());
            else imageIcon = new ImageIcon(Display.grass());
            JButton button = new JButton(String.valueOf(i),imageIcon);
            button.setPreferredSize(new Dimension(100,100));
            panel1.add(button);
        }


        JScrollPane scrollPane = new JScrollPane(panel1);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, textArea);
        splitPane.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
        splitPane.setDividerLocation(WIDTH/3);
        splitPane.setDividerSize(0);


        JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane, advance);
        splitPane2.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
        splitPane2.setDividerLocation(WIDTH - WIDTH/6);
        splitPane2.setDividerSize(0);

        this.add(splitPane2);
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
    }



}

