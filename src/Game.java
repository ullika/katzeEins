import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

public class Game implements Cloneable{

    Board board;

    Stack<Card> cardStack;
    Cat[] activeCats;
    Constraint[] activeConstraints;

    Move[] moves;
    Stack<Card> originalStack;

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
    public Game(Board board, Cat[] activeCats, Constraint[] activeConstraints, int nPositions,int nPatterns,int nColors) {
        this.board=board;
        this.activeCats=activeCats;
        this.nPatterns=nPatterns;
        this.nColors=nColors;
        this.activeConstraints = activeConstraints;
        reset(newStack(6,6));
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void reset(Stack<Card> stack) {
        flowerPoints=0;
        catPoints=0;
        constrPoints=0;
        for (Constraint ac:
        activeConstraints) {
            ac.reset();
        }

        rainbow = new boolean[nColors];
        for (int i=0;i<nColors;i++
             ) {
            rainbow[i]=false;
        }
        cardStack = stack;
        originalStack= (Stack<Card>) stack.clone();
        moves = new Move[board.n-board.nFixed];

        deck = new Card[]{getFromStack(), getFromStack()};
        display=new Card[]{getFromStack(), getFromStack(), getFromStack()};
        notflower = new boolean[board.n];
        notcat = new boolean[board.n];
        for (int i = 0; i < board.n; i++) {
            this.notcat[i]=true;
            this.notflower[i]=true;
        }

    }

    public Game copy() {
        Game copy = new Game(board.copy(),activeCats,this.activeConstraints.clone(),board.n,nPatterns,nColors);
        copy.cardStack= (Stack<Card>) cardStack.clone();
        copy.moves= moves.clone();
        copy.originalStack=(Stack<Card>) originalStack.clone();
        copy.catPoints=catPoints;
        copy.constrPoints=constrPoints;
        copy.flowerPoints=flowerPoints;
        copy.notcat=notcat.clone();
        copy.notflower=notflower.clone();
        copy.rainbow=rainbow.clone();
        copy.display=display.clone(); // cards to choose from after each move
        copy.deck=deck.clone(); // cards you hold in your hand
        return copy;
    }

    public Stack<Card> newStack(int nColors,int nPatterns) {
        cardStack = new Stack<>();
        for (int color = 0; color < nColors; color++) {
            for (int pattern = 0; pattern < nPatterns; pattern++) {
                cardStack.add(new Card(color, pattern));
                cardStack.add(new Card(color, pattern));
                cardStack.add(new Card(color, pattern));
            }
            Collections.shuffle(cardStack);

        }
        return cardStack;
    }
    public void move(Move move) {
  //      System.out.println(cardStack.size());
        moves[board.n - board.nFixed - board.nEmpty] = move;
        int[] cliques=board.update(move.fieldpos,deck[move.deckpos]); //returns array of the form (pattern,patternClique,color,colorClique)

        this.updateDeck(move);
        this.updateDisplay(move);

        // this depends on the number of players
        display[ThreadLocalRandom.current().nextInt(0, 3)] = getFromStack();
        display[ThreadLocalRandom.current().nextInt(0, 3)] = getFromStack();





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
  //                  System.out.println("Constraint 100% fulfilled");
                    return;
                }
                if (colorFulfilled || patternFulfilled) {
                    constr.setFulfilled(constr.getPointsHalf());
                    constrPoints+=constr.getPointsHalf();
 //                   System.out.println("Constraint 50% fulfilled");
                    return;
                }
                constr.setFulfilled(0);
  //              System.out.println("Constraint not fulfilled");
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

        if (colorCliqueMembers.size()>2) {
            for (int member:colorCliqueMembers
            ) {
                if (!notflower[member]) {
                    return;
                }
            }
            flowerPoints+=3;
//            System.out.println("Found a flower!");

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
//                System.out.println("Rainbow is complete!!");
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

    void reset() {


    }

    private void placeCat(Cat cat,int[] space) {
        catPoints += cat.getPoints();
//        System.out.println(String.format("Found a cat (%s)!!",cat.toString()));
    }



    int points() {
        return catPoints+constrPoints+flowerPoints;
    };


    private void updateDeck(Move move) {
        deck[move.deckpos]=display[move.displaypos];
    }
    private void updateDisplay(Move move) {
        display[move.displaypos]= getFromStack();
    }

    private Card getFromStack() {
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
