package cardGame;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;

public class CardBattler {
	//NOTE: Red is Intellisense, the bot.
	
	//These are the standard cards players start with (each player has their own set of cards)
	static ArrayList<Integer> cardsBlue = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
	static ArrayList<Integer> cardsRed = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
	
	//NOTE: Make sure columnValues.length() equals numColumns!
	static final int numColumns = 6;
	
    //These track the total value each player has on each column
	static final int[] blueValue = new int[numColumns];
	static final int[] redValue = new int[numColumns];
		    
	//This is how much a column is worth in game points
	static final int[] columnValues = {4, 5, 6, 7, 8, 9};
	
	//Score tracker, used when counting points in the end
	static int blueScore = 0;
	static int redScore = 0;
		    
	static Scanner read = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Column Battler Game! \nHave more sum of cards in a column that your opponent to win points for the column.\nWhoever scores more points wins!");
		System.out.println("\nYou will play against Intellisense, a bot.");
		for(int i = 0; i < numColumns; i++) {
			System.out.println("Column " + (i+1) + " is worth " + columnValues[i] + " points");
		}
		int toPlace = 0; //This variable represents the card that was drawn
		
		// This while loop ensures that the game only ends once both sides have used up all their cards (a.k.a. the game is still running
		System.out.println("\nPlease choose a difficulty level");
		System.out.println("A: You add a 1 card to a random column 4 times");
		System.out.println("B: You add a 1 card to a random column 2 times");
		System.out.println("C: Standard rules");
		System.out.println("D: Intellisense adds a 1 card to a random column 3 times");
		System.out.println("Please enter \"A\", \"B\", \"C\", or \"D\"");
		boolean validReply = false;
		while(!validReply) {
			String reply = read.next();
			reply.toUpperCase();
			if((reply.equals("A") || reply.equals("B") || reply.equals("C") || reply.equals("D"))) {
				validReply = true;
				if(reply.equals("A")) {
					for(int i = 0; i < 4; i++) {
						int location = (int)(Math.random() * numColumns);
						blueValue[location]++;	
					}
					System.out.println("You have selected difficulty A!");
				} else if(reply.equals("B")) {
					for(int i = 0; i < 2; i++) {
						int location = (int)(Math.random() * numColumns);
						blueValue[location]++;	
					}
					System.out.println("You have selected difficulty B!");
				} else if(reply.equals("D")){ 
					for(int i = 0; i < 3; i++) {
						int location = (int)(Math.random() * numColumns);
						redValue[location]++;	
					}
					System.out.println("You have selected difficulty D!");
				} else { //reply was "C"
					System.out.println("You have selected difficulty C!");
				}
			} 
			if(!validReply) {
				System.out.println("Please enter \"A\", \"B\", \"C\", or \"D\"");
			}
		}
		while(cardsBlue.size() != 0 && cardsRed.size() != 0) { 
			//Blue's turn
			toPlace = drawCard("blue"); 
			int location = 0; //This represents which column the card was assigned
			boolean validLocation = false; //This variable checks if the player entered a valid value
			while(!validLocation) {
				try {
					System.out.println("\nYou have drawn the " + toPlace + " card. \nSelect the column you want to place it! (1~" + numColumns + ")");
			        location = read.nextInt();
			        for(int i = 1; i <= numColumns; i++) {
						if(location == i) {
							validLocation = true;
							break;
						}
					}
			    } catch (InputMismatchException e) {
			        System.out.println("Invalid input. Please enter a valid number.");
			        read.next();
			    }	
				if(!validLocation) {
					System.out.println("Invalid column! Please enter an integer between 1 and " + numColumns + ".");
				}
			} //The player can only exit this loop until they entered a valid value
			System.out.println("You have placed the " + toPlace + " card at column " + location + "!");
			location--; //User enters 1~6, computer processes 0~5
			blueValue[location] += toPlace;
			printStatus();
		
			//Intellisense's turn
			toPlace = drawCard("red");
			location = red(toPlace);
			System.out.println("\nIntellisense placed the " + toPlace + " card at column " + (location+1) + "!");
			redValue[location] += toPlace;
			printStatus();
		}
		

		//Scoring logic
		System.out.println("\n\nGame finished! \nLet's count our points!");
		for(int i = 0; i < numColumns; i++) {
			if(redValue[i] > blueValue[i]) {
				System.out.println("Intellisense has won column " + (i+1) + "! " + columnValues[i] + " points for Intellisense!");
				redScore += columnValues[i];
			} else if(blueValue[i] > redValue[i]) {
				System.out.println("You won column " + (i+1) + "'s " + columnValues[i] + " points!");
				blueScore += columnValues[i];
			} else { //Draw
				System.out.println("Column " + (i+1) + " was tied!");
			}
		}
		
