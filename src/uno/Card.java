package uno;
/*Every bit of this project was made before realizing 
that types of classes and some instructions were given in the word doc.
In the end, the overall structure is almost the same as the one given.
Hope this will still work good for you.
*/


/*
Card is an abstract class that has a color, and an attribut.
an attribut can be either a number (0...9) or a +2, skip,...
Each card also has X and Y coordinates, a width and height, a boolean that indicates if the card is hidden or not,
that will be used to later draw the card on the panel.
Finally, a boolean that indicates if the card can be used or not in the actual situation.
*/
public abstract class Card {

    protected String color;
    protected int xStart = 30;
    protected int yStart = 90;
    protected final static int w = 120;
    protected final static int h = 150;
    protected boolean hidden = false;
    protected boolean canbeused = false;
    //constructor
    public Card(String color) {
        this.color = color;
    }
    //abstract method that is @Override later in numberedCard and signedCard classes.
    public abstract String getAttribut();
    //returns the color
    public String getColor() {
        return this.color;
    }
    //every method that prints in the consol is to debug and make sure everything works fine with sentences
    public void showCard() {
        System.out.println(this.getAttribut() + " of " + this.getColor());
    }
    //setter of X coordinate
    public void setX(int x) {
        this.xStart = x;
    }
    //setter of Y coordinate
    public void setY(int y) {
        this.yStart = y;
    }
    //getter of Y coordinate
    public int getY() {
        return this.yStart;
    }
    //getter of X coordinate
    public int getX() {
        return this.xStart;
    }
    //getter of width
    public int getW() {
        return this.w;
    }
    //getter of height
    public int getH() {
        return this.h;
    }
    //setter of hidden boolean
    public void setHidden(boolean bool) {
        this.hidden = bool;
    }
    //setter of boolean that indicates if the card can be used to play
    public void setCanBeUsed(boolean bool) {
        this.canbeused = bool;
        if (bool) {
            this.hidden = false;
            System.out.println("la carte utilisée apparait correctement");
        }
    }
    //getter of the previous boolean
    public boolean getCanBeUsed() {
        return this.canbeused;
    }

}
