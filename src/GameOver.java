import javax.swing.*;
import java.awt.*;

public class GameOver extends JPanel {
    Main main;
    //TODO GameOver class
    public GameOver(Main m) {
        main = m;
        setPreferredSize(new Dimension(900,780));
        setLayout(null);
        JButton pickNewTeam = new JButton("Pick New Team");
        pickNewTeam.addActionListener(e -> m.pickNewTeam());
        pickNewTeam.setBounds(0,700,900,80);
        add(pickNewTeam);

    }
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(Display.boxScore(main.game.awayTeam), 0,0,null);
        g.drawImage(Display.boxScore(main.game.homeTeam), 450, 0, null);
    }
}
