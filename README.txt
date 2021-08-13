*****PLEASE READ BEFORE PLAY*****
**In this folder is added the .exe version of the game.**
UFO game by Eliott Morcillo and Apolline Cherrey 
both in TD05

This game is a derived game from the famous UNO game.
We did not only want to recreate the famous game, but we also wanted
to give it a bit of a unique style.

What is UFO ?
UFO is a UNO game based around the theme of UFO(Unidentified Flying Object).
This should normally explain the aliens on the screen...
All rules are exactly the sames as the original game.

How to play ?
you can either chose to play against one, two, or three opponents.
Your opponents have to be humans players.
You almost only use you mouse to play. The only time you have to use you keyboard 
is when you have to type in the color you want to play when playing a wild or wild four.
Every time a player has to play, his deck is visible, and the others are not.
When clicking on one of his cards, if the card can be used, it is picked and added to his hand;
otherwise if it can not be used nothing happens.
He can chose to play the same color as the card on the playing deck, the same number or 
attribut, a wild or a wild four. Just like the real UNO game, a player can play multiple 
cards of the same attribut. He can then, if he has the appropriate card(s), decide to add them 
to his hand. When he's ready to play, he can then click on his hand to play the cards. He has
to click as much as he has cards in his hand(if he has two for example, he has to click two times).
When all cards from player's deck are added to the game deck, his deck is now not visible anymore,
and the deck of the next player is visible, and he can then play as well.
If the player doesn't have any card that can be played, he has to draw a card, 
by clicking on the drawing deck (also called starting deck).
If the picked card can be used, the game automatically plays it. Otherwise, it is added to the player's deck,
and his turn is skipped.
If a player plays a skip and there are more than two players, the next player's turn is
skipped.
If a player plays a reverse and there are more than two players, the rotation is reversed.
If a player plays a skip and there are only two players, he can play again, same thing for a reverse.
The winner of the game is the one who doesn't have any cards left in his deck.
At the end of a game, the score is showed, along with the overall ranking. At this point, the user can
chose to go back to the menu and play another game or exit.
At any time, the user can chose to exit or go mack to menu.

////CODE EXPLANATION////

*abstract Class Card*
This game has an abstract class called Card.
A card has :
-coordinates(to draw it)->(integers)
-a color->(String)
-a width and height->(integers)
-a boolean to show it or hide it->(boolean)
-a boolean to indicate if it can be played or not->(boolean)
Has a method called getColor() -> returns the color.
Has an abstract method called getAttribut().
This method is @Override in numberedCard and signedCard.

*class numberedCard*
extends Card.
Has an added attribut called number.
@Override abstract method getAttribut()-returns it's sign.

*class signedCard*
extends Card.
Has an added attribut called sign.
@Override abstract method getAttribut()->returns it's number.

*class Deck*
This game has a class called Deck and implements ConstInterface.
a deck has :
-a vector of cards->(Vector<Card>)
Has a method that creates each 108 cards called createDeck().
Has a method that shuffles the deck once (just like in tutorial 4) called shuffleOnce().
Has a method that shuffles the deck calling n times (random) the previous method(just like in tutorial 4)
called shuffleDeck().

*class Player*
This game has a class called Player.
a player has :
-a name->(String)
-a deck(Object Deck)
-a counter of points->(integer)
Has classical getters and setter : getName(), addPoint(),...
Has methods to display infos for debugging.

*Interface ConstInterface*
Inteface containing every final constants of the game.
this includes : the number of total cards, the number of cards per player at the 
beginning, the different colors, the different attributs.

*class Game*
this game has a class called Game. 
this class extends JPanel and implements ConstInterface, MouseListener and ActionListener.
a game has :
-dimensions of the screen->(integers) 
-a deck1 (draw deck or starting deck)->(Object Deck)
-a deck2 (playing deck)->(Object Deck)
-a deck3 (player's hand)->(Object Deck)
-a list of players->(Vector<Player>)
-a ranking->(Vector<Player>)
-a playernumber (index of the player in the list)->(integer)
-coordinates of the mouse->(Integers)
-an actual color->(String)
-multiple booleans indicating if the player has played a skip, a reverse,...->(booleans)
-couple of images->(BufferedImage)
Has a method called drawCard() that uses Graphics to draw a card depending on :
its color, attribut, and if it's hidden or not.
Has a method called drawOnePlayerDeck() that calls the drawCard method to draw every card
in the player's hand, hidden or not, depending on the boolean received in parameter.
Has a @Override paint() method that calls every drawing method.
Has a method called showPlayersHand() that calls the drawCard() method to draw the 
cards from the player's hand.
Has a method called showStartingDeck() that calls the drawCard() method to draw the 
starting deck of the game.
Has a method called showGameDeck() that calls the drawCard() method to draw the 
game deck.
Has a method called showPlayersDecks() that calls the drawOnePlayerDeck() method and draw the deck visible 
for the actual player and hidden for other players.
Has a method called drawAlienBubble() that draw a bubble for him to talk.
Has a method playOnce() that is called everytime a player wants to play a card. It receives
3 decks in parameters : an actual, target and final deck. It also receives an index(integer) that
correspond to the index of the card in the player's deck. Finally it receives two booleans :
these booleans indicate if we want to play comparing the color and the attribut, or only the attribut.
This method checks on the target deck if the card selected in the actual deck can be played. If it's color and attribut
depending, if it can be played, it's added to the final deck. Later, this function is called 
with deck1, deck2 and deck3.
this function also looks for skips, reverses, draw four and wilds, and adds cards to other players if needed,
reverses the rotation, skippes one player,...
Has a method called draw called when a player clicks on the starting deck.
Has a function called getMenuChoice() that, depending on where the mouse is clicked, retuns an integer later used in MouseClicked() method.
Has a function called getCardAtMouse() that receives a Player object in parameters. If the mouse is clicked on one of his cards (using cards coordinates)
it returns it's index in the vector of cards. If the top card of the draw deck is clicked (moving as the number of cards reduces)
it returns -2. Returns -3 if the player clicks ont his hand to play the cards.
Has a method called setColor() taht receives a String object in parameters. Uses the setColor() method to set the color of Graphics depending on the 
String received.
Has a method called setInitialCardsToPlayers() that randomly picks card from the starting deck and adds them to the players' decks. This 
method is called at the beggining to give cards to players.
Has a @Override mouseClicked() method that, depending on the booleans indicating if the player has played skips, reverses, draw four of wilds,
calls the playOnce() method and the index received itn the parameters of playOnce() method is the getCardAtMouse() itself.

*class Main*
The main class creates a JFrame wih the dimensions of the screen, et makes it take tuhe full screen with setUndecorated() method.
Creates a Game object and adds it to the frame. Finally, it calls the method setInitialCardsToPlayers().











