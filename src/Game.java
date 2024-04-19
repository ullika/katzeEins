import java.util.*;

public class Game {
    Player player;
    Board board;

    Stack<Card> cardStack;
    Cat[] activeCats;
    Constraint[] activeConstraints;

    int nPatterns;
    int nColors;


    int catPoints;
    int constrPoints;
    int flowerPoints;

    boolean[] notcat;
    boolean[] notflower;

    boolean[] rainbow;

    Card[] display; // cards to choose from after each move
    Card[] deck; // cards you hold in your hand
    public Game(Board board, Cat[] activeCats, Constraint[] activeConstraints, Player player) {
        this.board=board;
        this.activeCats=activeCats;
        this.player=player;
        setup(activeCats.length,6,board.n);
    }

    public void setup(int nCats,int nColors,int nPositions) {
        flowerPoints=0;
        catPoints=0;
        constrPoints=0;
        nPatterns=nCats*2;
        this.nColors=nColors;
        rainbow = new boolean[nColors];
        for (int i=0;i<nColors;i++
             ) {
            rainbow[i]=false;
        }
        cardStack = new Stack<>();
        for (int color = 0; color < nColors; color++) {
            for (int pattern = 0; pattern < nPatterns; pattern++) {
                cardStack.add(new Card(color, pattern));
                cardStack.add(new Card(color, pattern));
                cardStack.add(new Card(color, pattern));
            }
            Collections.shuffle(cardStack);

        }
        deck = new Card[]{getRandomCard(), getRandomCard()};
        display=new Card[]{getRandomCard(), getRandomCard(),getRandomCard()};
        notflower = new boolean[nPositions];
        notcat = new boolean[nPositions];
        for (int i = 0; i < nPositions; i++) {
            this.notcat[i]=true;
            this.notflower[i]=true;
        }


    }
    public void move() {
        Move move=player.bestMove(board,deck,display); // one move is a tupel ((stone,field),stoneToTake)

        this.updateDeck(move);
        this.updateDisplay(move);
        int[] cliques=board.update(move.fieldpos,deck[move.deckpos]); //returns array of the form (pattern,patternClique,color,colorClique)

        updateCats(cliques[0],cliques[1]);
        updateFlowers(cliques[2], cliques[3]);
        updateConstr(move);
    };

    void updateConstr(Move move) {
        for (Constraint constr:activeConstraints
             ) {
            if (constr.dependsOn(move.fieldpos)&&!constr.isFulfilled()&& constr.determined(board.colors)) {
                boolean colorFulfilled=(constr.checkQuality(board.colors, nColors));
                boolean patternFulfilled=(constr.checkQuality(board.patterns, nPatterns));
                if (colorFulfilled&&patternFulfilled) {
                    constr.setFulfilled(constr.getPointsFull());
                    constrPoints += constr.getPointsFull();
                    return;
                }
                if (colorFulfilled || patternFulfilled) {
                    constr.setFulfilled(constr.getPointsHalf());
                    constrPoints+=constr.getPointsHalf();
                    return;
                }
                constr.setFulfilled(0);
            }
        }
    }

    void updateCats(int pattern, int cliqueID) {
        for (Cat cat:activeCats
        ) {
            if (cat.hasPattern(pattern)) {
                int[] space = findCatSpace(cat, cliqueID);
                if (space.length > 0) {
                    placeCat(cat, space);
                    for(int position:space) {
                        notcat[position] = false;
                    }
                }
            }

        }
    }

    void updateFlowers(int color, int cliqueID) {
        ArrayList<Integer> colorCliqueMembers=board.getColorCliqueMembers(cliqueID);

        if (colorCliqueMembers.size()>3) {
            for (int member:colorCliqueMembers
            ) {
                if (!notflower[member]) {
                    return;
                }
            }
            flowerPoints+=3;

            boolean isNew=!rainbow[color];
            rainbow[color]=true;

            boolean complete=true;
            for (boolean havecolor:rainbow
                 ) {
                if (!havecolor) {
                    complete=false;
                }
            }
            if (complete && isNew) {
                flowerPoints+=3;
            }


            for (int member:colorCliqueMembers) {
                notflower[member]=false;
            }
        }
    }


    Boolean finished(){
        return
                (0==board.nEmpty);

    }

    private void placeCat(Cat cat,int[] space) {
        catPoints += cat.getPoints();
    }



    int points() {
        return catPoints+constrPoints+flowerPoints;
    };
    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    private void updateDeck(Move move) {
        deck[move.deckpos]=display[move.displaypos];
    }
    private void updateDisplay(Move move) {
        display[move.displaypos]=getRandomCard();
    }

    private Card getRandomCard() {
        return cardStack.pop();
    }


    private int[] findCatSpace(Cat cat,int cliqueID) {
        ArrayList<Integer> clique=board.getPatternCliqueMembers(cliqueID);
        if (clique.size()<cat.getSize()) {
            return new int[]{};
            }; // clique too small to find a cat;
        for (int member:clique
             ) {
            if (!notcat[member]) { // clique is invaldid because it already hosts a cat
                return new int[]{};
            }
        }

        if (cat.isFlexible()) { //there is space for a flexible cat
            int[] space=new int[cat.getSize()];
            for (int i=0;i<cat.getSize();i++
                 ) {
                space[i] = clique.get(i);
            }
            return space;
        }

        //check if the clique has the right form for the nonflexible cat
        for (int pos:clique
             ) {
            for (int direction : cat.getDirections()
            ) {
                boolean possible=true;
                int[] required = cat.occupation(board, pos, direction);
                for (int requiredPos : required
                ) {
                    if (-1==requiredPos||!clique.contains((Integer) requiredPos)) {
                        possible=false;break;
                    }

                }
                if (possible) {
                    return required;
                }

            }
        } return new int[]{};
    }

}
