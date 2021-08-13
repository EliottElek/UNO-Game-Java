package uno;
/*Every bit of this project was made before realizing 
that types of classes and some instructions were given in the word doc.
In the end, the overall structure is almost the same as the one given.
Hope this will still work good for you.
*/
import java.util.Vector;
/*
each player has a name, an object Deck, and a boolean that indicates if the player has played or not.
*/
public class Player {

    private String name;
    private Deck playerDeck = new Deck();
    private boolean hasplayed = false;
    private int points;
    //constructor
    public Player(String name) {
        this.name = name;
        this.points = 0;
    }
    //getter of the name
    public String getName() {
        return this.name;
    }
    //setter of the name
    public void setName(String name)
    {
        this.name = name;
    }
    //adds a card to the deck
    public void addCardToPlayer(Card card) {
        this.playerDeck.addCard(card);
    }
    //shows the deck of the player, in the console
    public void showPlayerDeck() {
        this.playerDeck.showDeck();
    }
    //returns the deck of the player
    public Deck retunPlayerDeck() {
        return this.playerDeck;
    }
    //sets the boolean that indicates if the players has played or not
    public void setHasPlayed(boolean bool)
    {
        this.hasplayed=bool;
    }
    //returns this previous boolean
    public boolean hasPlayed()
    {
        return this.hasplayed;
    }
    public void addPoints(int points)
    {
        this.points = this.points + points;
    }
    public int getPoints()
    {
        return this.points;
    }
    
    
}
