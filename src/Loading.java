import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Loading extends JPanel {
    public Player player;
    final int WIDTH = 800;
    final int HEIGHT = 850;
    Player josh = new Player(20, "Gladiators", "Sushi", 84, "Josh", 76);
    Player stefan = new Player (19, "Polar Bears", "Buffalo Wings",74, "Stefan", 86);
    Player rohil = new Player (19, "Rams", "Poke", 80, "Rohil", 80);

    Player[] players = {josh, stefan, rohil};

    public Loading(Main main) {
        JLabel label = new JLabel("Select a player");

        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(10,1));
        JButton advance = new JButton("Advance");
        advance.setPreferredSize(new Dimension(100,100));
        advance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player != null) main.newGame();
                else label.setText("You must select a player to advance");

            }
        });

        JPanel panel2 = new JPanel();
        panel2.setOpaque(true);
        panel2.setBackground(Color.blue);

        for (int i = 0; i < 3; i++) {
            JButton button = new JButton(players[i].name);
            button.setPreferredSize(new Dimension(100,100));
            int finalI = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    player = players[finalI];
                    label.setText("You have selected: " + players[finalI].name);
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

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, label);
        splitPane.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
        splitPane.setDividerLocation(WIDTH/2);
        splitPane.setDividerSize(0);


        JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane, advance);
        splitPane2.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
        splitPane2.setDividerLocation(WIDTH/2);
        splitPane2.setDividerSize(0);

        this.add(splitPane2);
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
    }
}

