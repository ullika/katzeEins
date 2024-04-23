import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class View {
    Game game;
    JFrame frame;
    BoardPanel boardPanel;
    JLabel infoLabel;
    JLabel summaryLabel;
    JLabel catInfoLabel;
    ActionListener[] al;

    public View(Game game) {
        setGame(game);
    }

    void setGame(Game game) {
        this.game = game;
    }

    public void addActionListener(ActionListener[] al) {
        this.al = al;
    }

    void update() {
        this.frame.getContentPane().remove(boardPanel);
        this.boardPanel = new BoardPanel(game.board.colors, game.board.patterns,
                game.board.colorClique, game.activeConstraints, game.deck, game.display);
        this.frame.getContentPane().add(BorderLayout.CENTER, this.boardPanel);
        this.boardPanel.setVisible(true);
        this.boardPanel.repaint();
        String catInfo = "";
        for (Cat cat : game.activeCats) {
            catInfo = catInfo.concat(cat.name + ": ");
            for (int pattern : cat.patterns) {
                catInfo = catInfo.concat(boardPanel.patternNames[pattern].split("\\.")[0]).concat(" ");
            }
            catInfo = catInfo.concat("<br>");
        }
        this.catInfoLabel.setText(String.format(
                "<html> CAT INFO: <br>" +
                        catInfo +
                        "</html>"
        ));
        this.summaryLabel.setText(String.format(
                "<html>" +
                        "Game finished! <br> Total points: %d <br>" +
                        "</html>", game.points()));
        this.infoLabel.setText(String.format(
                "<html>" +
                        "Cats: %s <br>" +
                        "Constraints: %s<br><br>" +
                        "Cat Points: %d <br> " +
                        "Flower Points: %d <br> " +
                        "Constraint Points: %d <br><br>" +
                        "Rainbow progress: <br>%s" +
                        "</html>", Arrays.toString(game.activeCats),
                Arrays.toString(game.activeConstraints),
                game.catPoints,
                game.flowerPoints,
                game.constrPoints, Arrays.toString(game.rainbow)));
        this.summaryLabel.setVisible(game.finished());
        this.frame.getContentPane().revalidate();
    }

    void init() {


        this.frame = new JFrame("Katze1");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(800, 700);
        this.boardPanel = new BoardPanel(game.board.colors, game.board.patterns,
                game.board.patternClique, game.activeConstraints, game.deck, game.display);
        this.infoLabel = new JLabel();
        this.catInfoLabel = new JLabel();
        this.summaryLabel = new JLabel();
        JButton newGameButton = new JButton("New Game");
        JButton resetButton = new JButton("Reset");
        JButton nextButton = new JButton("Next Move!");

        nextButton.addActionListener(al[0]);
        resetButton.addActionListener(al[1]);
        newGameButton.addActionListener(al[2]);

        this.frame.getContentPane().add(BorderLayout.CENTER, this.boardPanel);

        JPanel infopanel = new JPanel(new BorderLayout());
        infopanel.add(BorderLayout.NORTH, this.summaryLabel);
        infopanel.add(BorderLayout.CENTER, this.infoLabel);
        infopanel.add(BorderLayout.SOUTH, this.catInfoLabel);
        this.frame.getContentPane().add(BorderLayout.EAST, infopanel);


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(BorderLayout.EAST, newGameButton);
        buttonPanel.add(BorderLayout.CENTER, resetButton);
        buttonPanel.add(BorderLayout.WEST, nextButton);
        this.frame.getContentPane().add(BorderLayout.SOUTH, buttonPanel);


        this.boardPanel.setVisible(true);
        frame.setVisible(true);


    }
}
