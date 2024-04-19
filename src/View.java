import javax.swing.*;
import java.awt.*;

public class View {
    Game game;
    public View(Game game) {
        this.game=game;
    }

    void update() {


                JFrame frame = new JFrame("Katze1");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600,600);
                JPanel boardPanel = new BoardPanel(game.board.colors, game.board.patterns);
                //JButton button = new JButton("Press");

                frame.getContentPane().add(boardPanel);
                boardPanel.setVisible(true);
                frame.setVisible(true);


    }
}
