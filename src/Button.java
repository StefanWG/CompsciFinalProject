/**
 * This class is used to create the buttons that appear on the
 * loading screen so the user can pick their team.
 **/

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Button extends JButton {
    public Button(BufferedImage image, Loading loading, int finalI) {
        image = Display.resize(image, 228,228);
        setIcon(new ImageIcon(image));
        addActionListener(e -> {
            try {
                loading.team = Loading.teams.get(finalI);
                loading.label.setIcon(new ImageIcon(Display.lineupCard(loading.team)));
            } catch (Exception ignored) {}
        });
    }
}
