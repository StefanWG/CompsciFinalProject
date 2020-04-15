import javax.swing.*;
import java.awt.*;

public class Loading extends JPanel {
    final int WIDTH = 800;
    final int HEIGHT = 900;

    public Loading (){
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
    }

    @Override
    public void paintComponent(Graphics g) {

        g.drawString("josh", 100, 450);
        g.drawString("Rohil",300,450 );
        g.drawString("Stefan",500,450);
        g.drawString("RandomPlayer",700,450 );
        g.drawString("Player", 700, 470);


    }





}
