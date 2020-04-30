/**
 * This class controls the screen that users see when they are picking a team and game mode.
 * It contains a scrollpane filled with buttons for each team, a image area for the
 * lineup to be displayed, and buttons to select the game mode.
 **/

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;


/**
 * Loading extends Jpanel so that this class is displayed separately from when the game is run
 **/

public class Loading extends JPanel {
    public Team team;
    public BufferedImage[] imagesArray;
    public JLabel label = new JLabel("Please Select a Team");
    public static ArrayList<Team> teams  = new ArrayList<>();

    public Loading(Main main) {
        imagesArray = TeamImages.teamImages;

        setPreferredSize(new Dimension(Main.WIDTH,Main.HEIGHT));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        File rosters = new File("Rosters");
        File[] list = rosters.listFiles();
        if (list != null && teams.size() == 0) {
            for (File f : list) {
                teams.add(new Team(String.valueOf(f), false));
            }
        }

        /** This creates the button that allows the user to select the single game mode */

        JButton advance = new JButton("Single Game");
        advance.setPreferredSize(new Dimension(100,380));
        advance.addActionListener(e -> {
            if (team != null)  {
                team.humanPlayer = true;
                main.newGame();
            }
        });

    /** This creates the button that allows the user to select the season mode */

        JButton season = new JButton("Full Season");
        season.setPreferredSize(new Dimension(100,380));
        season.addActionListener(e -> {
            if (team != null)  {
                team.humanPlayer = true;
                main.newSeason();
            }
        });

        /** Creates buttons, the scroll pane, and the split panes. */

        JPanel teamPanel = new JPanel();
        teamPanel.setLayout(new GridLayout(teams.size(),1));

        JScrollPane scrollPane = new JScrollPane(teamPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
;
        for (int i = 0; i < teams.size(); i++) {
            teamPanel.add(new Button(imagesArray[i], this, i));
        }

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(advance);
        buttonsPanel.add(season);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, label);
        splitPane.setDividerLocation(266);
        splitPane.setDividerSize(0);

        JSplitPane splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane, buttonsPanel);
        splitPane2.setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT));
        splitPane2.setDividerLocation(790);
        splitPane2.setDividerSize(0);

        this.add(splitPane2);
    }
}