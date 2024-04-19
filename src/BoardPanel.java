import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {
    int nHex;
    Color[] colors;
    int[] patterns;
    Color[] colorValues = {
            Color.decode("#5FA8AE"),
            Color.decode("#7A9A48"),
            Color.decode("#C42D6F"),

            Color.decode("#872D88"),
            Color.decode("#35368C"),

            Color.decode("#F8B537")
    };

    public BoardPanel(int[] colors,int[] patterns) {
        super();
        this.colors = new Color[colors.length];
        for (int i=0;i<colors.length;i++
             ) {

            if (colors[i] >= 0) {
                this.colors[i] = colorValues[colors[i]];
            } else {
                this.colors[i] = Color.WHITE;
            }

        }
        this.patterns = patterns;


    }



    public void paint(Graphics g) {
        int xPos=100;int yPos=100;int side=30;
        g.setColor(Color.RED);


        for (int i=0;i<6;i++
             ) {
            g.setColor(colors[i]);
            g.fillPolygon(makeHex(100+70*i,100,35));
            g.drawString(String.valueOf(i),100+70*i,100);
        }
        for (int i=0;i<7;i++
        ) {
            g.setColor(colors[6+i]);
            g.fillPolygon(makeHex(65+70*i,160,35));
        }
        for (int i=0;i<3;i++
        ) {
            g.setColor(colors[13+i]);
            g.fillPolygon(makeHex(30+70*i,220,35));
        }
            g.setColor(Color.BLACK);
            g.fillPolygon(makeHex(30+70*3,220,35));
        for (int i=3;i<6;i++) {
            g.setColor(colors[13+i]);
            g.fillPolygon(makeHex(30+70*(i+1),220,35));
        }

        for (int i=0;i<4;i++
        ) {
            g.setColor(colors[19+i]);
            g.fillPolygon(makeHex(65+70*i,280,35));
        }
        g.setColor(Color.BLACK);
        g.fillPolygon(makeHex(65+70*4,280,35));
        for (int i=4;i<6;i++
        ) {
            g.setColor(colors[19+i]);
            g.fillPolygon(makeHex(65+70*(i+1),280,35));
        }

        for (int i=0;i<2;i++
        ) {
            g.setColor(colors[25+i]);
            g.fillPolygon(makeHex(30+70*i,340,35));
        }
        g.setColor(Color.BLACK);
        g.fillPolygon(makeHex(30+70*2,340,35));
        for (int i=2;i<6;i++
        ) {
            g.setColor(colors[25+i]);
            g.fillPolygon(makeHex(30+70*(i+1),340,35));
        }
        for (int i=0;i<7;i++
        ) {
            g.setColor(colors[31+i]);
            g.fillPolygon(makeHex(65+70*i,400,35));
        }
        for (int i=0;i<6;i++
        ) {
            g.setColor(colors[38+i]);
            g.fillPolygon(makeHex(100+70*i,460,35));
        }


     }
     private Polygon makeHex(int xPos,int yPos,int side) {
         Polygon h = new Polygon();
         for (int i = 0; i < 6; i++){
             h.addPoint((int) (xPos + side * Math.sin(i * 2 * Math.PI / 6)),
                     (int) (yPos + side * Math.cos(i * 2 * Math.PI / 6)));
         }
         return h;
     }
}
