package com.uno.app;

import java.util.ArrayList;


public class Player{

    // Class member variables
    private ArrayList<Card> hand;
    private String name;


    // Getters
    public ArrayList<Card> getHand()    { return this.hand; }
    public Card getCard(int i)          { return this.hand.get(i); } 
    public String getName()             { return this.name; }

    // Contructor
    public Player(String name){
        this.hand = new ArrayList<Card>();
        this.name = name.equals("") || name==null? "RACH": name;// Gives default name in case of no name
    }

    public void addToHand(Card c){
        hand.add(c);
    }


    /* 
    * Removes that a Card from the hand
    * @param i - Index position 
    */
    public Card removeFromHand(int i){
        return this.hand.remove(i);
    }

    public boolean handContainsSameFunction(Card c){
        for (Card h : hand) {
            CardFunction cfunc = h.getFunction();
            if (c.getFunction() == cfunc){
                return true;
            }
        }
        return false;
    }



    // Rach - [1, 2, draw4, draw2]
    @Override
    public String toString(){
        return (this.name + " - " + this.hand);
    }
    
}

// Player p1 = new Player("rach");
// System.out.println(p1);

// String str = p1.toString();