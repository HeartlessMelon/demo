package com.uno.app;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void testSplitPlayerInput(){
        String idx = "2";
        String color = "blue";
        String[] splitted = App.splitPlayerInput("2 blue");
        assertTrue(splitted[0].equals(idx));
        assertTrue(splitted[1].equals(color));

        splitted = App.splitPlayerInput("2");
        assertTrue(splitted[0].equals(idx));
        assertTrue(splitted[1].equals(""));
    }
}
