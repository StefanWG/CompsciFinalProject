import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOver extends JPanel {
    boolean gameWasFinished;
    //TODO GameOver class
    public GameOver(Main m) {
//        gameWasFinished = b; //If true show box score/scoreboard..., if not give other options

        setPreferredSize(new Dimension(900,780));
        setLayout(null);
        JButton pickNewTeam = new JButton("Pick New Team");
        pickNewTeam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m.pickNewTeam();
            }
        });
        pickNewTeam.setBounds(0,0,100,100);
        add(pickNewTeam);


        JButton sameTeam = new JButton("Run it back same team");
        pickNewTeam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m.newGame();
            }
        });
        pickNewTeam.setBounds(0,0,100,100);
        sameTeam.setBounds(100,0,100,100);
        add(pickNewTeam);
        add(sameTeam);
    }
}
