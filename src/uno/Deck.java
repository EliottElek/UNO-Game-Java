package uno;
/*Every bit of this project was made before realizing 
that types of classes and some instructions were given in the word doc.
In the end, the overall structure is almost the same as the one given.
Hope this will still work good for you.
*/
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Vector;
import java.util.Random;

public class Deck implements constInterface {

    private Vector<Card> deck = new Vector<>();//every deck has 108 cards.

    public Deck() {
    }
    public void createDeck() {
        for (int j = 0; j < 4; j++)//creating colored and numbered cards
        {
            for (int i = 0; i < 9; i++) {
                for (int k = 0; k < 2; k++) {
                    numberedCard card = new numberedCard(colors[j], Integer.toString(i + 1));
                    deck.addElement(card);
                }
            }
            // creating the 0 cards
            numberedCard card0 = new numberedCard(colors[j], Integer.toString(0));
            deck.addElement(card0);
        }//end of creating each card of numbered colors
        for (int j = 0; j < 4; j++)//creating colored and signed cards
        {
            for (int i = 0; i < 3; i++) {
                for (int k = 0; k < 2; k++) {
                    signedCard card = new signedCard(colors[j], colorattributs[i]);
                    deck.addElement(card);
                }
            }
        }//end of creating colored and signed cards
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                signedCard card = new signedCard("special", specialattributs[j]);
                deck.addElement(card);
            }
        }
    }

    public void showDeck() {
        for (int i = 0; i < deck.size(); i++) {
            deck.elementAt(i).showCard();
        }
    }

    public int getSize() {
        return deck.size();
    }

    public void shuffleDeck() {
        Random random = new Random();
        int nb;
        nb = 5 + random.nextInt(20 - 5);
        for (int i = 0; i < nb; i++) {
            shuffleDeckOnce();
        }
    }

    public Card getCard(int i) {
        return deck.elementAt(i);
    }

    public void removeCard(int i) {
        deck.removeElementAt(i);
    }

    public void addCard(Card card) {
        deck.addElement(card);
    }
    public Card returnLastCard()
    {
        return this.getCard(this.getSize()-1);
    }
    public void deleteDeck()
    {
        this.deck.removeAllElements();
    }

    public void shuffleDeckOnce() {
        Vector<Card> firstHalf = new Vector<>();
        Vector<Card> secondHalf = new Vector<>();
        Vector<Card> shuffledVector = new Vector();
        int index1 = 0;
        int index2 = 0;
        boolean first = true;
        for (int i = 0; i < numberofcards / 2; i++) {
            firstHalf.addElement(deck.elementAt(i));
        }
        for (int i = (numberofcards / 2)-1; i < numberofcards; i++) {
            secondHalf.addElement(deck.elementAt(i));
        }
        // we here have separated the deck vector in two halves
        //the next thing we need to do is shuffle these two
        for (int i = 0; i < deck.size(); i++) {
            if (first) {
                shuffledVector.add(firstHalf.elementAt(index1));
                index1 = index1 + 1;
                first = false;
            } else if (!first) {
                shuffledVector.add(secondHalf.elementAt(index2));
                index2 = index2 + 1;
                first = true;
            }
        }
        this.deck = shuffledVector;
    }

}
