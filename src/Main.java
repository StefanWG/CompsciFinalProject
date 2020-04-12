import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Baseball");
        Game game = new Game();
        frame.setContentPane(game);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        game.pickPlayer();
        game.runGame();
    }
}
