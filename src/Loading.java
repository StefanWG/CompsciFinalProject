import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Loading extends JPanel {
    public Team team;
    final int WIDTH = 900;
    final int HEIGHT = 780;
    BufferedImage image1 = Display.characterJosh();
    BufferedImage image2 = Display.characterStefan();
    BufferedImage image3 = Display.characterRohil();

    BufferedImage[] imagesArray = {image1, image2, image3,image1, image2, image3};
    JLabel label = new JLabel("Please Select a Team");

    static Team[] teams  = new Team[] {new Team("Astronauts", "HoustonAstronauts.txt", Color.orange),
            new Team("Swordfish", "MiamiSwordfish.txt", Color.CYAN),
            new Team("Americans","NewyorkAmericans.txt", Color.blue),
            new Team("Rocks", "ColoradoRocks.txt", new Color(68,8,122)),
            new Team("Red Socks", "BostonRedsocks.txt", Color.red),
            new Team("Angles", "LosangelesAngles.txt", Color.red)};


    public Loading(Main main) {
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JButton advance = new JButton("Advance");
        advance.setPreferredSize(new Dimension(100,100));
        advance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (team != null) main.newGame();
            }
        });

        JPanel teamPanel = new JPanel();
        teamPanel.setLayout(new GridLayout(teams.length,1));

        JScrollPane scrollPane = new JScrollPane(teamPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        for (int i = 0; i < 6; i++) {
            teamPanel.add(new Button(imagesArray[i], this, i));
        }

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, label);
        splitPane.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
        splitPane.setDividerLocation(266);
        splitPane.setDividerSize(0);

        JSplitPane splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane, advance);
        splitPane2.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
        splitPane2.setDividerLocation(790);
        splitPane2.setDividerSize(0);

        this.add(splitPane2);
    }
}