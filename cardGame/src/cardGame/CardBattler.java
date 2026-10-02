package cardGame;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class CardBattler {
	//NOTE: Player red is the computer.
	
	//These are the standard cards players start with (each player has their own set of cards)
	static ArrayList<Integer> cardsBlue = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
	static ArrayList<Integer> cardsRed = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
			
    //These track the total value each player has on each column
	static final int[] blueValue = {0, 0, 0, 0, 0, 0};
	static final int[] redValue = {0, 0, 0, 0, 0, 0};
		    
	//This is how much a column is worth in game points
	static final int[] columnValues = {4, 5, 6, 7, 8, 9};
		    
	static Scanner read = new Scanner(System.in);
	
	public static void main(String[] args) {
	    /* This while loop ensures that the game only ends once both players have used up all their cards
	    (a.k.a. the game is still running */ 
		int toPlace = 0; //This represents the card the players drew
		
		while(cardsBlue.size() != 0 && cardsRed.size() != 0) { 
			//Blue's turn
			toPlace = drawCard("blue"); 
			System.out.println("You have drawn the " + toPlace + " card. Select the column you want to place it! (1~6)");
			int location = 0; //This represents which column the players assigned the card
			//This is to ensure they enter valid column values
			while((location != 1) || (location != 2) || (location != 3) || (location != 4) || (location != 5) || (location != 6)) {
				location = read.nextInt();
			}
					
			//Red's turn
			toPlace = drawCard("red");
		}
	}
	
	//This method simulates a random card drawn from the player's deck
	public static int drawCard(String player) {
		int cardDrawn = 0; //This represents the card the players drew
		if(player.equals("blue")) {
			cardDrawn = cardsBlue.remove((int)(Math.random() * cardsBlue.size()));
		} else { //player is red
			cardDrawn = cardsRed.remove((int)(Math.random() * cardsBlue.size()));
		}
		return cardDrawn;
	}
	
	//This method simulates the Red's actions
	public static int red(int card) {
		int columnChosen = ((int)(Math.random() * 6)); //For now, I made this random because I want to test play
		return columnChosen;
	}
}