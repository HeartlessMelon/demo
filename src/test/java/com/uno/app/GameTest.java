package com.uno.app;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import org.junit.Test;

public class GameTest {
    private Game testGame;
    private Player testPl1;
    private Player testPl2;

    @Before
    public void initNewTestGame(){
        testGame = new Game();
        testPl1 = new Player("Player1");
        testPl2 = new Player("Player2");
        testGame.addPlayerToGame(testPl1);
        testGame.addPlayerToGame(testPl2);
    }

    @Test //TODO WildCard changes the Color
    public void testIsCardPlayable(){
        Card blue0 = new Card(CardColor.BLUE,CardFunction.NUM0);
        Card green0 = new Card(CardColor.GREEN,CardFunction.NUM0);
        Card blue7 = new Card(CardColor.BLUE,CardFunction.NUM7);
        Card blueDraw2 = new Card(CardColor.BLUE,CardFunction.DRAW2);
        Card draw4 = new Card(CardColor.WILD, CardFunction.DRAW4);
        Card wild = new Card(CardColor.WILD, CardFunction.WILD);

        testGame.setDiscardPile(blue0);
        assertTrue(testGame.isCardPlayable(green0));// Same number
        assertTrue(testGame.isCardPlayable(blue7));// Same Color
        assertTrue(testGame.isCardPlayable(blueDraw2));
        assertTrue(testGame.isCardPlayable(wild));// Wild
        assertTrue(testGame.isCardPlayable(draw4));// Wild
    }

    @Test
    public void testReverseOrder(){
        testGame = new Game();
        Player testPl1 = new Player("JackietheAnimal");
        Player testPl2 = new Player("JackietheBaboon");
        Player testPl3 = new Player("JackietheCow");
        Player testPl4 = new Player("Rach");
        testGame.addPlayerToGame(testPl1);
        testGame.addPlayerToGame(testPl2);
        testGame.addPlayerToGame(testPl3);
        testGame.addPlayerToGame(testPl4);

        testGame.reverseOrder(testGame.getTurnOrder().remove(),testGame.getTurnOrder());
        assertEquals(4 ,testGame.getTurnOrder().size());
        assertEquals(testPl1 ,testGame.getTurnOrder().peek());

        List<Player> tmpLst = new ArrayList<Player>();
        tmpLst.add(testPl1);
        tmpLst.add(testPl4);
        tmpLst.add(testPl3);
        tmpLst.add(testPl2);

        for(Player p: tmpLst){
            assertEquals(p ,testGame.getTurnOrder().remove());
        }
        
    }
    @Test //TODO IT BROKE :(
    public void testCreateDeck() {
        testGame = new Game();
        testGame.createDeck();
        assertEquals(108 , testGame.getDeck().size());

        // Checks if the created deck has the proper amount of colored cards
        List<Card> stacktolist = new ArrayList<Card>(testGame.getDeck());                     
        int occurenceBLUE = Collections.frequency(stacktolist, CardColor.BLUE);
        assertEquals(25 , occurenceBLUE); 
        int occurenceGREEN = Collections.frequency(stacktolist, CardColor.GREEN);
        assertEquals(25 , occurenceGREEN); 
        int occurenceYELLOW = Collections.frequency(stacktolist, CardColor.YELLOW);
        assertEquals(25 , occurenceYELLOW); 
        int occurenceRED = Collections.frequency(stacktolist, CardColor.RED);
        assertEquals(25 , occurenceRED); 
    }
    @Test//TODO
    public void testShuffleDeck(){
        testGame = new Game();
        testGame.createDeck();
        testGame.shuffleDeck();
        Stack<Card> basedeck = testGame.getDeck();
        testGame.shuffleDeck();
        assertArrayEquals(basedeck.toArray(), testGame.getDeck().toArray());
  
    }
    @Test
    public void testEndTurn(){
        // Testing if endturn works
        testGame = new Game();
        Player testPl1 = new Player("JackietheAnimal");
        Player testPl2 = new Player("JackietheBaboon");
        Player testPl3 = new Player("JackietheCow");
        testGame.addPlayerToGame(testPl1);
        testGame.addPlayerToGame(testPl2);
        testGame.addPlayerToGame(testPl3);
        testGame.endTurn();
        assertEquals(3 ,testGame.getTurnOrder().size());
        assertEquals(testPl2 ,testGame.getTurnOrder().peek());



        testGame = new Game();
        testPl1 = new Player("Jack");
        testPl2 = new Player("Rach"); 
        testGame.addPlayerToGame(testPl1);
        testGame.addPlayerToGame(testPl2);
        testGame.setSkipCounter(2);
        testGame.endTurn();
        assertEquals(2 ,testGame.getTurnOrder().size());
        assertEquals(testPl1 ,testGame.getTurnOrder().peek());


    }
    @Test
    //tet draw2 card function - didnt seem to add to opponents hand 
    public void testDrawTwo(){
        testGame.createDeck();
        testGame.shuffleDeck();
        Card c = new Card(CardColor.BLUE, CardFunction.NUM1);
        testPl1.addToHand(c);
        testPl2.addToHand(c);
        testGame.setDiscardPile(c);
        c = new Card(CardColor.BLUE, CardFunction.NUM2);
        testPl1.addToHand(c);
        testPl2.addToHand(c);
        c = new Card(CardColor.BLUE, CardFunction.NUM3);
        testPl1.addToHand(c);
        testPl2.addToHand(c);
        c = new Card(CardColor.BLUE, CardFunction.DRAW2);
        testPl1.addToHand(c);
        assertEquals(testPl1.getCard(3) , c);
        testGame.playCard(testPl1 ,3,null);
        assertEquals(2, testGame.getDrawCounter());// The next player gets 2 Cards added to Hand
        testGame.endTurn();
        assertEquals(5 , testPl2.getHand().size());
    }
    