		//Print results	
		System.out.println("You: " + blueScore + " points");
		System.out.println("Intellisense: " + redScore + " points");
		
		//Determine winner
		if(redScore > blueScore) {
			System.out.println("Intellisense wins!");
		} else if(blueScore > redScore) {
			System.out.println("You win!");
		} else { //Draw
			System.out.println("It's a draw!");
		}
	}
	
	//This method simulates a random card drawn from the deck
	public static int drawCard(String player) {
		int cardDrawn = 0; //This represents the card drawn
		if(player.equals("blue")) {
			cardDrawn = cardsBlue.remove((int)(Math.random() * cardsBlue.size()));
		} else { //Player is red
			cardDrawn = cardsRed.remove((int)(Math.random() * cardsBlue.size()));
		}
		return cardDrawn;
	}
	
	//This method prints the game board each time
	public static void printStatus() {
		for(int i = 0; i < numColumns; i++) {
			System.out.print(blueValue[i] + " ");
		}
		System.out.println();
		for(int i = 0; i < numColumns; i++) {
			System.out.print(redValue[i] + " ");
		}
	}
	
	//This method simulates Intellisense's actions
	public static int red(int card) {
		int columnChosen = (numColumns-1); //The column where Intellisense thinks is the best place to put the card
		int bestPoints = 0; //This is the internal score rating of the best column
		int redPower = 0; //Remaining cards' value for Intellisense
		int bluePower = 0; //Remaining cards' value of the player
		for(int i = 0; i < redValue.length; i++) { //Calculate remaining cards' value for Intellisense
			redPower += redValue[i];
		}
		for(int i = 0; i < blueValue.length; i++) { //Calculate remaining cards' value for the player
			bluePower += blueValue[i];
		}
		for(int x = (numColumns-1); x >= 0; x--) { //Assess each column
			int pointValue = 0; //Internal score rating of the column in question
			int maxCard = 0;
			for(int i = 0; i < cardsBlue.size(); i++) { //This is the highest card value in the player's hand
				if(cardsBlue.get(i) > maxCard) {
					maxCard = cardsBlue.get(i);
				}
			}
			if((redValue[x]) > (maxCard + blueValue[x])) { 
				//Already winning by one turn, no need to reinforce yet
			} else if((redPower + redValue[x]) < blueValue[x]) { 
				//Give up because it's impossible to win
			} else { 
				//Only then consider adding the card to this column
				if(redValue[x] < blueValue[x]) { //We are losing in this column right now
					if((redValue[x] + card) > blueValue[x]) { 
						//I am losing right now, but if I drop the card here, I can retake it (priority consideration)
						pointValue = columnValues[x] * 200; //Emphasis on changing control of column
					} else if ((redValue[x] + card) == blueValue[x]) { 
						//I can prevent the player from getting points on this column by making it a draw (big consideration)
						pointValue = columnValues[x] * 100;
					} else {
						//Emphasis on closing the gap
						pointValue = columnValues[x] * (100 - (blueValue[x] - redValue[x] - card));
					}
				} else if (redValue[x] > blueValue[x]) { //Winning in this column right now 
					if(cardsBlue.isEmpty()) { 
						//If I am winning and the player has no cards left, there's absolutely no reason to add a card here
						pointValue = Integer.MIN_VALUE;
					} else if((cardsBlue.size() == 1) && ((redPower + redValue[x]) < (bluePower + blueValue[x]))) { 
						//The player have their last card, and if the player uses it on this column they will win in the end, even if I put my remaining cards here
						pointValue = Integer.MIN_VALUE;
					} else {
						pointValue = (int)(columnValues[x] * 50 - Math.pow(redValue[x] - blueValue[x] + card , 2)); //Dis-incentive for overkill 
					}
				} else { //Draw in this column right now
					pointValue = columnValues[x] * 100 - card; //Win it but not by a lot
				}
			}
			if((card == 1) && (redValue[x] == 0) && (blueValue[x] == 0)) {
				//A 1 card can be strategically used in an empty column to force a reply or take points uncontested. OVERRIDE all other rules if this is possible.
				pointValue = 100_000_000 + columnValues[x];
			}
			if(bestPoints < pointValue) {
				bestPoints = pointValue;
				columnChosen = x;
			}
		}
		return columnChosen;
	}
}
