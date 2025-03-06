package com.example.project;
import java.util.ArrayList;
import java.util.List;


public class Player{
    private ArrayList<Card> hand;
    private ArrayList<Card> allCards; //the current community cards + hand
    String[] suits  = Utility.getSuits();
    String[] ranks = Utility.getRanks();
    
    public Player(){
        hand = new ArrayList<Card>();
    }

    public ArrayList<Card> getHand(){return hand;}
    public ArrayList<Card> getAllCards(){return allCards;}

    public void addCard(Card c){
        hand.add(c);
    }

    public String playHand(ArrayList<Card> communityCards){
        // Initializes and adds the community + player's hand to the list
        allCards = new ArrayList<Card>();
        allCards.addAll(hand);
        allCards.addAll(communityCards);

        ArrayList<Integer> rankFreq = findRankingFrequency();
        // IDX values and their corresponding ranks
        // 0=2,1=3,2=4,3=5,4=6,5=7,6=8,7=9,8=10,9=J,10=Q,11=K,12=A
        ArrayList<Integer> suitFreq = findSuitFrequency();
        // IDX values and their corresponding suits
        // 0=♠,1=♥,2=♣,3=♦

        // --- Hands that rely on the suit being the same ---
        for (Integer suits : suitFreq) {
            String cardsAvailable = "";
            if (suits == 5) { // all cards in both the player's hand and the community hand are the same suit, which means it HAS to be
            // -a Royal Flush, Straight Flush, or Flush
                for (int i = rankFreq.size() - 1; i > rankFreq.size() - 6 ; i --) {
                    cardsAvailable +=  rankFreq.get(i);
                } if (cardsAvailable.indexOf("11111") > -1) { // The last 5 values of rank frequency (A,K,Q,J,10) should all have exactly 1 card in them to be a royal flush
                    return "Royal Flush";
                } else {
                    cardsAvailable = "";
                    for (int i = rankFreq.size() - 1; i > 0; i --) {
                        cardsAvailable += rankFreq.get(i);
                    }
                    if (cardsAvailable.indexOf("11111") > -1) { // Any 5 ranks in a row should have a frequency of 1 to be a straight flush
                        return "Straight Flush";
                    } else {
                        return "Flush"; // if it's none of these, then it defaults to a flush due to the ranks being the same.
                    }
                }
            } 
        }

        // --- Hands that rely on two/three/four of a kind ---
        int numberOfPairs = 0;
        int numberOfTrios = 0; // should always be 0 or 1
        for (Integer ranks : rankFreq) { // for every rank in rankFreq, if the rank frequency is equal to the following...
            if (ranks == 4) {
                return "Four of a Kind"; // they have a four of a kind
            }
            if (ranks == 3) {
                numberOfTrios ++;
            }
            if (ranks == 2) {
                numberOfPairs ++;
            }
        }
        if (numberOfPairs == 1 && numberOfTrios == 1) { // 1 pair and 1 trio
            return "Full House";
        } else if (numberOfTrios == 1 && numberOfPairs == 0) { // trio but no pairs
            return "Three of a Kind";
        } else if (numberOfTrios == 0 && numberOfPairs == 2) { // no trios but 2 pairs
            return "Two Pair";
        } else if (numberOfPairs == 1) { // only 1 pair
            return "A Pair";
        }
        numberOfPairs = 0;
        numberOfTrios = 0;

    // --- Other (High Card & Straight) ---
    boolean highCard = true;
    for (Card comCard : communityCards) {
        // (if statement below) --> If the only two cards in the player's hand are less than all of the community cards, then they cannot have a high card.
            if (Utility.getRankValue(hand.get(0).getRank()) < Utility.getRankValue(comCard.getRank()) && Utility.getRankValue(hand.get(1).getRank()) < Utility.getRankValue(comCard.getRank())) {
             highCard = false;   
        }
    }
    String cardsAvailable = "";
    for (int i = rankFreq.size() - 1; i > 0; i --) {
        cardsAvailable += rankFreq.get(i);
    }
    if (cardsAvailable.indexOf("11111") > -1) { // Same as a straight flush, there needs to be 5 ranks in a row with a frequency of 1
        return "Straight";
    } else if (highCard) { // if the player DOES have the highest card out of all of the cards available...
        return "High Card"; 
    }
        return "Nothing"; // if none of these are true, then they can't play anything.
    }

    public void sortAllCards(){ // selection sort
        for (int i = 0; i < allCards.size(); i ++) {
            for (int j = i; j < allCards.size(); j ++) {
                if (Utility.getRankValue(allCards.get(j).getRank()) < Utility.getRankValue(allCards.get(i).getRank())) {
                    Card temp = allCards.get(i);
                    allCards.set(i, allCards.get(j));
                    allCards.set(j, temp);
                }
            }
        }
    } 

    public ArrayList<Integer> findRankingFrequency(){
        ArrayList<Integer> freq = new ArrayList<Integer>(12);
        for (int i = 0 ; i < 13 ; i ++) { // sets the frequency list to have a default of 0 for all 12 ranks
            freq.add(0);
        }
        if (allCards.size() > 0){ 
            for (int currentRank = 0; currentRank < ranks.length; currentRank ++) { // current rank it's searching for..
                for (int inDeck = 0; inDeck < allCards.size(); inDeck++) {
                    if (allCards.get(inDeck).getRank().equals(ranks[currentRank])) { // check if any card in the deck is equal to this rank
                        freq.set(currentRank, (freq.get(currentRank) + 1));
                    }
                }
        }
        }
        return freq; 
    }

    public ArrayList<Integer> findSuitFrequency(){ 
        ArrayList<Integer> freq = new ArrayList<Integer>(4);
        for (int i = 0 ; i < 4 ; i ++) { // sets a default of 0 for all 4 suits
            freq.add(0);
        }
        for (int currentSuit = 0; currentSuit < suits.length; currentSuit ++) { // current suit it's searching for...
            for (int inDeck = 0; inDeck < allCards.size(); inDeck++) {
                if (allCards.get(inDeck).getSuit().equals(suits[currentSuit])) { // check if any card in the deck has this suit
                    freq.set(currentSuit, (freq.get(currentSuit) + 1));
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
