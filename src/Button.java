/**
 * This class is used to create the buttons that appear on the
 * loading screen so the user can pick their team.
 **/

import javax.swing.*;
import java.awt.image.BufferedImage;
/** This class extends JButton so that we can create instances of Button (individual buttons) throughout*/
public class Button extends JButton {
    public Button(BufferedImage image, Loading loading, int buttonNum) {
        image = Display.resize(image, 228,228);
        setIcon(new ImageIcon(image));
        addActionListener(e -> {
            try {
                loading.team = Loading.teams.get(buttonNum);
                loading.label.setIcon(new ImageIcon(Display.lineupCard(loading.team)));
            } catch (Exception ignored) {}
        });
    }
}
