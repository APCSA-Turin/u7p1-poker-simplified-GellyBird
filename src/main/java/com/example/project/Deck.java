package com.example.project;
import java.util.ArrayList;
import java.util.Collections;

public class Deck{
    private ArrayList<Card> cards;

    public Deck(){
        cards = new ArrayList<>();
        initializeDeck();
        shuffleDeck();
    }

    public ArrayList<Card> getCards(){
        return cards;
    }

    public void initializeDeck(){ //hint.. use the utility class
    for (int suit = 0; suit < 3; suit ++) {
        for (int card = 0; card < 13; card ++) {
                cards.add(new Card(Utility.getRanks()[card],Utility.getSuits()[suit]));
            }
        }  
    }

    public  void shuffleDeck(){ //You can use the Collections library or another method. You do not have to create your own shuffle algorithm
    Collections.shuffle(cards); //is .shuffle banned for this?? it says you can use collections,,
    }

    public  Card drawCard(){
        if (!isEmpty()) {
            Card card = cards.get(0);
            cards.remove(0);
            return card;
        } else {
            return null;
        }
    }

    public  boolean isEmpty(){
        return cards.isEmpty();
    }

   


}