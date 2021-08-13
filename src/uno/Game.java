package uno;
/*Every bit of this project was made before realizing 
 that types of classes and some instructions were given in the word doc.
 In the end, the overall structure is almost the same as the one given.
 Hope this will still work good for you.
 */
/*
 this is the most important class of the program.
 The class Game is a class that extends a JPanel. In it, we have all methods that draw and play.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import static uno.Card.w;

public class Game extends JPanel implements constInterface, KeyListener, MouseListener, ActionListener {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenH = screenSize.height;
    int screenW = screenSize.width;
    //deck corresponding to the initial deck of cards
    private Deck deck = new Deck();
    //deck corresponding to the game deck, on which players put their cards
    private Deck deck2 = new Deck();
    //deck corresponding to the player's hand
    private Deck deck3 = new Deck();
    //declaration of a MouseListener 
    MouseListener ml;
    KeyListener kl;
    //declaration of the list of players using a vector
    private Vector<Player> listOfPlayers = new Vector<>();
    //declaration of the ranking 
    private Vector<Player> ranking = new Vector<>();
    //declaration of the number of the player (between 0 and listOfPlayers.size()-1)
    private int playernumber = 0;
    //declaration of a timer
    private Timer timer;
    //declaration of X and Y coordinates of the mouse
    private int mouseX;
    private int mouseY;
    //declaration of the variable corresponding to the actual color to play.
    private String actualcolor;
    //boolean that indicates if we are going in reverse or not
    private boolean reverse = false;
    //boolean that indicates if the player has played a skip
    private boolean skip = false;
    //boolean that indicates if the player has played a +4
    private boolean wildfour = false;
    //boolean that indicates if the player has played a +2
    private boolean drawtwo = false;
    //declaration of the delay for the timer
    private final int delay = 8;
    //declaration of both images we are using for display
    private boolean end = false;
    private boolean gamestart = false;
    private boolean rules = false;
    BufferedImage ufo;
    BufferedImage alien;
    BufferedImage menu;
    BufferedImage rul;

    //constructor of Game
    public Game() throws IOException {
        //allows us to read both images and adding everything to the Game
        this.ufo = ImageIO.read(this.getClass().getResource("ufo.gif"));
        JLabel UFO = new JLabel(new ImageIcon(ufo));
        this.add(UFO);
        this.alien = ImageIO.read(this.getClass().getResource("alienVF.gif"));
        JLabel ALIEN = new JLabel(new ImageIcon(alien));
        this.add(ALIEN);
        this.menu = ImageIO.read(this.getClass().getResource("menuVF.gif"));
        JLabel MENU = new JLabel(new ImageIcon(menu));
        this.add(MENU);
        this.rul = ImageIO.read(this.getClass().getResource("rules.gif"));
        JLabel RULES = new JLabel(new ImageIcon(rul));
        this.add(RULES);
        this.addMouseListener(this);
        this.addMouseListener(ml);
        this.addKeyListener(this);
        this.addKeyListener(kl);
        this.setFocusable(true);
        this.requestFocus();
        this.deck = deck;
        addKeyListener(this);
        // setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        //starting the timer
        timer.start();
    }

    //overriding the method paint of JPanel 
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.black);
        if (!this.gamestart) {
            g.drawImage(this.menu, 0, 0, screenW, screenH, this);
            if (this.rules) {
                g.drawImage(this.rul, 0, 0, screenW, screenH, this);
                g.setColor(Color.black);
                if (getMenuChoice() == 5) {
                    this.rules = false;
                }
            }

        }

        if (this.gamestart) {
            g.drawImage(this.alien, 0, 0, screenW, screenH, this);
            if (!this.end) {
                //drawAlienBubble(g, true);
                //if we still have two players or more
                System.out.println("size: " + this.listOfPlayers.size());
                if (this.listOfPlayers.size() > 1) {
                    //we draw the starting deck
                    showStartingDeck(g);
                    System.out.println("player number:" + this.playernumber);
                    //we draw the game deck
                    showGameDeck(g);
                    //we draw the players's decks
                    showPlayersDecks(g);
                    //we draw the player's hand
                    showPlayersHand(g);
                    //showing the infos of the player as long as he still has clards
                    //the alien screams that the player is UFO
                    if (this.listOfPlayers.elementAt(this.playernumber).retunPlayerDeck().getSize() == 1) {
                        drawAlienBubble(g, false);
                        g.setColor(Color.black);
                        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
                        g.drawString(this.listOfPlayers.elementAt(this.playernumber).getName() + " IS UFO ! ", 1190, 260);
                        //the alien is thinking inside his head that the players is almost UFO...
                    } else if (this.listOfPlayers.elementAt(this.playernumber).retunPlayerDeck().getSize() == 2) {
                        drawAlienBubble(g, true);
                        g.setColor(Color.black);
                        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
                        g.drawString(this.listOfPlayers.elementAt(this.playernumber).getName() + " has only 2 cards left... ", 1120, 260);
                    } else if (listOfPlayers.elementAt(this.playernumber).retunPlayerDeck().getSize() == 0) {
                        //else we remove him/her from the list, and had it to the ranking list
                        this.ranking.add(this.listOfPlayers.elementAt(this.playernumber));
                        listOfPlayers.removeElementAt(this.playernumber);
                        if (this.playernumber == this.listOfPlayers.size()) {
                            if (this.reverse) {
                                this.playernumber = 0;
                            } else if (!this.reverse) {
                                this.playernumber = this.listOfPlayers.size() - 1;
                            }
                        }
                    } else {
                        showInfos(g, this.listOfPlayers.elementAt(this.playernumber));
                    }
                    //if there is only one player left we sho the ranking

                } else if (this.listOfPlayers.size() == 1) {
                    this.end = true;
                }

            } else if (this.end) {
                if (!this.listOfPlayers.isEmpty()) {
                    this.ranking.add(this.listOfPlayers.elementAt(0));
                    this.listOfPlayers.remove(this.listOfPlayers.elementAt(0));
                }
                for (int i = 0; i < this.ranking.size(); i++) {
                    g.setColor(Color.white);
                    g.setFont(new Font("TimesRoman", Font.PLAIN, 48));
                    g.drawString(" RANKING", 550, 180);
                    g.drawString(" POINTS", 900, 180);
                    g.drawString((i + 1) + "-" + this.ranking.elementAt(i).getName(), 550, 250 + (i * 70));
                    g.drawString(Integer.toString(this.ranking.elementAt(i).getPoints()), 900, 250 + (i * 70));
                    //this.end = false;
                }
                //this.listOfPlayers.remove(this.listOfPlayers.elementAt(0));
            }
        }

    }

    //draws the players hand
    public void showPlayersHand(Graphics g) {
        int offset = 570;
        int decalage = listOfPlayers.size() * 190;//Y coordinate offset depending on the number of players
        g.setColor(Color.white);
        if (this.deck3.getSize() > 0) {
            g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
            g.drawString("player hand (" + this.deck3.getSize() + " cards)", 1200, 75 + decalage);
            for (int i = 0; i < this.deck3.getSize(); i++) {
                this.deck3.getCard(i).setHidden(false);
                drawCard(g, offset, this.deck3.getCard(i), decalage);
                offset = offset + 8;
            }
        }

    }

    //draws the starting deck, which is also the deck from which players draw cards
    public void showStartingDeck(Graphics g) {
        int offset = 300;
        int decalage = this.listOfPlayers.size() * 190;
        g.setColor(Color.white);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        g.drawString("draw (" + this.deck.getSize() + " cards left)", 750, 42 + decalage);
        for (int i = 0; i < this.deck.getSize(); i++) {
            //every card is hidden
            this.deck.getCard(i).setHidden(true);
            drawCard(g, offset, this.deck.getCard(i), decalage);
            g.setColor(Color.black);
            offset = offset + 2;
        }
    }

    //drawss the deck on which players put their cards
    public void showGameDeck(Graphics g) {
        int offset = 0;
        int decalage = listOfPlayers.size() * 190;
        if (this.deck2.getSize() == 0) {
            Random random = new Random();
            int nb;
            nb = random.nextInt(this.deck.getSize() - 1);
            this.deck.getCard(nb).setHidden(false);
            this.actualcolor = this.deck.getCard(nb).getColor();
            this.deck2.addCard(this.deck.getCard(nb));
            this.deck.removeCard(nb);
        }
        g.setColor(Color.white);
        g.drawString("Game deck (" + this.deck2.getSize() + " cards)", 50, 42 + decalage);
        for (int i = 0; i < this.deck2.getSize(); i++) {
            this.deck2.getCard(i).setHidden(false);
            drawCard(g, offset, this.deck2.getCard(i), decalage);
            offset = offset + 5;
        }

    }

    //shows every deck of every player, but only showing for the actual player
    //decks of players who don't play are hidden
    public void showPlayersDecks(Graphics g) {
        int decalage = 0;
        for (int i = 0; i < listOfPlayers.size(); i++) {
            if (i == this.playernumber) {
                g.setColor(Color.blue);
                drawOnePlayerDeck(g, decalage, this.listOfPlayers.elementAt(i), false);
            } else {
                drawOnePlayerDeck(g, decalage, this.listOfPlayers.elementAt(i), true);
            }
            decalage = decalage + 190;
        }
    }

    //draws one player deck
    public void drawOnePlayerDeck(Graphics g, int decalage, Player player, boolean hidden) {
        int offset = 0;
        int xName = 50;
        int yName = 25;
        g.setColor(Color.white);
        g.drawString("Deck of player " + player.getName(), xName, yName + decalage + 16);
        for (int i = 0; i < player.retunPlayerDeck().getSize(); i++) {
            player.retunPlayerDeck().getCard(i).setHidden(hidden);
            drawCard(g, offset, player.retunPlayerDeck().getCard(i), decalage);
            offset = offset + 75;
        }
    }

    //draws the bubble of the alien, weither he's thinking or screaming
    public void drawAlienBubble(Graphics g, boolean thinking) {
        g.setColor(Color.white);
        if (!thinking) {
            g.fillPolygon(new int[]{1359, 1371, 1437}, new int[]{259, 221, 273}, 3);
        } else if (thinking) {
            g.fillOval(1400, 180, 50, 26);
            g.fillOval(1460, 200, 30, 16);
        }
        g.fillOval(1100, 180, 300, 160);
    }

    public Vector<Player> returnListOfPlayers() {
        return this.listOfPlayers;
    }
    /*this function was supposed to ask the user the color he choses when he plays a special card.
     In the end we decided to use an inpuDialog instead.
     public void showColorOnScreen(Graphics g) {
     g.setColor(Color.black);
     int x = 1300;
     int y = 60 + this.listOfPlayers.size() * 190;
     int h = 200;
     int w = 200;
     g.fillRoundRect(x, y, w, h, 12, 12);
     g.setColor(Color.blue);
     g.fillRoundRect(x, y, w / 2, h / 2, 12, 12);
     g.setColor(Color.orange);
     g.fillRoundRect(x + (w / 2), y + (h / 2), w / 2, h / 2, 12, 12);
     g.setColor(Color.green);
     g.fillRoundRect(x + (w / 2), y, w / 2, h / 2, 12, 12);
     g.setColor(Color.red);
     g.fillRoundRect(x, y + (h / 2), w / 2, h / 2, 12, 12);

     }*/
    //this function is complementary of the previous one.
    // it was supposed to return the color chosen depending on where the user clicked.
    /*public String returnColorOfWild() {
     int colorx = 1300;
     String color="none";
     int colory = 60 + this.listOfPlayers.size() * 190;
     int colorh = 200;
     int colorw = 200;
     int x = this.mouseX;
     int y = this.mouseY;
     //if blue
     if ((x > colorx) && (x < colorx + (colorw/2)) && (y > colory) && (y < colory + (colorh/2))) {
     color = "blue";
     }
     //else if green
     else if ((x > colorx+(colorw/2)) && (x < colorx + colorw) && (y > colory) && (y < colory + (colorh/2))) {
     color = "green";
     } 
     //else if red
     else if ((x > colorx) && (x < colorx + (colorw/2)) && (y > colory+(colorh/2)) && (y < colory + colorh)) {
     color = "red";
     }
     //ellse if yellow
     else if ((x > colorx+(colorw/2)) && (x < colorx + colorw) && (y > colory+(colorh/2)) && (y < colory + colorh)) {
     color = "yellow";
     }
     return color;

     }*/

    //this is the principal function of the class. This function is basically the one *
    //that makes the player play once
    public void playOnce(Deck actual, Deck target, Deck finaldeck, int i, boolean colorDepending, boolean noAttributDepending) {
        /*thz variable i received in parameters is the one returned by the program "getCardAtmouse(), which
         is supposed to return :
         -1 is nothing has been clicked
         -2 if the draw package has been clicked
         -3 if the player's hand has been clicked
         a positive number between 0 and 7 if a card has been selected
         it also receives :
         a deck actual (from which we see if we can play)
         a deck target (on which we want to put the card)
         a deck final (on which we will put the card at the very end)
         Finally, it receives boolean, indicating if we want to compare the card by color and attribut
         or attribut only (when player can play multiple cards)
         */

        if (i != -1) {
            //if we actually selected something
            actual.getCard(i).setHidden(false);//we show it
            System.out.println("la couleur est " + this.actualcolor);//just to debug
            if (actual.getSize() != 0) {
                if (colorDepending && (!noAttributDepending)) {
                    if ((actual.getCard(i).getAttribut().equals(target.getCard(target.getSize() - 1).getAttribut()))
                            || (actual.getCard(i).getColor().equals(this.actualcolor))) {//If the card has same attribut or same color
                        if (actual.getCard(i).getAttribut().equals("Draw Two")) {//If draw two is played
                            this.listOfPlayers.elementAt(this.playernumber).addPoints(20);
                            this.drawtwo = true;
                            this.skip = true;
                            if (!this.reverse) {
                                if (this.playernumber < this.listOfPlayers.size() - 1) {
                                    draw(this.listOfPlayers.elementAt(this.playernumber + 1), 2);
                                } else {
                                    //depending on the playing direction, next player draws two
                                    draw(this.listOfPlayers.elementAt(0), 2);
                                }
                            } else if (this.reverse) {
                                if (this.playernumber > 0) {
                                    draw(this.listOfPlayers.elementAt(this.playernumber - 1), 2);
                                } else {
                                    draw(this.listOfPlayers.elementAt(listOfPlayers.size() - 1), 2);
                                }
                            }

                        } else if (actual.getCard(i).getAttribut().equals("Reverse")) {//if player plays a reverse
                            this.listOfPlayers.elementAt(this.playernumber).addPoints(20);
                            if (this.reverse) {
                                this.reverse = false;
                            } else if (!this.reverse) {
                                this.reverse = true;
                            }

                        } else if (actual.getCard(i).getAttribut().equals("Skip")) {//if player plays a skip
                            this.listOfPlayers.elementAt(this.playernumber).addPoints(20);
                            this.skip = true;//si on a un Skip
                        }
                        if (!actual.getCard(i).getColor().equals("special")) {
                            this.actualcolor = actual.getCard(i).getColor();
                        }
                        //If one of the previous conditions is true, the card can be used, and me add it to the final deck
                        //and remove it from the actual deck
                        if ((!actual.getCard(i).getColor().equals("special")) && (!actual.getCard(i).getAttribut().equals("skip"))
                                && (!actual.getCard(i).getAttribut().equals("Draw Two"))
                                && (!actual.getCard(i).getAttribut().equals("Wild+4"))
                                && (!actual.getCard(i).getAttribut().equals("Reverse"))
                                && (!actual.getCard(i).getAttribut().equals("Skip"))) {
                            this.listOfPlayers.elementAt(this.playernumber).addPoints(Integer.parseInt(actual.getCard(i).getAttribut()));
                        }
                        actual.getCard(i).setCanBeUsed(true);
                        finaldeck.addCard(actual.getCard(i));
                        actual.removeCard(i);
                    } else if ((actual.getSize() != 0)
                            && ((actual.getCard(i).getAttribut().equals("Wild+4")))) {//else if we have a wild four
                        this.listOfPlayers.elementAt(this.playernumber).addPoints(50);
                        this.wildfour = true;//this will later be used in the mouseClicked function
                        this.skip = true;
                        actual.getCard(i).setCanBeUsed(true);
                        finaldeck.addCard(actual.getCard(i));
                        actual.removeCard(i);
                        if (!this.reverse) {
                            if (this.playernumber < this.listOfPlayers.size() - 1) {
                                //depending on the playing direction, next player draws four
                                //the actual player choses the color with an inputDialog in the mouseClicked function
                                draw(this.listOfPlayers.elementAt(this.playernumber + 1), 4);
                            } else {
                                draw(this.listOfPlayers.elementAt(0), 4);
                            }
                        } else if (this.reverse) {
                            if (this.playernumber > 0) {
                                draw(this.listOfPlayers.elementAt(this.playernumber - 1), 4);
                            } else {
                                draw(this.listOfPlayers.elementAt(listOfPlayers.size() - 1), 4);
                            }
                        }
                        //this.actualcolor = returnColorOfWild();
                    } else if ((actual.getSize() != 0)
                            && ((actual.getCard(i).getAttribut().equals("Wild")))) {
                        //the actual player choses the color with an inputDialog in the mouseClicked function
                        this.listOfPlayers.elementAt(this.playernumber).addPoints(50);
                        actual.getCard(i).setCanBeUsed(true);
                        finaldeck.addCard(actual.getCard(i));
                        actual.removeCard(i);
                        //this.actualcolor = returnColorOfWild();
                        // player.setHasPlayed(true);
                    } else {
                        System.out.println("cant use this card");
                    }

                }//
                else if (!colorDepending && (!noAttributDepending)) {
                    //this is basically the same program but we only compare by the attribut
                    actual = this.listOfPlayers.elementAt(playernumber).retunPlayerDeck();
                    //this occures only between the player's deck and his playing hand
                    target = this.deck3;
                    finaldeck = this.deck3;
                    if (actual.getCard(i).getAttribut().equals(target.getCard(target.getSize() - 1).getAttribut())) {//si la carte est de meme couleur ou de meme attribut
                        if (actual.getCard(i).getAttribut().equals("Draw Two")) {//si on a un +2
                            this.drawtwo = true;
                            if (!this.reverse) {
                                if (this.playernumber < this.listOfPlayers.size() - 1) {
                                    draw(this.listOfPlayers.elementAt(this.playernumber + 1), 2);
                                } else {
                                    draw(this.listOfPlayers.elementAt(0), 2);
                                }
                            } else if (this.reverse) {
                                if (this.playernumber > 0) {
                                    draw(this.listOfPlayers.elementAt(this.playernumber - 1), 2);
                                } else {
                                    draw(this.listOfPlayers.elementAt(listOfPlayers.size() - 1), 2);
                                }
                            }

                        } else if (actual.getCard(i).getAttribut().equals("Reverse")) {//si on a un reverse

                            if (this.reverse) {
                                this.reverse = false;
                            } else if (!this.reverse) {
                                this.reverse = true;
                            }

                        } else if (actual.getCard(i).getAttribut().equals("Skip")) {
                            this.skip = true;//si on a un Skip
                        }
                        if (!actual.getCard(i).getColor().equals("special")) {
                            this.actualcolor = actual.getCard(i).getColor();
                        }
                        actual.getCard(i).setCanBeUsed(true);
                        finaldeck.addCard(actual.getCard(i));
                        actual.removeCard(i);
                        //player.setHasPlayed(true);
                    } else if ((actual.getSize() != 0)
                            && ((actual.getCard(i).getAttribut().equals("Wild+4")))) {
                        this.wildfour = true;
                        actual.getCard(i).setCanBeUsed(true);
                        finaldeck.addCard(actual.getCard(i));
                        actual.removeCard(i);
                        if (!this.reverse) {
                            if (this.playernumber < this.listOfPlayers.size() - 1) {
                                draw(this.listOfPlayers.elementAt(this.playernumber + 1), 4);
                            } else {
                                draw(this.listOfPlayers.elementAt(0), 4);
                            }
                        } else if (this.reverse) {
                            if (this.playernumber > 0) {
                                draw(this.listOfPlayers.elementAt(this.playernumber - 1), 4);
                            } else {
                                draw(this.listOfPlayers.elementAt(listOfPlayers.size() - 1), 4);
                            }
                        }
                        //this.actualcolor = returnColorOfWild();
                    } else if ((actual.getSize() != 0)
                            && ((actual.getCard(i).getAttribut().equals("Wild")))) {
                        actual.getCard(i).setCanBeUsed(true);
                        finaldeck.addCard(actual.getCard(i));
                        actual.removeCard(i);
                        //this.actualcolor = returnColorOfWild();
                        // player.setHasPlayed(true);
                    } else {
                        System.out.println("cant use this card");
                    }

                }

            }

        }

    }

    //this function removes a card from starting deck and adds it to player's hand
    public void draw(Player player, int nb) {
        if (this.deck.getSize() != 0) {
            for (int i = 0; i < nb; i++) {
                this.deck.getCard(this.deck.getSize() - 1).setHidden(false);
                player.addCardToPlayer(this.deck.getCard(this.deck.getSize() - 1));
                this.deck.removeCard(this.deck.getSize() - 1);
            }
            System.out.println("carte piochee");
        } else {

            System.out.println("plus de carte dans la pioche");
        }
    }

    //this function shows infos on screen : who's UFO, who's playing,...
    public void showInfos(Graphics g, Player player) {
        g.setColor(Color.white);
        if (!player.hasPlayed()) {
            g.drawString("player " + player.getName() + " has not played.", 660, 45);
        } else if (player.hasPlayed()) {
            g.drawString("player " + player.getName() + " has played.", 600, 45);
        }
        g.drawString("color is " + this.actualcolor, 700, 22);
        if (this.listOfPlayers.size() == 1) {
            g.drawString("Game is over, " + this.listOfPlayers.elementAt(0).getName() + " has lost.", 600, 45);
        }
    }

    //this functions shows coordinates of the mouse on screen. This was very helpfull to draw everything 
    // nicely and symetrically.
    public void showMouseCoords(Graphics g) {
        g.drawString(Integer.toString(this.mouseX) + "," + Integer.toString(this.mouseY), this.mouseX, this.mouseY);
    }
    /*
     this function returns -1 if nothing is selected
     returns -2 if the draw deck is selected
     returns -3 if the player's hand is selected
     returns an integer between 0 and 6 if a card is selected;
     this integer correspond to the place of the card in the player's deck
     */

    public int getMenuChoice() {
        int x = this.mouseX;
        int y = this.mouseY;
        int element = -1;
        if (!this.gamestart) {
            if ((x > 473) && (x < 1456) && (y > 304) && (y < 400)) {
                element = 1;
            } //if three players
            else if ((x > 469) && (x < 1456) && (y > 514) && (y < 623)) {
                element = 2;
            } //if four players
            else if ((x > 460) && (x < 1457) && (y > 727) && (y < 828)) {
                element = 3;
            } //if rules
            else if ((x > 670) && (x < 1257) && (y > 958) && (y < 1046)) {
                element = 4;
            } else if ((x > 1373) && (x < 1667) && (y > 911) && (y < 984)) {
                //if exit rules
                element = 5;
            } else if ((x > 49) && (x < 144) && (y > 30) && (y < 94)) {
                element = 6;
                //exit
            }

        } else if (this.gamestart) {
            if ((x > 1268) && (x < 1767) && (y > 77) && (y < 165)) {
                element = 11;
            } else if ((x > 1817) && (x < 1900) && (y > 25) && (y < 90)) {
                element = 7;
            }
        }
        return element;
    }

    public int getCardAtMouse(Player player) {
        int x = this.mouseX;
        int y = this.mouseY;
        int decalage = 300 + (this.deck.getSize() * 2);
        int decalage3 = 0;
        int cardx;
        int cardy;
        int cardw;
        int cardh;
        int element = -1;
        System.out.println(deck3.getSize());
        if (this.gamestart) {
            for (int i = 0; i < player.retunPlayerDeck().getSize(); i++) {
                cardx = player.retunPlayerDeck().getCard(i).getX();
                cardy = player.retunPlayerDeck().getCard(i).getY();
                cardh = player.retunPlayerDeck().getCard(i).getH();
                cardw = player.retunPlayerDeck().getCard(i).getW();
                if ((x > cardx) && (x < cardx + cardw) && (y > cardy) && (y < cardy + cardh)) {
                    element = i;
                }

            }
            if ((deck3.getSize() > 0) && (x > this.deck3.returnLastCard().getX() + decalage3) && (x < this.deck3.returnLastCard().getX() + decalage3 + this.deck3.returnLastCard().getW())
                    && (y > this.deck3.returnLastCard().getY()) && (y < this.deck3.returnLastCard().getY() + this.deck3.returnLastCard().getH())) {
                element = -3;
                System.out.println("Player has played his cards");
            }
            if ((x > this.deck.returnLastCard().getX() + decalage) && (x < this.deck.returnLastCard().getX() + decalage + this.deck.returnLastCard().getW())
                    && (y > this.deck.returnLastCard().getY()) && (y < this.deck.returnLastCard().getY() + this.deck.returnLastCard().getH())) {
                element = -2;
                System.out.println("draw selected");
            } else if ((element != -1) && (element != -2) && (element != -3)) {
                System.out.println("card s picked and put in player's hand");
            } else if (element == -1) {
                System.out.println("no card selected");
            }
            System.out.println(element);
        }

        return element;
    }

    //this function draws one card, depending on it's attribut and color.
    //lots of code because each card is draw differently depending on if it's hidden or not,
    //a classic card or a special card.
    public void drawCard(Graphics g, int offset, Card card, int decalage) {
        Graphics2D g2d = (Graphics2D) g;
        //our Graphics g is converted to a Graphics2D to be able to use oval function
        card.setX(30 + offset);
        card.setY(55 + decalage);
        if (card.hidden == true) {
            g.setColor(Color.white);
            g.fillRoundRect(card.getX() + offset - 5, card.getY() - 5, card.getW() + 10, card.getH() + 10, 12, 12);
            g.setColor(Color.black);
            g.drawRoundRect(card.getX() + offset - 5, card.getY() - 5, card.getW() + 10, card.getH() + 10, 12, 12);
            g.fillRoundRect(card.getX() + offset, card.getY(), card.getW(), card.getH(), 12, 12);
            g.drawImage(this.ufo, card.getX() + offset + card.getW() / 2 - 32, card.getY() + card.getH() / 2 - 25, 70, 50, this);
            g.setColor(Color.white);
            g.drawString("UFO", card.getX() + 4 + offset, card.getY() + 16);
            g.drawString("UFO", card.getX() + offset + 5 + w - 6 * 6 - 10, card.getY() - 3 + card.getH());
        } else if (card.hidden == false) {
            g.setColor(Color.white);
            g.fillRoundRect(card.getX() + offset - 5, card.getY() - 5, card.getW() + 10, card.getH() + 10, 14, 14);
            g.setColor(Color.black);
            g.drawRoundRect(card.getX() + offset - 5, card.getY() - 5, card.getW() + 10, card.getH() + 10, 14, 14);
            setColor(card.getColor(), g);
            g.fillRoundRect(card.getX() + offset, card.getY(), card.getW(), card.getH(), 14, 14);
            int sizeOfAttribut = card.getAttribut().length();
            card.setX(card.getX() + offset);
            g.setColor(Color.white);
            g2d.fillOval(card.getX() + 1, card.getY() + 1, card.getW() - 2, card.getH() - 2);
            setColor(card.getColor(), g);
            if ((!card.getAttribut().equals("Wild+4")) && (!card.getAttribut().equals("Wild"))
                    && (!card.getAttribut().equals("Draw Two")) && (!card.getAttribut().equals("Reverse"))
                    && (!card.getAttribut().equals("Skip"))) {
                g.setFont(new Font("TimesRoman", Font.PLAIN, 68));
                g.drawString(card.getAttribut(), card.getX() - 12 + card.getW() / 2 - sizeOfAttribut * 4, card.getY() + 22 + card.getH() / 2);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
                g.setColor(Color.white);
                g.drawString(card.getAttribut(), card.getX() + 5, card.getY() + 18);
                g.drawString(card.getAttribut(), card.getX() + card.getW() - sizeOfAttribut - 13, card.getY() + card.getH() - 3);
            } else if (card.getAttribut().equals("Wild+4")) {
                g.setColor(Color.orange);
                g.fillRect(card.getX() + card.getW() / 2 - 10, card.getY() + card.getH() / 2, 20, 32);
                g.setColor(Color.red);
                g.fillRect(card.getX() + card.getW() / 3 - 7, card.getY() + card.getH() / 2 - 17, 20, 32);
                g.setColor(Color.green);
                g.fillRect(card.getX() + card.getW() / 2 + 7, card.getY() + card.getH() / 2 - 17, 20, 32);
                g.setColor(Color.blue);
                g.fillRect(card.getX() + card.getW() / 2 - 10, card.getY() + card.getH() / 2 - 32, 20, 32);
                g.setColor(Color.white);
                g.drawString("+4", card.getX() + 5, card.getY() + 18);
                g.drawString("+4", card.getX() + card.getW() - 25, card.getY() + card.getH() - 5);

            } else if (card.getAttribut().equals("Draw Two")) {
                g.setColor(Color.black);
                g.drawRect(card.getX() - 1 + card.getW() / 2 - 15, card.getY() - 4 + card.getH() / 2, 20, 32);
                setColor(card.getColor(), g);
                g.fillRect(card.getX() + card.getW() / 2 - 15, card.getY() - 3 + card.getH() / 2, 20, 32);
                //g.fillRect(card.getX() + card.getW() / 3 - 7, card.getY() + card.getH() / 2 - 17, 20, 32);
                g.setColor(Color.black);
                g.drawRect(card.getX() - 1 + card.getW() / 2 + 2, card.getY() - 4 + card.getH() / 2 - 17, 20, 32);
                setColor(card.getColor(), g);
                g.fillRect(card.getX() + card.getW() / 2 + 2, card.getY() - 3 + card.getH() / 2 - 17, 20, 32);
                // g.fillRect(card.getX() + card.getW() / 2 - 10, card.getY() + card.getH() / 2 - 32, 20, 32);
                g.setColor(Color.white);
                g.drawString("+2", card.getX() + 5, card.getY() + 18);
                g.drawString("+2", card.getX() + card.getW() - 25, card.getY() + card.getH() - 5);

            } else if (card.getAttribut().equals("Skip")) {
                setColor(card.getColor(), g);
                int centerx = card.getX() + card.getW() / 2;
                int centery = card.getY() + card.getH() / 2;
                int c = 60;
                g.fillOval(centerx - c / 2, centery - c / 2, c, c);
                g.setColor(Color.white);
                c = 45;
                g.fillOval(centerx - c / 2, centery - c / 2, c, c);
                c = 50;
                setColor(card.getColor(), g);
                g.fillRect(centerx - c / 2, centery - 6, c, 12);
                g.setColor(Color.white);
                g.drawString("S", card.getX() + 5, card.getY() + 18);
                g.drawString("S", card.getX() + card.getW() - 15, card.getY() + card.getH() - 5);

            } else if (card.getAttribut().equals("Reverse")) {
                int centerx = card.getX() + card.getW() / 2;
                int centery = card.getY() + card.getH() / 2;
                int h = 35;
                int w = 15;
                setColor(card.getColor(), g);
                g.fillRect(centerx + 3, centery - 6, w, h);
                g.fillRect(centerx - w - 3, centery - h + 6, w, h);
                g.fillPolygon(new int[]{centerx - w - 13, centerx - (w / 2) - 3, centerx + 7}, new int[]{centery - h + 6, centery - h - 9, centery - h + 6}, 3);
                g.fillPolygon(new int[]{centerx - 7, centerx + 3 + (w / 2), centerx + 13 + w}, new int[]{centery + h - 6, centery + h + 9, centery + h - 6}, 3);
                g.setColor(Color.white);
                g.drawString("R", card.getX() + 5, card.getY() + 18);
                g.drawString("R", card.getX() + card.getW() - 15, card.getY() + card.getH() - 5);
            } else if (card.getAttribut().equals("Wild")) {
                int centerx = card.getX() + card.getW() / 2;
                int centery = card.getY() + card.getH() / 2;
                int h = 60;
                int w = 32;
                int r = 30;
                g.setColor(Color.red);
                g.fillRoundRect(centerx - w, centery - h, w, h, r, r);
                g.fillRect(centerx - w / 3, centery - h, w / 3, h);
                g.fillRect(centerx - w, centery - h / 2, w, h / 2);
                g.setColor(Color.blue);
                g.fillRoundRect(centerx, centery - h, w, h, r, r);
                g.fillRect(centerx, centery - h, w / 3, h);
                g.fillRect(centerx, centery - h / 2, w, h / 2);
                g.setColor(Color.orange);
                g.fillRoundRect(centerx - w, centery, w, h, r, r);
                g.fillRect(centerx - w / 3, centery, w / 3, h);
                g.fillRect(centerx - w, centery, w, h / 2);
                g.setColor(Color.green);
                g.fillRoundRect(centerx, centery, w, h, r, r);
                g.fillRect(centerx, centery, w / 3, h);
                g.fillRect(centerx, centery, w, h / 2);
                g.setColor(Color.white);
                g.drawString("W", card.getX() + 5, card.getY() + 18);
                g.drawString("W", card.getX() + card.getW() - 18, card.getY() + card.getH() - 5);
            }
        }
    }

    //this function sets the color of Graphics depending on what color the card has
    public void setColor(String color, Graphics g) {
        if (color.equals("blue")) {
            g.setColor(Color.blue);
        } else if (color.equals("green")) {
            g.setColor(Color.green);
        } else if (color.equals("yellow")) {
            g.setColor(Color.orange);
        } else if (color.equals("red")) {
            g.setColor(Color.red);
        } else if (color.equals("special")) {
            g.setColor(Color.black);
        }
    }

    //this function is for debugging
    public void showPlayers() {
        System.out.println("There are " + this.listOfPlayers.size() + " players in this game :");
        for (int i = 0; i < this.listOfPlayers.size(); i++) {
            System.out.println("Player " + (i + 1) + " is " + this.listOfPlayers.elementAt(i).getName());
            System.out.println("His deck is composed of :");
            this.listOfPlayers.elementAt(i).showPlayerDeck();
        }
        System.out.println("\n\n");
    }

    //this function gives random cards from the starting deck to the players
    public void setinitialCardsToPlayers() {
        Random random = new Random();
        int nb = 0;
        int nbCardremoved = 0;
        for (int i = 0; i < this.listOfPlayers.size(); i++) {
            for (int j = 0; j < nbCardsAtBeginning; j++) {
                nb = random.nextInt(108 - nbCardremoved);
                Card card = this.deck.getCard(nb);
                this.listOfPlayers.elementAt(i).addCardToPlayer(card);
                this.deck.removeCard(nb);
                nbCardremoved = nbCardremoved + 1;
            }

        }
    }

    public Deck returnDeck() {
        return this.deck;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        this.timer.start();
        repaint();
    }

    @Override
    //this function is the @Override fuction from MouseListener
    public void mouseClicked(MouseEvent me) {
        this.mouseX = me.getX();
        this.mouseY = me.getY();
        if (this.gamestart) {
            if ((!this.listOfPlayers.isEmpty()) && (!this.listOfPlayers.elementAt(this.playernumber).hasPlayed())) {//If the player has not played yet
                if ((getCardAtMouse(listOfPlayers.elementAt(this.playernumber)) >= 0) && (this.deck3.getSize() == 0)) {//if player's hand is empty and his deck is not
                    if (this.listOfPlayers.elementAt(this.playernumber).retunPlayerDeck().getCard(getCardAtMouse(this.listOfPlayers.elementAt(this.playernumber))).getColor().equals("special")) {//if we have a special color, we ask the user
                        this.actualcolor = JOptionPane.showInputDialog("Enter the new color : ");
                        //then we play from player's deck to player's hand
                        playOnce(this.listOfPlayers.elementAt(this.playernumber).retunPlayerDeck(), this.deck2, this.deck3, getCardAtMouse(this.listOfPlayers.elementAt(this.playernumber)), true, false);
                    } else {
                        playOnce(this.listOfPlayers.elementAt(this.playernumber).retunPlayerDeck(), this.deck2, this.deck3, getCardAtMouse(this.listOfPlayers.elementAt(this.playernumber)), true, false);
                    }
                } else if ((getCardAtMouse(listOfPlayers.elementAt(this.playernumber)) >= 0) && (this.deck3.getSize() > 0)) {
                    if (this.listOfPlayers.elementAt(this.playernumber).retunPlayerDeck().getCard(getCardAtMouse(listOfPlayers.elementAt(this.playernumber))).getColor().equals("special")) {
                        //same thing here but we play from palyer's deck to player's hand omparaing only with attributs
                        this.actualcolor = JOptionPane.showInputDialog("Enter the new color : ");
                        playOnce(this.listOfPlayers.elementAt(this.playernumber).retunPlayerDeck(), this.deck3, this.deck2, getCardAtMouse(this.listOfPlayers.elementAt(this.playernumber)), false, false);
                    } else {
                        playOnce(this.listOfPlayers.elementAt(this.playernumber).retunPlayerDeck(), this.deck3, this.deck3, getCardAtMouse(this.listOfPlayers.elementAt(this.playernumber)), false, false);
                    }
                } else if (getCardAtMouse(this.listOfPlayers.elementAt(this.playernumber)) == -2) {//if we pick
                    draw(this.listOfPlayers.elementAt(this.playernumber), 1);
                    if (this.listOfPlayers.elementAt(this.playernumber).retunPlayerDeck().getCard(this.listOfPlayers.elementAt(this.playernumber).retunPlayerDeck().getSize() - 1).getColor().equals("special")) {
                        this.actualcolor = JOptionPane.showInputDialog("Enter the new color : ");
                    }
                    //the card the player has picked is automatically played; if it can be used, it's added to the game deck;
                    //else it's added to player's deck and his turn is skipperd
                    playOnce(this.listOfPlayers.elementAt(this.playernumber).retunPlayerDeck(), this.deck2, this.deck2, this.listOfPlayers.elementAt(this.playernumber).retunPlayerDeck().getSize() - 1, true, false);
                    this.listOfPlayers.elementAt(this.playernumber).setHasPlayed(true);
                    /*
                     all the following code is to go to next player when actual player has played, depending on
                     if there is a skip, a reverse, or if there are two players.
                     if there are two players, a skip and a reverse don't have the same behaviour as if there were
                     three or four players.
                     */
                    if (this.listOfPlayers.size() != 2) {
                        this.listOfPlayers.elementAt(this.playernumber).setHasPlayed(true);
                        if (!this.reverse) {
                            if (this.skip) {
                                if (this.playernumber < this.listOfPlayers.size() - 1) {
                                    this.playernumber = this.playernumber + 1;
                                } else {
                                    this.playernumber = 0;
                                }
                                this.skip = false;
                            }
                            if (this.playernumber < this.listOfPlayers.size() - 1) {
                                this.playernumber = this.playernumber + 1;
                            } else {
                                this.playernumber = 0;
                            }
                        } else if (this.reverse) {
                            if (this.skip) {
                                if (this.playernumber > 0) {
                                    this.playernumber = this.playernumber - 1;
                                } else {
                                    this.playernumber = this.listOfPlayers.size() - 1;
                                }
                                this.skip = false;
                            }
                            if (this.playernumber > 0) {
                                this.playernumber = this.playernumber - 1;
                            } else {
                                this.playernumber = this.listOfPlayers.size() - 1;
                            }
                        }
                        for (int j = 0; j < this.listOfPlayers.size(); j++) {
                            if (j != this.playernumber) {
                                this.listOfPlayers.elementAt(j).setHasPlayed(false);
                            }
                        }
                    } else if (this.listOfPlayers.size() == 2) {
                        if ((!this.reverse) && (!this.skip) && (!this.wildfour) && (!this.drawtwo)) {
                            this.listOfPlayers.elementAt(this.playernumber).setHasPlayed(true);
                            if (this.playernumber < this.listOfPlayers.size() - 1) {
                                this.playernumber = this.playernumber + 1;
                            } else {
                                this.playernumber = 0;
                            }
                            for (int j = 0; j < this.listOfPlayers.size(); j++) {
                                if (j != this.playernumber) {
                                    this.listOfPlayers.elementAt(j).setHasPlayed(false);
                                }
                            }
                        } else {
                            this.listOfPlayers.elementAt(this.playernumber).setHasPlayed(false);
                            this.skip = false;
                            this.reverse = false;
                            this.wildfour = false;
                            this.drawtwo = false;
                        }
                    }

                } else if ((this.deck3.getSize() > 0) && (getCardAtMouse(this.listOfPlayers.elementAt(this.playernumber)) == -3)) {
                    if (!this.deck3.getCard(this.deck3.getSize() - 1).getColor().equals("special")) {
                        this.actualcolor = this.deck3.getCard(this.deck3.getSize() - 1).getColor();
                        //if we finally decide to add cards from player's hand to playing deck.
                        //the process is the same to go grom a player to another.
                    }
                    for (int i = 0; i < this.deck3.getSize(); i++) {
                        System.out.println("la main a " + this.deck3.getSize());
                        this.deck2.addCard(this.deck3.getCard(i));
                        if (!this.deck2.getCard(this.deck2.getSize() - 1).getColor().equals("special")) {
                            this.actualcolor = this.deck2.getCard(this.deck2.getSize() - 1).getColor();
                        }
                        this.deck3.removeCard(i);
                        if (this.deck3.getSize() == 0) {
                            if (this.listOfPlayers.size() != 2) {
                                this.listOfPlayers.elementAt(this.playernumber).setHasPlayed(true);
                                if (!this.reverse) {
                                    if (this.skip) {
                                        if (this.playernumber < this.listOfPlayers.size() - 1) {
                                            this.playernumber = this.playernumber + 1;
                                        } else {
                                            this.playernumber = 0;
                                        }
                                        this.skip = false;
                                    }
                                    if (this.playernumber < this.listOfPlayers.size() - 1) {
                                        this.playernumber = this.playernumber + 1;
                                    } else {
                                        this.playernumber = 0;
                                    }
                                } else if (this.reverse) {
                                    if (this.skip) {
                                        if (this.playernumber > 0) {
                                            this.playernumber = this.playernumber - 1;
                                        } else {
                                            this.playernumber = this.listOfPlayers.size() - 1;
                                        }
                                        this.skip = false;
                                    }
                                    if (this.playernumber > 0) {
                                        this.playernumber = this.playernumber - 1;
                                    } else {
                                        this.playernumber = this.listOfPlayers.size() - 1;
                                    }
                                }
                                for (int j = 0; j < this.listOfPlayers.size(); j++) {
                                    if (j != this.playernumber) {
                                        this.listOfPlayers.elementAt(j).setHasPlayed(false);
                                    }
                                }
                            } else if (this.listOfPlayers.size() == 2) {
                                if ((!this.reverse) && (!this.skip) && (!this.wildfour) && (!this.drawtwo)) {
                                    this.listOfPlayers.elementAt(this.playernumber).setHasPlayed(true);
                                    if (this.playernumber < this.listOfPlayers.size() - 1) {
                                        this.playernumber = this.playernumber + 1;
                                    } else {
                                        this.playernumber = 0;
                                    }
                                    for (int j = 0; j < this.listOfPlayers.size(); j++) {
                                        if (j != this.playernumber) {
                                            this.listOfPlayers.elementAt(j).setHasPlayed(false);
                                        }
                                    }
                                } else {
                                    this.listOfPlayers.elementAt(this.playernumber).setHasPlayed(false);
                                    this.skip = false;
                                    this.reverse = false;
                                    this.wildfour = false;
                                    this.drawtwo = false;
                                }
                            } else {
                                //playOnce(this.deck3, this.deck2, this.deck2, i, false, true);

                                this.listOfPlayers.elementAt(this.playernumber).setHasPlayed(false);
                            }
                        }
                    }
                }

            }
            if (getMenuChoice() == 11) {
                this.end = false;
                this.gamestart = false;
                this.deck.deleteDeck();
                this.deck2.deleteDeck();
                this.deck3.deleteDeck();
                this.ranking.removeAllElements();
                this.listOfPlayers.removeAllElements();
            } else if (getMenuChoice() == 7) {
                System.exit(0);
            }
        } else if (!this.gamestart) {
            String name;
            if (getMenuChoice() == 1) {
                for (int i = 0; i < 2; i++) {
                    name = JOptionPane.showInputDialog("Enter the name of player " + (i + 1) + " : ");
                    this.listOfPlayers.add(new Player(name));
                }
                this.deck = new Deck();
                this.deck.createDeck();
                this.deck.shuffleDeck();
                setinitialCardsToPlayers();
                this.gamestart = true;
            } else if (getMenuChoice() == 2) {
                for (int i = 0; i < 3; i++) {
                    name = JOptionPane.showInputDialog("Enter the name of player " + (i + 1) + " : ");
                    this.listOfPlayers.add(new Player(name));
                }
                this.deck = new Deck();
                this.deck.createDeck();
                this.deck.shuffleDeck();
                setinitialCardsToPlayers();
                this.gamestart = true;
            } else if (getMenuChoice() == 3) {
                for (int i = 0; i < 4; i++) {
                    name = JOptionPane.showInputDialog("Enter the name of player " + (i + 1) + " : ");
                    this.listOfPlayers.add(new Player(name));
                }
                this.deck = new Deck();
                this.deck.createDeck();
                this.deck.shuffleDeck();
                setinitialCardsToPlayers();
                this.gamestart = true;
            } else if (getMenuChoice() == 4) {
                this.rules = true;

            } else if (getMenuChoice() == 5) {
                this.rules = false;
            } else if (getMenuChoice() == 6) {
                System.exit(0);
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent me
    ) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me
    ) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me
    ) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me
    ) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
