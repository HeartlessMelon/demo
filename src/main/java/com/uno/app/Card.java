package com.uno.app;

public class Card{

    private CardColor color;
    private CardFunction function;


    public Card(){};
    public Card(CardColor color, CardFunction function){
        this.color = color;
        this.function = function;
    }

    public CardColor getColor()       { return this.color; }
    public CardFunction getFunction() { return this.function; }

    /*
    * Card have to match either by the number, color, or the function
    */
    public boolean isCardMatching(Card otherCard){
        return this.getColor() == otherCard.getColor() 
            || this.getFunction() == otherCard.getFunction();
    }
    /*
    * Overloads the method
    * For use when a WIlD Card is in play and changes the global Color
    */
    public boolean isCardMatching(CardColor c, CardFunction f){// Overloads isCardMatching
        return this.getColor() == c || this.getFunction() == f;
    }
    
    @Override
    public String toString(){
        return (Constants.colorPicker(this.color) + this.function.name() + Constants.ANSI_RESET);
    }
}