    @Test
    public void testExecuteCardEffect(){
        // Reverse
        // Skip

        /** Draw4 */
        Card draw4 = new Card(CardColor.WILD, CardFunction.DRAW4);
        // Test color change
        testGame.executeCardEffect(draw4, CardColor.BLUE); 
        assertEquals(CardColor.BLUE, testGame.getCurrentColor());
        // Test actually adds 4 to hand
        assertEquals(4, testGame.getDrawCounter());
       
        /** WILD */
        Card wild = new Card(CardColor.WILD, CardFunction.WILD);
        testGame.executeCardEffect(wild, CardColor.BLUE);
        assertEquals(CardColor.BLUE, testGame.getCurrentColor());

        Card green0 = new Card(CardColor.GREEN,CardFunction.NUM0);
        testGame.executeCardEffect(green0, CardColor.BLUE);
        assertEquals(CardColor.GREEN, testGame.getCurrentColor());
    }

    @Test
    public void testPlayCard(){
        testGame.startGame();
        /** Draw4 */

        Card c = new Card(CardColor.BLUE, CardFunction.NUM1);
        testPl1.addToHand(c);
        testPl2.addToHand(c);
        testGame.setDiscardPile(c);
        c = new Card(CardColor.BLUE, CardFunction.NUM2);
        testPl1.addToHand(c);
        testPl2.addToHand(c);
        c = new Card(CardColor.BLUE, CardFunction.NUM3);
        testPl1.addToHand(c);
        testPl2.addToHand(c);
        c = new Card(CardColor.WILD, CardFunction.DRAW4);
        testPl1.addToHand(c);
        assertEquals(testPl1.getCard(3) , c);
        testGame.playCard(testPl1 ,3,CardColor.BLUE);
        assertEquals(4, testGame.getDrawCounter());
        testGame.endTurn();
        assertEquals(7 , testPl2.getHand().size());// The next player gets 4 Cards added to Hand
        assertEquals(testGame.getCurrentColor(), CardColor.BLUE);
         /** WILD */
         
    }
}

