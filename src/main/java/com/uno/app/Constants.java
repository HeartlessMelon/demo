package com.uno.app;
public final class Constants {

    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m"; 
    public static  final String colorPicker(CardColor c){
        switch(c){
            case RED:
                return RED;
            case GREEN:
                return GREEN;
            case YELLOW:
                return YELLOW;
            case BLUE:
                return BLUE;
            default:
                return ANSI_RESET;
        }
        
    } 
}



