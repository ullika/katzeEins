import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BoardPanel extends JPanel {
    int nHex;
    Color[] colors;
    int[] cliqueIDs;

    String[] constr;

    Card[] deck;
    Card[] display;
    TexturePaint[] textures;

    BufferedImage dots;
    Color[] colorValues = {
            Color.decode("#5FA8AE"),
            Color.decode("#7A9A48"),
            Color.decode("#C42D6F"),
            Color.decode("#872D88"),
            Color.decode("#35368C"),
            Color.decode("#F8B537")
    };

    String directory="/home/ullika/projects/katzeEins/img/";
    String[] patternNames={
            "floral.png",
            "dots.png",
            "lines.png",
            "palm.png",
            "tiling.png",
            "ornamental.png",
            "transparent.png"
    };

    BufferedImage[] images;



    public BoardPanel(int[] colors,int[] patterns,int[] cliqueIDs,Constraint[] activeConstraints,
                      Card[] deck, Card[] display) {
        super();
        this.colors = new Color[colors.length];
        this.cliqueIDs = cliqueIDs;
        this.deck=deck;
        this.display = display;
        images = new BufferedImage[patternNames.length];
        for (int i=0;i<patternNames.length;i++) {
            try {
            images[i] = ImageIO.read(new File(directory.concat(patternNames[i])));
            } catch (IOException e) {
            // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        for (int i=0;i<colors.length;i++
             ) {

            if (colors[i] >= 0) {
                this.colors[i] = colorValues[colors[i]];
            } else {
                this.colors[i] = Color.WHITE;
            }

        }
        this.textures = new TexturePaint[patterns.length];
        for (int i = 0; i < patterns.length; i++) {
            if (patterns[i] >= 0) {
                this.textures[i]= new TexturePaint(images[patterns[i]], new Rectangle(0, 0, images[patterns[i]].getWidth(), images[patterns[i]].getHeight()));

            } else {
                this.textures[i] = new TexturePaint(images[6],new Rectangle(0,0,0,0));
            }
        }
        this.constr = new String[3];
        for (int i=0;i<activeConstraints.length;i++ ) {
            this.constr[i] = activeConstraints[i].toString();
        }


    }



    public void paint(Graphics g) {
        int xPos=100;int yPos=100;int side=30;
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.black);

        for (int i=0;i<6;i++
             ) {

            g.setColor(colors[i]);


            g.fillPolygon(makeHex(100+70*i,100,35));
            g2d.setPaint(textures[i]);
            g2d.fillPolygon(makeHex(100+70*i,100,35));
            g.drawString(String.valueOf(i),100+70*i,100);
            g.setColor(Color.black);
            g.drawString(String.valueOf(cliqueIDs[i]),100+70*i,100);
        }
        for (int i=0;i<7;i++
        ) {

            g.setColor(colors[6+i]);
            g.fillPolygon(makeHex(65+70*i,160,35));
            g2d.setPaint(textures[6+i]);
            g2d.fillPolygon(makeHex(65+70*i,160,35));
            g.setColor(Color.black);
            g.drawString(String.valueOf(cliqueIDs[6+i]),65+70*i,160);
        }
        for (int i=0;i<3;i++
        ) {
            g.setColor(colors[13+i]);
            g.fillPolygon(makeHex(30+70*i,220,35));
            g2d.setPaint(textures[13+i]);
            g2d.fillPolygon(makeHex(30+70*i,220,35));
            g.setColor(Color.black);
            g.drawString(String.valueOf(cliqueIDs[13+i]),30+70*i,220);
        }
            g.setColor(Color.BLACK);
            g.fillPolygon(makeHex(30+70*3,220,35));
            g.setColor(Color.WHITE);
            g.drawString(constr[0],15+70*3,220);

        for (int i=3;i<6;i++) {
            g.setColor(colors[13+i]);
            g.fillPolygon(makeHex(30+70*(i+1),220,35));
            g2d.setPaint(textures[13+i]);
            g2d.fillPolygon(makeHex(30+70*(i+1),220,35));
        }

        for (int i=0;i<4;i++
        ) {
            g.setColor(colors[19+i]);
            g.fillPolygon(makeHex(65+70*i,280,35));
            g2d.setPaint(textures[19+i]);
            g2d.fillPolygon(makeHex(65+70*i,280,35));
        }
        g.setColor(Color.BLACK);
        g.fillPolygon(makeHex(65+70*4,280,35));
        g.setColor(Color.WHITE);
        g.drawString(constr[2],40+70*4,280);
        for (int i=4;i<6;i++
        ) {
            g.setColor(colors[19+i]);
            g.fillPolygon(makeHex(65+70*(i+1),280,35));
            g2d.setPaint(textures[19+i]);
            g2d.fillPolygon(makeHex(65+70*(i+1),280,35));
        }

        for (int i=0;i<2;i++
        ) {
            g.setColor(colors[25+i]);
            g.fillPolygon(makeHex(30+70*i,340,35));
            g2d.setPaint(textures[25+i]);
            g2d.fillPolygon(makeHex(30+70*i,340,35));
        }
        g.setColor(Color.BLACK);
        g.fillPolygon(makeHex(30+70*2,340,35));
        g.setColor(Color.WHITE);
        g.drawString(constr[1],5+70*2,340);
        for (int i=2;i<6;i++
        ) {
            g.setColor(colors[25+i]);
            g.fillPolygon(makeHex(30+70*(i+1),340,35));
            g2d.setPaint(textures[25+i]);
            g2d.fillPolygon(makeHex(30+70*(i+1),340,35));
        }
        for (int i=0;i<7;i++
        ) {
            g.setColor(colors[31+i]);
            g.fillPolygon(makeHex(65+70*i,400,35));
            g2d.setPaint(textures[31+i]);
            g2d.fillPolygon(makeHex(65+70*i,400,35));
        }
        for (int i=0;i<6;i++
        ) {
            g.setColor(colors[38+i]);
            g.fillPolygon(makeHex(100+70*i,460,35));
            g2d.setPaint(textures[38+i]);
            g2d.fillPolygon(makeHex(100+70*i,460,35));
        }

        //deck
        g.setColor(colorValues[deck[0].color]);
        g.fillPolygon(makeHex(100,560,25));

        TexturePaint tp = new TexturePaint(images[deck[0].pattern], new Rectangle(0, 0, images[deck[0].pattern].getWidth(), images[deck[0].pattern].getHeight()));
        g2d.setPaint(tp);
        g2d.fillPolygon(makeHex(100,560,25));

        g.setColor(colorValues[deck[1].color]);
        g.fillPolygon(makeHex(150,560,25));

        tp = new TexturePaint(images[deck[1].pattern], new Rectangle(0, 0, images[deck[1].pattern].getWidth(), images[deck[0].pattern].getHeight()));
        g2d.setPaint(tp);
        g2d.fillPolygon(makeHex(150,560,25));

        // display
        g.setColor(colorValues[display[0].color]);
        g.fillPolygon(makeHex(250,560,25));

        tp = new TexturePaint(images[display[0].pattern], new Rectangle(0, 0, images[display[0].pattern].getWidth(), images[deck[0].pattern].getHeight()));
        g2d.setPaint(tp);
        g2d.fillPolygon(makeHex(250,560,25));

        g.setColor(colorValues[display[1].color]);
        g.fillPolygon(makeHex(300,560,25));
        tp = new TexturePaint(images[display[1].pattern], new Rectangle(0, 0, images[display[1].pattern].getWidth(), images[deck[0].pattern].getHeight()));
        g2d.setPaint(tp);
        g2d.fillPolygon(makeHex(300,560,25));

        g.setColor(colorValues[display[2].color]);
        g.fillPolygon(makeHex(350,560,25));
        tp = new TexturePaint(images[display[2].pattern], new Rectangle(0, 0, images[display[2].pattern].getWidth(), images[deck[0].pattern].getHeight()));
        g2d.setPaint(tp);
        g2d.fillPolygon(makeHex(350,560,25));


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
