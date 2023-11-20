package com.uno.app;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Collections;
import java.util.Stack;

public class Game{

    // Class member variables
    private Stack<Card> deck;
    private Queue<Player> turnOrder;
    private Stack<Card> discardPile;
    private int skipCounter;
    private int drawCounter;
    private CardColor currentColor;
    private boolean gameRunning;


    // Getters
    public Stack<Card> getDeck()        { return this.deck;                 }
    public Queue<Player> getTurnOrder() { return this.turnOrder;            }
    public Card getTopDiscard()         { return this.discardPile.peek();   }
    public int getDrawCounter()         { return this.drawCounter;          }
    public CardColor getCurrentColor()  { return this.currentColor;         }
    public boolean isGameRunning()      { return this.gameRunning;}

    public void setSkipCounter(int i)    {this.skipCounter = i;}
    public void setDiscardPile(Card c)   {this.discardPile.push(c);}
    public void setCurrentColor(CardColor c)        {this.currentColor = c;}

    // Contructor
    public Game(){
        this.deck = new Stack<Card>();
        this.discardPile = new Stack<Card>();
        this.turnOrder = new LinkedList<Player>();
        this.skipCounter = 1;
        this.drawCounter = 0;
        this.gameRunning = true;
    }
    public void startGame(){
        this.createDeck();
        this.shuffleDeck();
        this.populateDiscard();
    }

    /*
    * Places the first Card from the Deck onto the Discard Pile
    */
    public void populateDiscard(){
        setCurrentColor(discardPile.push(this.deck.pop()).getColor());
        while(discardPile.peek().getColor() == CardColor.WILD){
            setCurrentColor(discardPile.push(this.deck.pop()).getColor());
        }
    }
    /*
    * Adds a new Player into the turn order Queue 
    * param p - 
    */
    public void addPlayerToGame(Player p){
        turnOrder.add(p);
    }
    /**
     * Hands out 7 Cards to each Player at the start of the game 
     */
    public void distributeCardsAtStart(){
        for (Player p : turnOrder) {
            while (p.getHand().size()<7){
            drawCard(p);
            }   
        }
    }
    /*
    * Takes a card from the deck and add to a Player's hand
    */
    public Card drawCard(Player p){
        Card c  = deck.pop();
        p.addToHand(c);
        return c;
    }
    public void drawMultipleCards(Player p){
        for(int i = 0; i < this.drawCounter; i++){
            drawCard(p);
        }
        drawCounter = 0;
    }


    /*
    * Card is playable only when it matches the Color or Function of the top Card of the pile
    */
    public boolean isCardPlayable(Card c){
        // Wild card always valid
        if(c.getColor().equals(CardColor.WILD)){
            return true;
        }
        // Check if Card is same Color or Function as the top Card in the Discard Pile
        return c.isCardMatching(getCurrentColor(), discardPile.peek().getFunction());
    }

    public boolean validHand(){
        for (Card c: turnOrder.peek().getHand()){
            if(isCardPlayable(c)){
                return true;
            }
        }
        return false;
    }

    public void drawUntilValid(){
        if(!validHand()){
            while(!isCardPlayable(drawCard(turnOrder.peek()))){}
        }
    }

    /**
     * 
     */
    public boolean playCard(Player p, int i, CardColor color){
        try{
            if (drawCounter>0){
                if (discardPile.peek().getFunction() ==  p.getCard(i).getFunction()){
                    executeCardEffect(p.getCard(i), null);
                    discardPile.add(p.removeFromHand(i));
                    return true;
                }
                return false;
            }
            if (isCardPlayable(p.getCard(i))){
                executeCardEffect(p.getCard(i), color);
                discardPile.add(p.removeFromHand(i));
                return true;
            }
            return false;
        }
        catch (IndexOutOfBoundsException e) {
            return false;
        }
    } 

    public void executeCardEffect(Card c, CardColor color){
        switch(c.getFunction()){
            case REVERSE:
                setCurrentColor(c.getColor());
                reverseOrder(turnOrder.remove(),turnOrder);
                return;
            case SKIP:
                setCurrentColor(c.getColor());
                skipCounter++;
                return;
            case DRAW2:
                setCurrentColor(c.getColor());
                drawCounter+=2;
                return;
            case DRAW4:
                this.setCurrentColor(color);
                drawCounter+=4;
                return;
            case WILD:
                this.setCurrentColor(color);
                return;
            default: 
                setCurrentColor(c.getColor());
                return;
        }
    }

    public void chooseColor(int i){
        switch(i){
            case 0:
                setCurrentColor(CardColor.BLUE);
                return;
            case 1:
                setCurrentColor(CardColor.RED);
                return;
            case 2:
                setCurrentColor(CardColor.YELLOW);
                return;
            case 3:
                setCurrentColor(CardColor.GREEN);
                return;
            default:
                return;
        }
    }
    /*
     * Reverses the turn order Queue but keeps the current Player at the head 
     * @param first - the current Player 
     * @param q - the turn order Queue
     */
    public void reverseOrder(Player first, Queue<Player> q){
        if (q.size() == 0){
            q.add(first);// Adds the current Player to the head
            return;
        }
        // storing front(first element) of queue
        Player fr = q.peek();
        // removing front
        q.remove();
        // asking recursion to reverse the
        // leftover queue
        reverseOrder(first, q);
        // placing first element
        // at its correct position
        q.add(fr);
    }

    public void drawCardsHandler(){
        if(drawCounter>0){
            if(!turnOrder.peek().handContainsSameFunction(discardPile.peek())){// Checks if Player's Hand contains a Draw Card 
                drawMultipleCards(turnOrder.peek());
                this.turnOrder.add(turnOrder.remove());

            }else{
                return;//should give player choice to continue drawStack or draw current drawStack
                //import the keyboard thing
                //yes or no whether to draw or play
                //yes -> enter index of card check if index card is draw 2 or 4 function
                //no -> draw the draw stack
            }
        }
    }

    


    /**TODO
     * Goes to the next Player in the Queue
     * Also takes into account if the Skip Card is played
     */
    public void endTurn(){
        while (skipCounter>0){
            this.turnOrder.add(turnOrder.remove());
            skipCounter--;
        }
        skipCounter++;
        drawCardsHandler();//TODO
    }

    /*
    *  Creates a new UNO deck with 108 cards
    */
    public void createDeck(){
        for(CardFunction f: CardFunction.values()){
            for(CardColor c: CardColor.values()){
                    Card tmpcard = new Card(c, f);
                    if(c == CardColor.WILD && (f == CardFunction.WILD|| f == CardFunction.DRAW4)){
                        deck.push(tmpcard);
                        deck.push(tmpcard);
                        deck.push(tmpcard);
                        deck.push(tmpcard);
                   
                        }
                    else if(c != CardColor.WILD && f != CardFunction.WILD && f != CardFunction.DRAW4){
                            deck.push(tmpcard);
                            if(f!=CardFunction.NUM0){
                            deck.push(tmpcard);
                            }
                    }
            }
        }
    }

    public boolean winGame(){
        if(turnOrder.peek().getHand().isEmpty()){
            gameRunning = false;
            return true;
        }
        return false;
    }


    public void shuffleDeck(){
        Collections.shuffle(this.deck);
    }

    @Override
    public String toString(){
        return ("top of Discard is " + Constants.colorPicker(discardPile.peek().getColor()) + discardPile.peek().getFunction() + Constants.ANSI_RESET);
    }

}

/**
 * Global Color variable
 * when wild card played it would prompt the player for a color(needs to convert string input to a CardColor)
 * whenever a card is played set currentColor to that cards color
 * tweak isCardplayable()
 */