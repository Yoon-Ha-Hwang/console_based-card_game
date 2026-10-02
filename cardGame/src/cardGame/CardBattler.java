package cardGame;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class CardBattler {
	//NOTE: Player red is the computer.
	
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
	
	//Score tracker
	static int blueScore = 0;
	static int redScore = 0;
		    
	static Scanner read = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Column Battler Game! \nHave more points in a column than your opponent to win the column.\nDeploy cards in the column to increase your points in the column!");
		for(int i = 0; i < numColumns; i++) {
			System.out.println("Column " + (i+1) + " is worth " + columnValues[i] + " points");
		}
		int toPlace = 0; //This variable represents the card the players drew
		
		// This while loop ensures that the game only ends once both players have used up all their cards (a.k.a. the game is still running
		while(cardsBlue.size() != 0 && cardsRed.size() != 0) { 
			//Blue's turn
			toPlace = drawCard("blue"); 
			System.out.println("\nYou have drawn the " + toPlace + " card. \nSelect the column you want to place it! (1~6)");
			int location = 0; //This represents which column the players assigned the card
			boolean validLocation = false; //This variable checks if the user entered a valid value
			while(!validLocation) {
				location = read.nextInt();
				for(int i = 1; i <= numColumns; i++) {
					if(location == i) {
						validLocation = true;
						break;
					}
				}
				if(!validLocation) {
					System.out.println("Invalid column! Please enter an integer between 1 and 6 (inclusive");
				}
			} //The user can only exit this loop until they entered a valid value
			System.out.println("You have placed the " + toPlace + " card at column " + location + "!");
			location--; //User enters 1~6, computer processes 0~5
			blueValue[location] += toPlace;
			printStatus();
		
			//Red's turn
			toPlace = drawCard("red");
			location = red(toPlace);
			System.out.println("\nRed placed the " + toPlace + " card at column " + (location+1) + "!");
			redValue[location] += toPlace;
			printStatus();
		}
		

		//Scoring logic
		System.out.println("\nLet's count our points!");
		for(int i = 0; i < numColumns; i++) {
			if(redValue[i] > blueValue[i]) {
				System.out.println("Red has won column " + (i+1) + "! " + columnValues[i] + " points for Red!");
				redScore += columnValues[i];
			} else if(blueValue[i] > redValue[i]) {
				System.out.println("Blue has won column " + (i+1) + "! " + columnValues[i] + " points for Blue!");
				blueScore += columnValues[i];
			} else { //Draw
				System.out.println("Column " + (i+1) + " was tied!");
			}
		}
		
		//Print results
		System.out.println("Red: " + redScore + " points");
		System.out.println("Blue: " + blueScore + " points");
		
		//Determine winner
		if(redScore > blueScore) {
			System.out.println("Red wins!");
		} else if(blueScore > redScore) {
			System.out.println("Blue wins!");
		} else { //Draw
			System.out.println("It's a draw!");
		}
	}
	
	//This method simulates a random card drawn from the player's deck
	public static int drawCard(String player) {
		int cardDrawn = 0; //This represents the card the players drew
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
	
	//This method simulates the Red's actions
	public static int red(int card) {
		int columnChosen = 0;
		int bestPoints = 0; //This is the internal score rating
		for(int x = 0; x < numColumns; x++) {
			int[] ifRed = new int[numColumns]; //Internal experiment arrayLists
			int[] ifBlue = new int[numColumns];
			for(int i = 0; i < numColumns; i++) { //Clone arrayLists
				ifRed[i] = redValue[i];
				ifBlue[i] = blueValue[i];
			}
			int oldPoints = assessPoints(ifRed, ifBlue);
			ifRed[x] += card;
			int newPoints = assessPoints(ifRed, ifBlue);
			int changePoints = newPoints - oldPoints;
			if(changePoints >= bestPoints) {
				columnChosen = x;
				bestPoints = changePoints;
			}
		}
		return columnChosen;
	}
	
	public static int assessPoints(int[] R, int[] B) {
		int assessResult = 0;
		for(int i = 0; i < numColumns; i++) {
			assessResult += (R[i] - 2 * B[i]) * columnValues[i];
		}
		return assessResult;
	}
}
