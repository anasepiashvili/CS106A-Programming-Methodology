
/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import java.util.Arrays;

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {

	public static void main(String[] args) {
		new Yahtzee().start(args);
	}

	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		upperScore = new int[nPlayers]; // This array counts upper score for each player
		lowerScore = new int[nPlayers]; // This array counts lower score for each player
		total = new int[nPlayers]; // This array counts total score for each player
		playGame();
	}

	private void playGame() {
		// This matrix with initial values 0 is used to check if category is selected
		// before or not
		selected = new int[N_CATEGORIES][nPlayers];

		// All players select categories as many times as scoring categories are
		for (int i = 0; i < N_SCORING_CATEGORIES; i++) {
			allPlayersRoll();
		}

		// Updates categories after finishing the game
		for (int y = 1; y <= nPlayers; y++) {
			display.updateScorecard(UPPER_SCORE, y, upperScore[y - 1]);
			display.updateScorecard(LOWER_SCORE, y, lowerScore[y - 1]);
			display.updateScorecard(UPPER_BONUS, y, upperBonus);
			display.updateScorecard(TOTAL, y, total[y - 1] + upperBonus);
		}

		int maxScore = 0;
		int playerIndex = 0;
		for (int i = 0; i < total.length; i++) {
			if (total[i] > maxScore) {
				maxScore = total[i]; // finds the largest total score between players
				playerIndex = i; // tells number of player with largest score
			}
		}
		maxScore += upperBonus;
		// Prints message to declare winner
		message = ("Congratulations, " + playerNames[playerIndex] + ", you're the winner with a total score of "
				+ maxScore + "!");
		display.printMessage(message);
	}

	private void allPlayersRoll() {
		int player = 0;
		// Each player fills one category
		for (int i = 1; i <= nPlayers; i++) {
			player = i;
			name = playerNames[i - 1]; // names of players that are stored in playerNames array
			fillOneCategory(player);
		}
	}

	// All steps each player goes through
	private void fillOneCategory(int player) {
		message = (name + "'s turn! Click \"Roll Dice\" button to roll the dice.");
		display.printMessage(message);
		display.waitForPlayerToClickRoll(player);

		int[] dice = new int[N_DICE]; // initializes array for dice
		for (int i = 0; i < N_DICE; i++) {
			dice[i] = rgen.nextInt(1, 6); // chooses values from 1 to 6 of all dices randomly
		}
		display.displayDice(dice);
		message = "Select the dice you wish to re-roll and click \"Roll Again\".";
		// player can re-roll selected dices twice
		for (int j = 0; j < 2; j++) {
			display.printMessage(message);
			display.waitForPlayerToSelectDice();
			for (int i = 0; i < N_DICE; i++) {
				int index = i;
				if (display.isDieSelected(index)) {
					dice[index] = rgen.nextInt(1, 6);
				}
			}
			display.displayDice(dice);
		}
		message = "Select a category for this roll.";
		display.printMessage(message);
		int category = display.waitForPlayerToSelectCategory();
		// while chosen category is selected before, asks player to select category
		// again
		while (selected[category - 1][player - 1] == 1) {
			display.printMessage("You have already selected this category. Select again.");
			category = display.waitForPlayerToSelectCategory();
		}
		// changes initial value of matrix 0 to 1 after selecting a certain category
		if (selected[category - 1][player - 1] == 0) {
			selected[category - 1][player - 1] = 1;
		}
		int score = countScore(category, dice);
		total[player - 1] += score; // counts total score
		if (category <= 6) {
			upperScore[player - 1] += score; // counts upper score
		}
		if (category >= 9) {
			lowerScore[player - 1] += score; // counts lower score
		}
		if (upperScore[player - 1] >= 63) {
			upperBonus = 35;
		} else {
			upperBonus = 0;
		}
		// updates scorecard
		display.updateScorecard(category, player, score);
		display.updateScorecard(TOTAL, player, total[player - 1]);
	}

	// Counts and returns score for each category
	private int countScore(int category, int[] dice) {
		int score = 0;
		if (category >= 1 && category <= 6) {
			for (int i = 0; i < dice.length; i++) {
				if (category == dice[i]) {
					score += dice[i];
				}
			}
		}
		if (category == THREE_OF_A_KIND || category == FOUR_OF_A_KIND) {
			if (checkCategory(category, dice)) {
				for (int i = 0; i < dice.length; i++) {
					score += dice[i];
				}
			} else {
				score = 0;
			}
		}
		if (category == SMALL_STRAIGHT) {
			if (checkCategory(category, dice)) {
				score = 30;
			} else {
				score = 0;
			}
		}
		if (category == LARGE_STRAIGHT) {
			if (checkCategory(category, dice)) {
				score = 40;
			} else {
				score = 0;
			}
		}
		if (category == FULL_HOUSE) {
			if (checkCategory(category, dice)) {
				score = 25;
			} else {
				score = 0;
			}
		}
		if (category == YAHTZEE) {
			if (checkCategory(category, dice)) {
				score = 50;
			} else {
				score = 0;
			}
		}
		if (category == CHANCE) {
			for (int i = 0; i < dice.length; i++) {
				score += dice[i];
			}
		}
		return score;
	}

	// Checks whether an array of dice values matches a particular category
	private boolean checkCategory(int category, int[] dice) {
		if (category == YAHTZEE) {
			if (dice[0] == dice[1] && dice[1] == dice[2] && dice[2] == dice[3] && dice[3] == dice[4]) {
				return true;
			} else {
				return false;
			}
		}

		if (category == THREE_OF_A_KIND) {
			Arrays.sort(dice);
			if ((dice[0] == dice[1] && dice[1] == dice[2]) || (dice[1] == dice[2] && dice[2] == dice[3])
					|| (dice[2] == dice[3] && dice[3] == dice[4])) {
				return true;
			} else {
				return false;
			}
		}

		if (category == FOUR_OF_A_KIND) {
			Arrays.sort(dice);
			if ((dice[0] == dice[1] && dice[1] == dice[2] && dice[2] == dice[3])
					|| (dice[1] == dice[2] && dice[2] == dice[3] && dice[3] == dice[4])) {
				return true;
			} else {
				return false;
			}
		}

		if (category == FULL_HOUSE) {
			Arrays.sort(dice);
			if (dice[0] == dice[1] && dice[3] == dice[4] && (dice[1] == dice[2] || dice[2] == dice[3])) {
				return true;
			} else {
				return false;
			}
		}

		if (category == SMALL_STRAIGHT) {
			Arrays.sort(dice);
			if ((dice[0] == 1 && dice[1] == 2 && dice[2] == 3 && dice[3] == 4)
					|| (dice[0] == 2 && dice[1] == 3 && dice[2] == 4 && dice[3] == 5)
					|| (dice[0] == 3 && dice[1] == 4 && dice[2] == 5 && dice[3] == 6)
					|| (dice[1] == 1 && dice[2] == 2 && dice[3] == 3 && dice[4] == 4)
					|| (dice[1] == 2 && dice[2] == 3 && dice[3] == 4 && dice[4] == 5)
					|| (dice[1] == 3 && dice[2] == 4 && dice[3] == 5 && dice[4] == 6)) {
				return true;
			} else {
				return false;

			}
		}
		if (category == LARGE_STRAIGHT) {
			Arrays.sort(dice);
			if ((dice[0] == 1 && dice[1] == 2 && dice[2] == 3 && dice[3] == 4 && dice[4] == 5)
					|| (dice[0] == 2 && dice[1] == 3 && dice[2] == 4 && dice[3] == 5 && dice[4] == 6)) {
				return true;
			} else {
				return false;
			}
		}
		return rootPaneCheckingEnabled;
	}

	/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private String name;
	private String message;
	private int[] total;
	private int[] upperScore;
	private int[] lowerScore;
	private int[][] selected;
	int upperBonus;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();

}
