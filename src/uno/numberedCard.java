package uno;
/*Every bit of this project was made before realizing 
that types of classes and some instructions were given in the word doc.
In the end, the overall structure is almost the same as the one given.
Hope this will still work good for you.
*/
/*
this class is numberedClass and extends Card. It correspond to a card that has a number.
*/
public class numberedCard extends Card {

    private String number;

    public numberedCard(String color, String number) {
        super(color);
        this.number = number;

    }
    //the method getAttribut is @Override from the abstract class
    @Override
    public String getAttribut() {
        return this.number;
    }
}
