public class Move {
    public int deckpos; // which card from the deck is chosen
    public int fieldpos; // where it is placed
    public int displaypos; //which card to take from the display

    public Move(int deckpos,int fieldpos,int displaypos) {
        this.deckpos=deckpos;
        this.fieldpos=fieldpos;
        this.displaypos=displaypos;
    }


}
