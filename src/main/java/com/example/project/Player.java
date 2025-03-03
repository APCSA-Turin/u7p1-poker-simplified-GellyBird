package com.example.project;
import java.util.ArrayList;


public class Player{
    private ArrayList<Card> hand;
    private ArrayList<Card> allCards; //the current community cards + hand
    String[] suits  = Utility.getSuits();
    String[] ranks = Utility.getRanks();
    
    public Player(){
        hand = new ArrayList<>();
    }

    public ArrayList<Card> getHand(){return hand;}
    public ArrayList<Card> getAllCards(){return allCards;}

    public void addCard(Card c){
        hand.add(c);
    }

    public String playHand(ArrayList<Card> communityCards){      //remove the cards too
        allCards = new ArrayList<Card>();allCards.addAll(communityCards);allCards.addAll(hand); // Initializes and adds the community + player's hand to the list

        ArrayList<Integer> rankFreq = findRankingFrequency();
        // IDX values and their corresponding ranks
        // 0=2,1=3,2=4,3=5,4=6,5=7,6=8,7=9,8=10,9=J,10=Q,11=K,12=A
        ArrayList<Integer> suitFreq = findSuitFrequency();
        // IDX values and their corresponding suits
        // 0=♠,1=♥,2=♣,3=♦

        // --- Hands that rely on the suit being the same ---
        for (Integer suits : suitFreq) {
            String cardsAvailable = "";
            if (suits >= 5) {
                for (int i = rankFreq.size() - 1; i > rankFreq.size() - 5; i --) {
                    cardsAvailable +=  rankFreq.get(i);
                } if (cardsAvailable.equals("11111")) {
                    return "Royal Flush";
                } else {
                    cardsAvailable = "";
                    for (int i = rankFreq.size() - 1; i > 0; i --) {
                        cardsAvailable += rankFreq.get(i);
                    }
                    if (cardsAvailable.contains("11111")) {
                        return "Straight Flush";
                    } else {
                        return "Flush";
                    }
                }
            } 
        }

        // --- Hands that rely on two/three/four of a kind ---
        int numberOfPairs = 0;
        int numberOfTrios = 0; // should always be 0 or 1
        for (Integer ranks : rankFreq) {
            if (ranks == 4) {
                return "Four of a Kind";
            }
            if (ranks == 3) {
                numberOfTrios ++;
            }
            if (ranks == 2) {
                numberOfPairs ++;
            }
        }
        if (numberOfPairs == 1 && numberOfTrios == 1) {
            return "Full House";
        } else if (numberOfTrios == 1 && numberOfPairs == 0) {
            return "Three of a Kind";
        } else if (numberOfTrios == 0 && numberOfPairs == 2) {
            return "Two Pair";
        } else if (numberOfPairs == 1) {
            return "Pair";
        }
        numberOfPairs = 0;
        numberOfTrios = 0;

    // --- Other (High Card & Straight) ---
    
        return "Nothing";
    }

    public void sortAllCards(){

    } 

    public ArrayList<Integer> findRankingFrequency(){
        ArrayList<Integer> freq = new ArrayList<>(13);
        for (int currentRank = 0; currentRank < ranks.length; currentRank ++) {
            for (int inDeck = 0; inDeck < allCards.size(); inDeck++) {
                if (allCards.get(inDeck).getRank().equals(ranks[currentRank])) {
                    freq.set(currentRank, freq.get(currentRank) + 1);
                }
            }
        }
        return freq; 
    }

    public ArrayList<Integer> findSuitFrequency(){
        ArrayList<Integer> freq = new ArrayList<>(13);
        for (int currentSuit = 0; currentSuit < suits.length; currentSuit ++) {
            for (int inDeck = 0; inDeck < allCards.size(); inDeck++) {
                if (allCards.get(inDeck).getSuit().equals(ranks[currentSuit])) {
                    freq.set(currentSuit, freq.get(currentSuit) + 1);
                }
            }
        }
        return freq;
    }

   
    @Override
    public String toString(){
        return hand.toString();
    }




}
