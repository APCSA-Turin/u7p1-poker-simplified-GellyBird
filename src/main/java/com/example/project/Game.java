package com.example.project;
import java.util.ArrayList;


public class Game{
    public static String determineWinner(Player p1, Player p2,String p1Hand, String p2Hand,ArrayList<Card> communityCards){
        if (!p1Hand.equals(p2Hand)) { // If they do not have the same play, then the following just determines which of the two is worth more points 
        if (Utility.getHandRanking(p1Hand) > Utility.getHandRanking(p2Hand)) {
            return "Player 1 wins!"; // p1 wins if it's hand is worth more
        } else {
            return "Player 2 wins!"; //p2 wins if it's hand is worth more
        }
    } else {
        if (!p1Hand.equals("A Pair") && !p1Hand.equals("Flush")) { // Pairs and flushes have their values determined differently, so the following does not include these.
            int totalRankP1 = 0;
            int totalRankP2 = 0;

            for (Card card : p1.getAllCards()) {
                totalRankP1 += Utility.getRankValue(card.getRank()); // adds the values of each card to totalRankP1
            }

            for (Card card : p2.getAllCards()) {
                totalRankP2 += Utility.getRankValue(card.getRank()); // adds the values of each card to totalRankP2
            }
            // If player 1 wins then that means the cards they played in their run were worth less and vice versa for player 2
            if (totalRankP1 < totalRankP2) {
                return "Player 2 wins!";
            } else if (totalRankP1 > totalRankP2) {
                return "Player 1 wins!";
            } else {
                return "Tie!"; // if the totals are worth the same then they tie
            }
        } else { // if it IS a Pair or a Flush, then determine winner the following way:
            int highestCardp1 = 0;
            int highestCardp2 = 0;

            for (Card card : p1.getAllCards()) { // finds the highest card in P1's hand + the community cards
                if (Utility.getRankValue(card.getRank()) > highestCardp1) {
                    highestCardp1 = Utility.getRankValue(card.getRank()); 
                }
            }
            for (Card card : p2.getAllCards()) { // finds the highest card in P2's hand + the community cards
                if (Utility.getRankValue(card.getRank()) > highestCardp2) {
                    highestCardp2 = Utility.getRankValue(card.getRank());
                }
            }
            // Whoever has the highest valued card wins
            if (highestCardp1 < highestCardp2) {
                return "Player 2 wins!";
            } else if (highestCardp1 > highestCardp2) {
                return "Player 1 wins!";
            } else {
                return "Tie!";
            }  
        }
    }
    }

    public static void play(){ //simulate card playing
        Player player1 = new Player();
        Player player2 = new Player();
        
        player1.addCard(new Card("7", "♠"));
        player1.addCard(new Card("10", "♠"));
  
        player2.addCard(new Card("A", "♠"));
        player2.addCard(new Card("3", "♠"));

        
        // Community cards that could help form the flush
        ArrayList<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card("J", "♠")); // Player 1 completes the flush with this card
        communityCards.add(new Card("J", "♥"));
        communityCards.add(new Card("Q", "♠"));
        
        // Player results after playing the hand
        String p1Result = player1.playHand(communityCards);
        String p2Result = player2.playHand(communityCards);
        
        // Determine the winner
        String winner = Game.determineWinner(player1, player2, p1Result, p2Result, communityCards);
        System.out.println(winner);
    }
        
        

}