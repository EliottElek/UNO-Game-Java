package uno;
/*Every bit of this project was made before realizing 
that types of classes and some instructions were given in the word doc.
In the end, the overall structure is almost the same as the one given.
Hope this will still work good for you.
*/
/*
this class is signedCard and extends Card. It correspond to a card that has a special sign.
*/
public class signedCard extends Card {

    private String sign;

    public signedCard(String color, String sign) {
        super(color);
        this.sign = sign;
    }
    //the method getAttribut is @Override from the abstract class
    public String getAttribut() {
        return this.sign;
    }

}
