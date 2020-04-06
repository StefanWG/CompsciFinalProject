import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel {
    public Player player;
    final int WIDTH = 1000;
    final int HEIGHT = 1000;

    ContactMeter cm;

    public Game() {
        //Code for picking player
        setPreferredSize(new Dimension(WIDTH,HEIGHT));

        cm = new ContactMeter(300,300, this);

//        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"),"STOP");
//        this.getActionMap().put("STOP", new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                System.out.println("here");
//            }
//        });

    }

    public void runGame() {
        cm.runMeter();
    }

    @Override
    public void paintComponent(Graphics g) {
        cm.draw(g);
    }
}
