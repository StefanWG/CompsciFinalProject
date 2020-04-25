import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Loading extends JPanel {
    public Team team;
    final int WIDTH = 900;
    final int HEIGHT = 780;
    BufferedImage astronauts = TeamImages.astronauts();
    BufferedImage americans = TeamImages.characterStefan();
    BufferedImage redSocks = TeamImages.redSocks();
    BufferedImage Mountains = TeamImages.Mountains();

    BufferedImage[] imagesArray = {astronauts, Mountains, Display.dirt(), Mountains, redSocks, Display.grass()};
    JLabel label = new JLabel("Please Select a Team");

    static Team[] teams  = new Team[] {new Team("Rosters/HoustonAstronauts.txt", true),
            new Team("Rosters/MiamiSwordfish.txt",true),
            new Team("Rosters/NewyorkAmericans.txt",true),
            new Team("Rosters/ColoradoRocks.txt",true),
            new Team("Rosters/BostonRedsocks.txt",true),
            new Team("Rosters/LosangelesAngles.txt",true)};


    public Loading(Main main) {

        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JButton advance = new JButton("Advance");
        advance.setPreferredSize(new Dimension(100,100));
        advance.addActionListener(e -> { if (team != null) main.newGame(); });

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