package com.uno.app;


import java.io.*;
import java.util.*;
class App {

        private final static int MAX_NUM_OF_PLAYERS = 10;
        
        private static Scanner playerInput;
        private static Player currPlayer;

        public static void addPlayerToGamesToGame(Game g){
            playerInput = new Scanner(System.in);
            String pName;
            System.out.println("Enter player 1 name");
            pName = playerInput.nextLine();
            Player tmpPlayer = new Player(pName);
            g.addPlayerToGame(tmpPlayer);
            System.out.println("Enter player 2 name");
            pName = playerInput.nextLine();
            int numPlayers = 2;
            while(!pName.equals("Next")&& g.getTurnOrder().size()<MAX_NUM_OF_PLAYERS){
                tmpPlayer = new Player(pName);
                g.addPlayerToGame(tmpPlayer);
                System.out.println("Enter player " + ++numPlayers +"name or type 'Next'");
                pName = playerInput.nextLine();
            }

        }

        public static int convertInputToInteger(String s){ 
            try {
                return Integer.parseInt(s)-1;
            }catch (NumberFormatException e) {
                return -1;
            }
        }
        public static CardColor chooseColor(String color){
            switch(color){
                case "blue":
                    return CardColor.BLUE;
                case "red":
                    return CardColor.RED;
                case "yellow":
                    return CardColor.YELLOW;
                case "green":
                    return CardColor.GREEN;
                default:
                    return null;
            }
        }
        public static String[] splitPlayerInput(String input){
            String[] splitInput = input.split(" ");
            String idxHand = splitInput[0];
            String color;
            try{
              
                color = splitInput[1];
            }catch(ArrayIndexOutOfBoundsException e){
                return new String[]{idxHand, ""};
            }
            return splitInput;
        }

        
        public static void main(String[] args){
            System.out.println("Welcome to Jank UNO");
            
            // Scanner playerInput = new Scanner(System.in);
            // System.out.println("Type 'Start' to start the game");
            // String line = playerInput.nextLine();
            // while(!line.equals("Start")){
            //     System.out.println("Try Again");
            //     line = playerInput.nextLine();   
            // }
   
            
            Game g = new Game();
            g.startGame();
            addPlayerToGamesToGame(g);
            g.distributeCardsAtStart();
            for (Player p : g.getTurnOrder()){
                System.out.println(p);
            } 

            while(g.isGameRunning()){
                String userInput;
                g.validHand();
                g.drawUntilValid();
                System.out.println("Discard Pile : " + g.getTopDiscard());
                currPlayer = g.getTurnOrder().peek();
                System.out.println(currPlayer);
                System.out.println("Input the index of the card you desire to play");
                playerInput = new Scanner(System.in);
                userInput = playerInput.nextLine();// Index of Hand

                String[] splittedInput = splitPlayerInput(userInput);
                
                while(!g.playCard(currPlayer, convertInputToInteger(splittedInput[0]), chooseColor(splittedInput[1]))){
                    System.out.println("Invalid Card, try again");
                    userInput = playerInput.nextLine();
                    splittedInput = splitPlayerInput(userInput);
                }
                if(g.winGame()){// Check for win condition
                    break;
                }
                System.out.println("Enter 'End Turn' to finish the turn");
                userInput = playerInput.nextLine();// "End Turn"
                while(!userInput.equals("End Turn")){
                    System.out.println("Enter 'End Turn' to finish the turn");
                    userInput = playerInput.nextLine(); 
                }
                g.endTurn();
                /**
                 * display player hand and top Card of discard pile
                 * take an input (number - index of a Card in the hand) (end turn - ends the turn )
                 * RL - we might have to print out the index number before the card for readability 
                 */
            }
            System.out.println(g.getTurnOrder().peek() + "has won");

            playerInput.close();
            




      
        }
}



      



