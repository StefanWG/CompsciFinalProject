import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Loading extends JPanel {
    public Team team;
    final int WIDTH = 900;
    final int HEIGHT = 780;
    BufferedImage astronauts = TeamImages.astronauts();
    BufferedImage americans = TeamImages.characterStefan();
    BufferedImage redSocks = TeamImages.redSocks();
    BufferedImage mountains = TeamImages.mountains();

    BufferedImage[] imagesArray = {astronauts, mountains, Display.dirt(), mountains, redSocks, Display.grass(),astronauts, mountains, Display.dirt(), mountains, redSocks, Display.grass()};
    JLabel label = new JLabel("Please Select a Team");

    static ArrayList<Team> teams  = new ArrayList<>();


    public Loading(Main main) {

        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        File rosters = new File("Rosters");
        File[] list = rosters.listFiles();
        if (list != null) {
            for (File f : list) {
                teams.add(new Team(String.valueOf(f), false));
            }
        }

        JButton advance = new JButton("Advance");
        advance.setPreferredSize(new Dimension(100,100));
        advance.addActionListener(e -> {
            if (team != null)  {
                team.humanPlayer = true;
                main.newGame();
            }
        });

        JPanel teamPanel = new JPanel();
        teamPanel.setLayout(new GridLayout(teams.size(),1));

        JScrollPane scrollPane = new JScrollPane(teamPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        for (int i = 0; i < teams.size(); i++) {
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