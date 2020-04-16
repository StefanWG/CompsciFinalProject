import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Button extends JButton {
    public Button(BufferedImage image, Loading loading, int finalI) {
        image = Display.resize(image, 228,228);
        setIcon(new ImageIcon(image));
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    loading.player = loading.players[finalI];
                    loading.textArea.setText("You have selected: \n" + loading.players[finalI].toString());
                } catch (Exception ignored) {}
            }
        });
    }
}
