/**
 * The GameOver class contains the code for the screen that is shown when the game is over.
 * It displays the box score and allows the user to return to the main screen
 * when the user finishes an individual game, not in season mode.
 **/

import javax.swing.*;
import java.awt.*;

public class GameOver extends JPanel {
    Main main;
    public GameOver(Main m) {
        main = m;
        setPreferredSize(new Dimension(Main.WIDTH,Main.HEIGHT));
        setLayout(null);

        JButton pickNewTeam = new JButton("Pick New Team");
        pickNewTeam.addActionListener(e -> m.pickNewTeam());
        pickNewTeam.setBounds(0,Main.HEIGHT - 80 ,Main.WIDTH,80);
        add(pickNewTeam);
    }
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(Display.boxScore(main.game.awayTeam), 0,0,null);
        g.drawImage(Display.boxScore(main.game.homeTeam), Main.WIDTH / 2, 0, null);
    }
}
