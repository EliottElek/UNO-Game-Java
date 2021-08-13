package uno;
/*Every bit of this project was made before realizing 
that types of classes and some instructions were given in the word doc.
In the end, the overall structure is almost the same as the one given.
Hope this will still work good for you.
*/

/*
Here we have the interface that gives acces to every constants of the game.
How many cards there are in total, how many cards every player has at the begining,
but also every colors and attributs that are not number.
this allows a much easier creation of the begginign deck.
*/
public interface constInterface {

    final static int numberofcards = 108;
    final static int nbCardsAtBeginning = 7;
    final static String[] colors = {"blue", "red", "yellow", "green", "special"};
    final static String[] colorattributs = {"Skip", "Draw Two", "Reverse"};
    final static String[] specialattributs = {"Wild+4", "Wild"};
}
