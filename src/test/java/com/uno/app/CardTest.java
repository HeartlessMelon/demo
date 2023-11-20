package com.uno.app;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Before;

import org.junit.Test;


public class CardTest {
    

    @Before
    public void init(){
    }

    @Test
    public void testIsCardMatching(){
        Card blue0 = new Card(CardColor.BLUE,CardFunction.NUM0);
        Card green0 = new Card(CardColor.GREEN,CardFunction.NUM0);
        Card blue7 = new Card(CardColor.BLUE,CardFunction.NUM7);
        Card blueDraw2 = new Card(CardColor.BLUE,CardFunction.DRAW2);
        Card draw4 = new Card(CardColor.WILD, CardFunction.DRAW4);
        Card wild = new Card(CardColor.WILD, CardFunction.WILD);
        

        assertTrue(blue0.isCardMatching(green0));// Same number differnet color 
        assertTrue(blue7.isCardMatching(blue0));// Same Color differnet number 
        assertTrue(blueDraw2.isCardMatching(blue0));// Same Color

        assertFalse(draw4.isCardMatching(blue0));
        assertFalse(wild.isCardMatching(blue0));
    }
}
